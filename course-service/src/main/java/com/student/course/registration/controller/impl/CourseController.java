package com.student.course.registration.controller.impl;

import com.student.course.registration.controller.ICourseController;
import com.student.course.registration.dto.CourseCreateUpdateDto;
import com.student.course.registration.entitycommon.entities.CourseType;
import com.student.course.registration.service.ICourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/courses")
@Tag(name = "Course Controller", description = "Course Actions")
public class CourseController implements ICourseController {

    @Autowired
    private ICourseService courseService;

    @Operation(summary = "Create a new course")
    @PostMapping
    @Override
    public ResponseEntity<Object> create(@RequestBody @Valid CourseCreateUpdateDto courseDto) {
        return courseService.create(courseDto);
    }

    @Operation(summary = "Get a course by id")
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Object> getById(@PathVariable(name = "id") String id) {
        return courseService.getById(id);
    }

    @Operation(summary = "Update a course by id")
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Object> updateById(@PathVariable(name = "id") String id,@RequestBody @Valid CourseCreateUpdateDto courseDto) {
        return courseService.updateById(id,courseDto);
    }

    @Operation(summary = "Delete a course by id")
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Object> deleteById(@PathVariable(name = "id") String id) {
        return courseService.deleteById(id);
    }

    @Operation(summary = "Get courses by type")
    @GetMapping("by-type/{type}")
    @Override
    public ResponseEntity<Object> getCoursesByType(@PathVariable(name = "type") CourseType type) {
        return courseService.getCoursesByType(type);
    }
}
