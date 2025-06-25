package com.student.course.registration.entitycommon.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendingRegistrationNotificationDto {
    private UUID registrationGroupId;
    private String studentUsername;



}