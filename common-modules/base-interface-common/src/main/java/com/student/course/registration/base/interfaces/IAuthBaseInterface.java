package com.student.course.registration.base.interfaces;

import org.springframework.http.ResponseEntity;

public interface IAuthBaseInterface<LOGIN_DTO,REGISTER_DTO> {

    ResponseEntity<Object>  login(LOGIN_DTO loginDto);
    ResponseEntity<Object>  register(REGISTER_DTO registerDto);
}
