package com.student.course.registration.entitycommon.dtos;

import com.student.course.registration.entitycommon.entities.CourseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDto {

    private Long id;
    private String name;
    private int maxCapacity;
    private int enrolledStudents;
    private CourseType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

