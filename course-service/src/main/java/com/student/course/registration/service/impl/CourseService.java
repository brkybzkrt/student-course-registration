package com.student.course.registration.service.impl;

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

            return ResponseEntity.ok().body(response);

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

                return ResponseEntity.ok().body(courseResponse);
            }
            else return ResponseEntity.status(404).body("Resource is not found");


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

                return ResponseEntity.ok().body(response);
            }
            else return ResponseEntity.status(404).body("Resource is not found");

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

                return ResponseEntity.ok().body("Successfully Deleted");
            }
            else return ResponseEntity.status(404).body("Resource is not found");

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

                return  ResponseEntity.ok().body(responseDtos);
            }

            return  ResponseEntity.status(404).body("There are no courses like this type yet, that you are looking for");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
