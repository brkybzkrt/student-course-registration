package com.student.course.registration.service.impl;


import com.student.course.registration.base.dtos.LoginDto;
import com.student.course.registration.base.dtos.RegisterDto;
import com.student.course.registration.dto.AdminCreateUpdateDto;
import com.student.course.registration.repository.AdminRepository;
import com.student.course.registration.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminService implements IAdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public ResponseEntity<Object> login(LoginDto loginDto) {
        return null;
    }

    @Override
    public ResponseEntity<Object> register(RegisterDto registerDto) {
        return null;
    }

    @Override
    public ResponseEntity<Object> create(AdminCreateUpdateDto createDto) {
        return null;
    }

    @Override
    public ResponseEntity<Object> getById(String s) {
        return null;
    }

    @Override
    public ResponseEntity<Object> updateById(String s, AdminCreateUpdateDto adminCreateUpdateDto) {
        return null;
    }

    @Override
    public ResponseEntity<Object> deleteById(String s) {
        return null;
    }
}
