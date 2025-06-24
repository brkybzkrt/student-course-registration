package com.student.course.registration.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ProcessRegistrationGroupDto {


    @NotNull(message = "Group ID cannot be null")
    private UUID groupId;

    @NotNull(message = "Approve flag must be specified")
    private Boolean approve;

    @NotBlank(message = "Admin username cannot be blank")
    private String adminUsername;
}
