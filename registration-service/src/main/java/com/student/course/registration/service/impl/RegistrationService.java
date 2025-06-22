package com.student.course.registration.service.impl;

import com.student.course.registration.base.response.SuccessResponse;
import com.student.course.registration.dto.RegistrationCreateUpdateDto;
import com.student.course.registration.entitycommon.dtos.CourseResponseDto;
import com.student.course.registration.entitycommon.dtos.StudentResponseDto;
import com.student.course.registration.entitycommon.entities.*;
import com.student.course.registration.feingClients.CourseFeignClient;
import com.student.course.registration.feingClients.StudentFeignClient;
import com.student.course.registration.repository.RegistrationRepository;
import com.student.course.registration.service.IRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService  implements IRegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private StudentFeignClient studentFeignClient;

    @Autowired
    private CourseFeignClient courseFeignClient;

    @Override
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

            for (CourseResponseDto course: courses){
                CourseRegistration registration = new CourseRegistration();
                registration.setCourse(new Course(course.getId()));
                registration.setStudent(new Student(student.getData().getId()));
                registration.setStatus(RegistrationStatusType.PENDING);
                registrationRepository.save(registration);


            }

                return  ResponseEntity.ok().body("KAYIT EDİLDİ");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
