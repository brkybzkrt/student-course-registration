package com.student.course.registration.authcommon.config;

import com.student.course.registration.base.interceptors.requestPath.RequestContextHolder;
import com.student.course.registration.base.response.ErrorResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class ErrorResponseUtil {

    public <T> ErrorResponse<T> createErrorResponse(T errors, Integer status, String message) {
        ErrorResponse<T> response = new ErrorResponse<>();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(status);
        response.setMessage(message);
        response.setPath(RequestContextHolder.getPath());
        response.setError(errors);
        return response;
    }
}
