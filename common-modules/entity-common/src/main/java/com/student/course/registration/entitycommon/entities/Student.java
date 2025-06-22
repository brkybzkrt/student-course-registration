package com.student.course.registration.entitycommon.entities;


import com.student.course.registration.postgrescommon.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends BaseEntity {

    @Column(name = "username",nullable = false,unique = true)
    private String username;

    public Student(Long id) {
        super(id);
    }

    @Column(name = "email",nullable = false,unique = true)
    private String email;

    @Column(name = "keycloak_id",nullable = false, unique = true)
    private String keycloakId;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<CourseRegistration> courseRegistrations = new ArrayList<>();

    public boolean canRegisterMoreCourses() {
        long approvedCount = courseRegistrations.stream()
                .filter(r -> r.getStatus() == RegistrationStatusType.APPROVED)
                .count();
        return approvedCount < 5;
    }
}
