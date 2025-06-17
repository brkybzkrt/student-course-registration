package com.student.course.registration.base.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SuccessResponse<T> extends ApiResponse{

    private T data;

}
