package com.student.course.registration.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrolledStudentsUpdateEvent {
    private List<Long> courseIds;
}
