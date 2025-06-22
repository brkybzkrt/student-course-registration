package com.student.course.registration.dto;


import lombok.Data;

import java.util.List;

@Data
public class RegistrationCreateUpdateDto {

    String studentId;
    List<Long> courseIds;
}
