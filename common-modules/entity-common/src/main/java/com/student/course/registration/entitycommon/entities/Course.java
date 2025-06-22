package com.student.course.registration.entitycommon.entities;

import com.student.course.registration.postgrescommon.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;


@Table(name = "courses",
indexes = {
        @Index(name = "idx_course_type",columnList = "type")
})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Course extends BaseEntity {

    @Column(name = "name",nullable = false,unique = true)
    private String name;

    public Course(Long id) {
        super(id);
    }

    @Column(name = "maxCapacity")
    @ColumnDefault("15")
    private int maxCapacity;

    @Column(name = "enrolled_students", nullable = false)
    @ColumnDefault("0")
    private Integer enrolledStudents = 0;

    @Column(name = "type",nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseType type; // MANDATORY, ELECTIVE

    @OneToMany(mappedBy = "course")
    private List<CourseRegistration> registrations = new ArrayList<>();


    public boolean hasAvailableCapacity() {
        long approvedCount = registrations.stream()
                .filter(r -> r.getStatus() == RegistrationStatusType.APPROVED)
                .count();
        return approvedCount < maxCapacity;
    }


    public void updateEnrolledStudents() {
        this.enrolledStudents = (int) registrations.stream()
                .filter(r -> r.getStatus() == RegistrationStatusType.APPROVED)
                .count();
    }
}
