package com.student.course.registration.controller.impl;


import com.student.course.registration.controller.IRegistrationController;
import com.student.course.registration.dto.ProcessRegistrationGroupDto;
import com.student.course.registration.dto.RegistrationCreateUpdateDto;
import com.student.course.registration.service.IRegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registrations")
public class RegistrationController implements IRegistrationController {

    @Autowired
    private IRegistrationService registrationService;


    @PostMapping
    @Override
    public ResponseEntity<Object> registerCourses(@RequestBody @Valid RegistrationCreateUpdateDto registrationCreateUpdateDto) {
        return registrationService.registerCourses(registrationCreateUpdateDto);
    }

    @GetMapping
    @Override
    public ResponseEntity<Object> getAllPendingGroupedByRegistrationGroup() {
        return registrationService.getAllPendingGroupedByRegistrationGroup();
    }

    @PostMapping("/process")
    @Override
    public ResponseEntity<Object> processRegistrationGroup(@RequestBody @Valid ProcessRegistrationGroupDto processRegistrationGroupDto) {
        return registrationService.processRegistrationGroup(processRegistrationGroupDto);
    }
}
