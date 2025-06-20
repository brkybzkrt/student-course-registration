package com.student.course.registration.controller.impl;

import com.student.course.registration.base.dtos.LoginDto;
import com.student.course.registration.base.dtos.RegisterDto;
import com.student.course.registration.controller.IAdminController;
import com.student.course.registration.dto.AdminCreateUpdateDto;
import com.student.course.registration.service.IAdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admins")
public class AdminController implements IAdminController {

    @Autowired
    private IAdminService adminService;

    @Override
    public ResponseEntity<Object> login(@RequestBody @Valid LoginDto loginDto) {
        return adminService.login(loginDto);
    }

    @Override
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDto registerDto) {
        return adminService.register(registerDto);
    }

    @Override
    public ResponseEntity<Object> create(@RequestBody @Valid AdminCreateUpdateDto createDto) {
        return adminService.create(createDto);
    }

    @Override
    public ResponseEntity<Object> getById(@RequestParam(name = "id") String id) {
        return adminService.getById(id);
    }

    @Override
    public ResponseEntity<Object> updateById(@RequestParam(name = "id") String id,@RequestBody @Valid AdminCreateUpdateDto adminCreateUpdateDto) {
        return adminService.updateById(id,adminCreateUpdateDto);
    }

    @Override
    public ResponseEntity<Object> deleteById(@RequestParam(name = "id") String id) {
        return adminService.deleteById(id);
    }
}
