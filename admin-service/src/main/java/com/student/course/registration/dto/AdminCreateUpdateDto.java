package com.student.course.registration.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AdminCreateUpdateDto {
    @NotBlank(message = "Admin's username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Admin's email cannot be blank")
    @Size(min = 3, max = 50, message = "Email must be between 3 and 50 characters")
    @Email(message = "Email format must be right")
    private String email;

    @JsonIgnore
    private String keycloakId;
}
