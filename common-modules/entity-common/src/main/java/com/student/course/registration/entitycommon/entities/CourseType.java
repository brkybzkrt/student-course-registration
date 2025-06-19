package com.student.course.registration.entitycommon.entities;

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