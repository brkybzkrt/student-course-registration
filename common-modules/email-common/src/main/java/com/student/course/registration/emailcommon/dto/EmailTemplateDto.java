package com.student.course.registration.emailcommon.dto;


import lombok.Data;

@Data
public class EmailTemplateDto {
    private String to;
    private String subject;
    private String templatePath;
}
