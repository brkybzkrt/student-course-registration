package com.student.course.registration.controller.impl;


import com.student.course.registration.base.dtos.LoginDto;
import com.student.course.registration.base.dtos.RegisterDto;
import com.student.course.registration.controller.IStudentController;
import com.student.course.registration.dto.StudentCreateUpdateDto;
import com.student.course.registration.service.IStudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/students")
@Tag(name = "Student Controller", description = "Student Actions")
public class StudentController implements IStudentController {

    @Autowired
    private IStudentService studentService;

    @Operation(summary = "Login as a student via Keycloak")
    @PostMapping("/login")
    @Override
    public ResponseEntity<Object> login(@RequestBody @Valid LoginDto loginDto) {
        return studentService.login(loginDto);
    }

    @Operation(summary = "Register as a student via Keycloak")
    @PostMapping("/register")
    @Override
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDto registerDto) {
        return studentService.register(registerDto);
    }

    @Operation(summary = "Create a new student")
    @PostMapping
    @Override
    public ResponseEntity<Object> create(@RequestBody @Valid StudentCreateUpdateDto createDto) {
        return studentService.create(createDto);
    }

    @Operation(summary = "Get a student by id")
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Object> getById(@PathVariable(name = "id") String id) {
        return studentService.getById(id);
    }

    @Operation(summary = "Update a student by id")
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Object> updateById(@PathVariable(name = "id") String id,@RequestBody @Valid StudentCreateUpdateDto studentCreateUpdateDto) {
        return studentService.updateById(id,studentCreateUpdateDto);
    }

    @Operation(summary = "Delete a student by id")
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Object> deleteById(@PathVariable(name = "id") String id) {
        return studentService.deleteById(id);
    }
}
