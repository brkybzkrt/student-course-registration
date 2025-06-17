package com.student.course.registration.entity;

import com.student.course.registration.postgrescommon.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;


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

    @Column(name = "maxCapacity")
    @ColumnDefault("15")
    private int maxCapacity;

    @Column(name = "enrolled_students", nullable = false)
    @ColumnDefault("0")
    private Integer enrolledStudents = 0;

    @Column(name = "type",nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseType type; // MANDATORY, ELECTIVE
}
