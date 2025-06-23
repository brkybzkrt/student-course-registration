package com.student.course.registration.service.impl;


import com.student.course.registration.dtos.EnrolledStudentsUpdateEvent;
import com.student.course.registration.entitycommon.entities.Course;
import com.student.course.registration.entitycommon.entities.RegistrationStatusType;
import com.student.course.registration.exceptionfiltercommon.exceptionFilter.ResourceNotFoundException;
import com.student.course.registration.repository.CourseRepository;
import com.student.course.registration.service.ICourseListener;
import com.student.course.registration.topics.KafkaTopics;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseListener implements ICourseListener {

    @Autowired
    private CourseRepository courseRepository;

    @Transactional
    @KafkaListener(topics = KafkaTopics.ENROLLED_STUDENTS_UPDATE, groupId = "course-service")
    @Override
    public void updateEnrolledStudents(EnrolledStudentsUpdateEvent event) {
        try {
            for (Long courseId : event.getCourseIds()) {

                Optional<Course> courseOptional = courseRepository.findById(courseId);
                if (courseOptional.isEmpty()) {
                    throw new ResourceNotFoundException("Course not found with id: " + courseId);
                }

                Course course = courseOptional.get();

                long approvedCount = course.getRegistrations().stream()
                        .filter(reg -> reg.getStatus() == RegistrationStatusType.APPROVED)
                        .count();

                course.setEnrolledStudents((int) approvedCount);
                courseRepository.save(course);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
