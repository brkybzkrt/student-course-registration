package com.student.course.registration.repository;

import com.student.course.registration.entitycommon.entities.CourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<CourseRegistration,Long> {
}
