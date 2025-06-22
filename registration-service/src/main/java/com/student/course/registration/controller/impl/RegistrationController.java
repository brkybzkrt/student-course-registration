package com.student.course.registration.controller.impl;


import com.student.course.registration.controller.IRegistrationController;
import com.student.course.registration.dto.RegistrationCreateUpdateDto;
import com.student.course.registration.service.IRegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/registrations")
public class RegistrationController implements IRegistrationController {

    @Autowired
    private IRegistrationService registrationService;


    @PostMapping()
    @Override
    public ResponseEntity<Object> registerCourses(@RequestBody @Valid RegistrationCreateUpdateDto registrationCreateUpdateDto) {
        return registrationService.registerCourses(registrationCreateUpdateDto);
    }
}
