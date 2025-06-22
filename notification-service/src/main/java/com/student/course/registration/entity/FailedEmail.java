package com.student.course.registration.entity;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "failed_email")
public class FailedEmail {

    @Id
    private String id;

    private String emailData;

    private int attemptCount = 0;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;


    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;
}
