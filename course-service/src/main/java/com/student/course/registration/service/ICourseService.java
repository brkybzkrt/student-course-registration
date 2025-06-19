package com.student.course.registration.service;

import com.student.course.registration.base.interfaces.IBaseInterface;
import com.student.course.registration.dto.CourseCreateUpdateDto;
import com.student.course.registration.entitycommon.entities.CourseType;
import org.springframework.http.ResponseEntity;

public interface ICourseService extends IBaseInterface<String,CourseCreateUpdateDto> {

    public ResponseEntity<Object> getCoursesByType (CourseType type);
}
