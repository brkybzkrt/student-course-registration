package com.student.course.registration.service;

import com.student.course.registration.dtos.EnrolledStudentsUpdateEvent;
import org.springframework.http.ResponseEntity;

public interface ICourseListener  {

    void updateEnrolledStudents(EnrolledStudentsUpdateEvent event);

}
