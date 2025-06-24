package com.student.course.registration.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class RegistrationCreateUpdateDto {

    @NotBlank(message = "Student ID cannot be blank")
    private String studentId;

    @NotEmpty(message = "At least one course ID must be provided")
    @Size(min = 5,max = 5, message = "course count must be 5")
    private List<Long> courseIds;
}
