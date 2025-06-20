package com.student.course.registration.service;

import com.student.course.registration.base.dtos.LoginDto;
import com.student.course.registration.base.dtos.RegisterDto;
import com.student.course.registration.base.interfaces.IAuthBaseInterface;
import com.student.course.registration.base.interfaces.IBaseInterface;
import com.student.course.registration.dto.StudentCreateUpdateDto;

public interface IStudentService extends IBaseInterface<String, StudentCreateUpdateDto>, IAuthBaseInterface<LoginDto, RegisterDto> {
}
