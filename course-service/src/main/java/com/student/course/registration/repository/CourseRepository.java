package com.student.course.registration.repository;


import com.student.course.registration.entitycommon.entities.Course;
import com.student.course.registration.entitycommon.entities.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

    List<Course> getCoursesByType (CourseType type);
}
