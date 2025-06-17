package com.student.course.registration.dto;


import com.student.course.registration.entity.CourseType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CourseCreateUpdateDto {

    @NotBlank(message = "Course name cannot be blank")
    @Size(min = 3, max = 50, message = "Course name must be between 3 and 50 characters")
    private String name;

    @Min(value = 15, message = "Minimum capacity is 15")
    @Max(value = 30, message = "Maximum capacity is 30")
    private int maxCapacity;

    @NotNull(message = "Course type cannot be null")
    private CourseType type;
}
