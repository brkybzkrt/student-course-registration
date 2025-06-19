package com.student.course.registration.entitycommon.entities;

import lombok.Data;
import lombok.Getter;

@Getter
public enum RegistrationStatusType {
    PENDING("WAITING"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED");

    private final String displayName;

    RegistrationStatusType(String displayName) {
        this.displayName = displayName;
    }
}