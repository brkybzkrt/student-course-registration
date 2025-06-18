package com.student.course.registration.exceptionfiltercommon.exceptionFilter;


public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("Resource not found");
    }

    public ResourceNotFoundException(String message) {
        super(message != null ? message : "Resource not found");
    }
}

