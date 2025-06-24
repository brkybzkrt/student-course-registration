package com.student.course.registration.entitycommon.entities;

import com.student.course.registration.postgrescommon.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "course_registrations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseRegistration extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegistrationStatusType status = RegistrationStatusType.PENDING;

    @Column(name = "registration_group_id", nullable = false)
    private UUID registrationGroupId;

    @Column(name = "approved_by")
    private String approvedBy;

}
