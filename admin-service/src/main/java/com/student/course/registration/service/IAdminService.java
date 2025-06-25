package com.student.course.registration.service;

import com.student.course.registration.base.dtos.LoginDto;
import com.student.course.registration.base.dtos.RegisterDto;
import com.student.course.registration.base.interfaces.IAuthBaseInterface;
import com.student.course.registration.base.interfaces.IBaseInterface;
import com.student.course.registration.dto.AdminCreateUpdateDto;
import org.springframework.http.ResponseEntity;

public interface IAdminService extends IBaseInterface<String, AdminCreateUpdateDto>, IAuthBaseInterface<LoginDto, RegisterDto> {

    ResponseEntity<Object> getAllAdmins();
}
