package com.student.course.registration.controller.impl;

import com.student.course.registration.controller.ICourseController;
import com.student.course.registration.dto.CourseCreateUpdateDto;
import com.student.course.registration.entitycommon.entities.CourseType;
import com.student.course.registration.service.ICourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/courses")
public class CourseController implements ICourseController {

    @Autowired
    private ICourseService courseService;

    @PostMapping
    @Override
    public ResponseEntity<Object> create(@RequestBody @Valid CourseCreateUpdateDto courseDto) {
        return courseService.create(courseDto);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Object> getById(@PathVariable(name = "id") String id) {
        return courseService.getById(id);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Object> updateById(@PathVariable(name = "id") String id,@RequestBody @Valid CourseCreateUpdateDto courseDto) {
        return courseService.updateById(id,courseDto);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Object> deleteById(@PathVariable(name = "id") String id) {
        return courseService.deleteById(id);
    }

    @GetMapping("by-type/{type}")
    @Override
    public ResponseEntity<Object> getCoursesByType(@PathVariable(name = "type") CourseType type) {
        return courseService.getCoursesByType(type);
    }
}
