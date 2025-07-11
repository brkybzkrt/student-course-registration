package com.student.course.registration.service;

import com.student.course.registration.dto.ProcessRegistrationGroupDto;
import com.student.course.registration.dto.RegistrationCreateUpdateDto;
import org.springframework.http.ResponseEntity;


public interface IRegistrationService {


    ResponseEntity<Object> registerCourses(RegistrationCreateUpdateDto registrationCreateUpdateDto);
    ResponseEntity<Object> getAllPendingGroupedByRegistrationGroup();
    ResponseEntity<Object> processRegistrationGroup(ProcessRegistrationGroupDto processRegistrationGroupDto);
    ResponseEntity<Object> getPendingRegistrationGroupsForNotification();
}
