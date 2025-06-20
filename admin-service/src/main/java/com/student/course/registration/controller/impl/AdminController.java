package com.student.course.registration.controller.impl;

import com.student.course.registration.base.dtos.LoginDto;
import com.student.course.registration.base.dtos.RegisterDto;
import com.student.course.registration.controller.IAdminController;
import com.student.course.registration.dto.AdminCreateUpdateDto;
import com.student.course.registration.service.IAdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admins")
public class AdminController implements IAdminController {

    @Autowired
    private IAdminService adminService;

    @PostMapping("/login")
    @Override
    public ResponseEntity<Object> login(@RequestBody @Valid LoginDto loginDto) {
        return adminService.login(loginDto);
    }

    @PostMapping("/register")
    @Override
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDto registerDto) {
        return adminService.register(registerDto);
    }

    @PostMapping
    @Override
    public ResponseEntity<Object> create(@RequestBody @Valid AdminCreateUpdateDto createDto) {
        return adminService.create(createDto);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Object> getById(@RequestParam(name = "id") String id) {
        return adminService.getById(id);
    }


    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Object> updateById(@RequestParam(name = "id") String id,@RequestBody @Valid AdminCreateUpdateDto adminCreateUpdateDto) {
        return adminService.updateById(id,adminCreateUpdateDto);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Object> deleteById(@RequestParam(name = "id") String id) {
        return adminService.deleteById(id);
    }
}
