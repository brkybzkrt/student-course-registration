package com.student.course.registration.service.impl;

import com.student.course.registration.base.interceptors.requestPath.RequestContextHolder;
import com.student.course.registration.base.response.SuccessResponse;
import com.student.course.registration.dto.CourseCreateUpdateDto;
import com.student.course.registration.dto.CourseResponseDto;
import com.student.course.registration.entity.Course;
import com.student.course.registration.entity.CourseType;
import com.student.course.registration.repository.CourseRepository;
import com.student.course.registration.service.ICourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService implements ICourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public ResponseEntity<Object> create(CourseCreateUpdateDto courseDto) {

        try {
            Course newCourse = new Course();
            BeanUtils.copyProperties(courseDto,newCourse);

            Course savedCourse = courseRepository.save(newCourse);

            CourseResponseDto response= new CourseResponseDto();
            BeanUtils.copyProperties(savedCourse,response);

            return ResponseEntity.status(201).body(getCourseResponse(response,201,null));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<Object> getById(String id) {
        try {
            Optional<Course> course = courseRepository.findById(Long.valueOf(id));

            if(course.isPresent()){
                CourseResponseDto courseResponse = new CourseResponseDto();
                BeanUtils.copyProperties(course.get(),courseResponse);

                return ResponseEntity.status(200).body(getCourseResponse(courseResponse,200,null));
            }
            else return ResponseEntity.status(404).body(getCourseResponse(null,404,"Resource is not found"));


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<Object> updateById(String id, CourseCreateUpdateDto courseDto) {
        try {
            Optional<Course> course = courseRepository.findById(Long.valueOf(id));

            if(course.isPresent()){
                BeanUtils.copyProperties(courseDto,course.get());

                Course createdCourse = courseRepository.save(course.get());

                CourseResponseDto response = new CourseResponseDto();
                BeanUtils.copyProperties(createdCourse,response);

                return ResponseEntity.status(200).body(getCourseResponse(response,200,null));
            }
            else return ResponseEntity.status(404).body(getCourseResponse(null,404,"Resource is not found"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<Object> deleteById(String id) {
        try {
            Optional<Course> course = courseRepository.findById(Long.valueOf(id));

            if(course.isPresent()){
                courseRepository.deleteById(course.get().getId());

                return ResponseEntity.status(200).body(getCourseResponse(null,200,"Successfully Deleted"));
            }

            else return ResponseEntity.status(404).body(getCourseResponse(null,404,"Resource is not found"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<Object> getCoursesByType(CourseType type) {
        try {
            List<Course> courses = courseRepository.getCoursesByType(type);

            if (!courses.isEmpty()){
                List<CourseResponseDto> responseDtos = courses.stream()
                        .map(course -> new CourseResponseDto(
                                course.getId(),
                                course.getName(),
                                course.getMaxCapacity(),
                                course.getEnrolledStudents(),
                                course.getType(),
                                course.getCreatedAt(),
                                course.getUpdatedAt()
                        ))
                        .collect(Collectors.toList());

                return  ResponseEntity.status(200).body(getCourseResponse(responseDtos,200,"success"));
            }

            return  ResponseEntity.status(404).body(getCourseResponse(null,404,"There are no courses like this type yet, that you are looking for"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private <T> SuccessResponse<T>  getCourseResponse(T data,Integer status,String message) {
        SuccessResponse<T> response = new SuccessResponse<>();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(status);
        response.setMessage(message);
        response.setPath(RequestContextHolder.getPath());
        response.setData(data);

        return response;
    }
}
