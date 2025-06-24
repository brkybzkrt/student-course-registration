package com.student.course.registration.service.impl;

import com.student.course.registration.base.interceptors.requestPath.RequestContextHolder;
import com.student.course.registration.base.response.SuccessResponse;
import com.student.course.registration.dto.GroupedRegistrationDto;
import com.student.course.registration.dto.ProcessRegistrationGroupDto;
import com.student.course.registration.dto.RegistrationCreateUpdateDto;
import com.student.course.registration.dto.RegistrationResponseDto;
import com.student.course.registration.entitycommon.dtos.CourseResponseDto;
import com.student.course.registration.entitycommon.dtos.StudentResponseDto;
import com.student.course.registration.entitycommon.entities.*;
import com.student.course.registration.feingClients.CourseFeignClient;
import com.student.course.registration.feingClients.StudentFeignClient;
import com.student.course.registration.repository.RegistrationRepository;
import com.student.course.registration.service.IRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RegistrationService  implements IRegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private StudentFeignClient studentFeignClient;

    @Autowired
    private CourseFeignClient courseFeignClient;

    @Autowired
    private RegistrationServiceProducer registrationServiceProducer;

    @Override
    @Transactional
    public ResponseEntity<Object> registerCourses(RegistrationCreateUpdateDto registrationCreateUpdateDto) {
        try{
            SuccessResponse<StudentResponseDto> student = studentFeignClient.getStudentById(registrationCreateUpdateDto.getStudentId());
            if (!student.getData().getCanRegisterCourse()){
                throw new IllegalStateException("Student has already registered 5 approved courses.");

            }

            SuccessResponse<List<CourseResponseDto>> coursesData =  courseFeignClient.getCoursesByIds(registrationCreateUpdateDto.getCourseIds());
            List<CourseResponseDto> courses= coursesData.getData();

            if(courses.size() != 5){
                throw new IllegalArgumentException("Exactly 5 courses must be selected.");

            }

            long mandatoryCoursesCount = courses.stream().filter(c -> c.getType()== CourseType.MANDATORY).count();

            long electiveCoursesCount = courses.stream().filter(c -> c.getType()==CourseType.ELECTIVE).count();

            if(mandatoryCoursesCount !=3 || electiveCoursesCount !=2){
                throw new IllegalArgumentException("You must select 3 mandatory and 2 elective courses.");

            }

            for(CourseResponseDto course: courses)
            {
                if(course.getEnrolledStudents() >= course.getMaxCapacity()) {
                    throw new IllegalStateException("Course is full: " + course.getName());


                }

            }
            UUID registrationGroupId = UUID.randomUUID();

            for (CourseResponseDto course: courses){
                CourseRegistration registration = new CourseRegistration();
                registration.setCourse(new Course(course.getId()));
                registration.setStudent(new Student(student.getData().getId()));
                registration.setStatus(RegistrationStatusType.PENDING);
                registration.setRegistrationGroupId(registrationGroupId);
                registrationRepository.save(registration);


            }

                return  ResponseEntity.ok().body(getRegistrationResponse(null,HttpStatus.CREATED.value(),"Course registration successfully completed"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public ResponseEntity<Object> getAllPendingGroupedByRegistrationGroup() {
        try {

            List<CourseRegistration> allPending = registrationRepository.findByStatus(RegistrationStatusType.PENDING);

            Map<UUID, List<CourseRegistration>> grouped = allPending.stream()
                    .collect(Collectors.groupingBy(CourseRegistration::getRegistrationGroupId));

            List<GroupedRegistrationDto> result = new ArrayList<>();

            for (Map.Entry<UUID, List<CourseRegistration>> entry : grouped.entrySet()) {
                UUID groupId = entry.getKey();
                List<CourseRegistration> groupRegs = entry.getValue();

                if (groupRegs.isEmpty()) continue;

                Student student = groupRegs.get(0).getStudent();

                List<RegistrationResponseDto> registrationDtos = groupRegs.stream()
                        .map(reg -> new RegistrationResponseDto(
                                reg.getId(),
                                reg.getStatus(),
                                reg.getCourse().getName()
                        ))
                        .toList();

                result.add(new GroupedRegistrationDto(
                        groupId,
                        student.getId(),
                        student.getUsername(),
                        student.getEmail(),
                        registrationDtos
                ));
            }

            return ResponseEntity.ok().body(getRegistrationResponse(result,HttpStatus.OK.value(), null));



        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public ResponseEntity<Object> processRegistrationGroup(ProcessRegistrationGroupDto processRegistrationGroupDto) {
        try {

            List<CourseRegistration> groupRegs = registrationRepository.findByRegistrationGroupId(processRegistrationGroupDto.getGroupId());

            if (groupRegs.isEmpty()) {
                throw new IllegalArgumentException("No registrations found for group: " + processRegistrationGroupDto.getGroupId());
            }

            for (CourseRegistration reg : groupRegs) {
                reg.setStatus(processRegistrationGroupDto.getApprove() ? RegistrationStatusType.APPROVED : RegistrationStatusType.REJECTED);
                reg.setApprovedBy(processRegistrationGroupDto.getAdminUsername());
            }

            registrationRepository.saveAll(groupRegs);


            List<Long> approvedCourseIds = groupRegs.stream()
                    .map(reg -> reg.getCourse().getId())
                    .distinct()
                    .toList();

            if (!approvedCourseIds.isEmpty()) {
                registrationServiceProducer.sendEnrolledStudentUpdate(approvedCourseIds);
            }

            return  ResponseEntity.ok().body(getRegistrationResponse(null, HttpStatus.OK.value(), "Registration successfully approved"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    private <T> SuccessResponse<T> getRegistrationResponse(T data, Integer status, String message) {
        SuccessResponse<T> response = new SuccessResponse<>();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(status);
        response.setMessage(message);
        response.setPath(RequestContextHolder.getPath());
        response.setData(data);

        return response;
    }
}
