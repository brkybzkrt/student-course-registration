package com.student.course.registration.repository;

import com.student.course.registration.entitycommon.entities.CourseRegistration;
import com.student.course.registration.entitycommon.entities.RegistrationStatusType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RegistrationRepository extends JpaRepository<CourseRegistration,Long> {
    List<CourseRegistration> findByStatus(RegistrationStatusType status);
    List<CourseRegistration> findByRegistrationGroupId(UUID registrationGroupId);


}
