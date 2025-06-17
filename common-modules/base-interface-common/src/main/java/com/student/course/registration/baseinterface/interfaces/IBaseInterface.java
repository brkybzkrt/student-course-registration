package com.student.course.registration.baseinterface.interfaces;

import org.springframework.http.ResponseEntity;

public interface IBaseInterface<ID, CREATE_UPDATE_DTO> {
    ResponseEntity<Object> create(CREATE_UPDATE_DTO createDto);
    ResponseEntity<Object> getById(ID id);
    ResponseEntity<Object> updateById(ID id, CREATE_UPDATE_DTO updateDto);
    ResponseEntity<Object> deleteById(ID id);
}
