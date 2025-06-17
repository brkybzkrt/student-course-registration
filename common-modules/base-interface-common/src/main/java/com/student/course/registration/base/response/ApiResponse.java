package com.student.course.registration.base.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String path;
}
