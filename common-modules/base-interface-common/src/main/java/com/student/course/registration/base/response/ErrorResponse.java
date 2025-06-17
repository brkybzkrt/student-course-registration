package com.student.course.registration.base.response;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class ErrorResponse<T> extends ApiResponse{
    private T error;

}
