package com.student.course.registration.dto;

import com.student.course.registration.entitycommon.entities.RegistrationStatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponseDto {

    private Long registrationId;
    private RegistrationStatusType status;
    private String courseName;
}
