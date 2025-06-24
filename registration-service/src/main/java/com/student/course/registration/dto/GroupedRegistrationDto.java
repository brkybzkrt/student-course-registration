package com.student.course.registration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupedRegistrationDto {

    private UUID registrationGroupId;
    private Long studentId;
    private String username;
    private String email;
    private List<RegistrationResponseDto> registrations;
}
