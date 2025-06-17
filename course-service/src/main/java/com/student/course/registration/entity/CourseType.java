package com.student.course.registration.entity;

import lombok.Getter;

@Getter
public enum CourseType {
    MANDATORY("MANDATORY"),
    ELECTIVE("ELECTIVE");

    private final String displayName;

    CourseType(String displayName) {
        this.displayName = displayName;
    }

}