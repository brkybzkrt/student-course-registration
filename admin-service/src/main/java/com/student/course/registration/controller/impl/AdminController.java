package com.student.course.registration.controller.impl;

import com.student.course.registration.base.dtos.LoginDto;
import com.student.course.registration.base.dtos.RegisterDto;
import com.student.course.registration.controller.IAdminController;
import com.student.course.registration.dto.AdminCreateUpdateDto;
import com.student.course.registration.service.IAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admins")
@Tag(name = "Admin Controller", description = "Admin Actions")
public class AdminController implements IAdminController {

    @Autowired
    private IAdminService adminService;

    @Operation(summary = "Login as an admin via Keycloak")
    @PostMapping("/login")
    @Override
    public ResponseEntity<Object> login(@RequestBody @Valid LoginDto loginDto) {
        return adminService.login(loginDto);
    }

    @Operation(summary = "Login as an admin via Keycloak")
    @PostMapping("/register")
    @Override
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDto registerDto) {
        return adminService.register(registerDto);
    }

    @Operation(summary = "Create a new admin")
    @PostMapping
    @Override
    public ResponseEntity<Object> create(@RequestBody @Valid AdminCreateUpdateDto createDto) {
        return adminService.create(createDto);
    }

    @Operation(summary = "Get an admin by id")
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Object> getById(@RequestParam(name = "id") String id) {
        return adminService.getById(id);
    }


    @Operation(summary = "Update an admin by id")
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Object> updateById(@RequestParam(name = "id") String id,@RequestBody @Valid AdminCreateUpdateDto adminCreateUpdateDto) {
        return adminService.updateById(id,adminCreateUpdateDto);
    }

    @Operation(summary = "Delete an admin by id")
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Object> deleteById(@RequestParam(name = "id") String id) {
        return adminService.deleteById(id);
    }

    @GetMapping
    @Override
    public ResponseEntity<Object> getAllAdmins() {
        return adminService.getAllAdmins();
    }
}
