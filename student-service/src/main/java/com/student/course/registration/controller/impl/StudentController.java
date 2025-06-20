package com.student.course.registration.controller.impl;


import com.student.course.registration.base.dtos.LoginDto;
import com.student.course.registration.base.dtos.RegisterDto;
import com.student.course.registration.controller.IStudentController;
import com.student.course.registration.dto.StudentCreateUpdateDto;
import com.student.course.registration.service.IStudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController implements IStudentController {

    @Autowired
    private IStudentService studentService;

    @PostMapping("/login")
    @Override
    public ResponseEntity<Object> login(@RequestBody @Valid LoginDto loginDto) {
        return studentService.login(loginDto);
    }

    @PostMapping("/register")
    @Override
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDto registerDto) {
        return studentService.register(registerDto);
    }

    @PostMapping
    @Override
    public ResponseEntity<Object> create(@RequestBody @Valid StudentCreateUpdateDto createDto) {
        return studentService.create(createDto);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Object> getById(@PathVariable(name = "id") String id) {
        return studentService.getById(id);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Object> updateById(@PathVariable(name = "id") String id,@RequestBody @Valid StudentCreateUpdateDto studentCreateUpdateDto) {
        return studentService.updateById(id,studentCreateUpdateDto);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Object> deleteById(@PathVariable(name = "id") String id) {
        return studentService.deleteById(id);
    }
}
