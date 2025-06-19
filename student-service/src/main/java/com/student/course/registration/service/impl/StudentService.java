package com.student.course.registration.service.impl;


import com.student.course.registration.base.interceptors.requestPath.RequestContextHolder;
import com.student.course.registration.base.response.SuccessResponse;
import com.student.course.registration.dto.LoginDto;
import com.student.course.registration.dto.RegisterDto;
import com.student.course.registration.dto.StudentCreateUpdateDto;
import com.student.course.registration.dto.StudentResponseDto;
import com.student.course.registration.entitycommon.entities.Course;
import com.student.course.registration.entitycommon.entities.Student;
import com.student.course.registration.exceptionfiltercommon.exceptionFilter.ResourceNotFoundException;
import com.student.course.registration.repository.StudentRepository;
import com.student.course.registration.service.IStudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class StudentService implements IStudentService {

    @Autowired
    private StudentRepository studentRepository;


    @Override
    public ResponseEntity<Object> login(LoginDto loginDto) {
        return null;
    }

    @Override
    public ResponseEntity<Object> register(RegisterDto registerDto) {
        return null;
    }

    @Override
    public ResponseEntity<Object> create(StudentCreateUpdateDto createDto) {
        try {
            Student newStudent = new Student();
            BeanUtils.copyProperties(createDto,newStudent);

            Student savedStudent = studentRepository.save(newStudent);

            StudentResponseDto response= new StudentResponseDto();
            BeanUtils.copyProperties(savedStudent,response);

            return ResponseEntity.status(201).body(getStudentResponse(response,201,null));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<Object> getById(String id) {
        try {
            Optional<Student> student = studentRepository.findById(Long.valueOf(id));

            if(student.isPresent()){
                StudentResponseDto courseResponse = new StudentResponseDto();
                BeanUtils.copyProperties(student.get(),courseResponse);

                return ResponseEntity.status(200).body(getStudentResponse(courseResponse,200,null));
            }
            else  throw new ResourceNotFoundException("Student not found with id: " + id);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<Object> updateById(String id, StudentCreateUpdateDto studentCreateUpdateDto) {
        try {
            Optional<Student> student = studentRepository.findById(Long.valueOf(id));

            if(student.isPresent()){
                BeanUtils.copyProperties(studentCreateUpdateDto,student.get());

                Student createdCourse = studentRepository.save(student.get());

                StudentResponseDto response = new StudentResponseDto();
                BeanUtils.copyProperties(createdCourse,response);

                return ResponseEntity.status(200).body(getStudentResponse(response,200,null));
            }
            else  throw new ResourceNotFoundException("Student not found with id: " + id);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<Object> deleteById(String id) {
        try {
            Optional<Student> student = studentRepository.findById(Long.valueOf(id));

            if(student.isPresent()){
                studentRepository.deleteById(student.get().getId());

                return ResponseEntity.status(200).body(getStudentResponse(null,200,"Successfully Deleted"));
            }

            else  throw new ResourceNotFoundException("Student not found with id: " + id);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private <T> SuccessResponse<T> getStudentResponse(T data, Integer status, String message) {
        SuccessResponse<T> response = new SuccessResponse<>();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(status);
        response.setMessage(message);
        response.setPath(RequestContextHolder.getPath());
        response.setData(data);

        return response;
    }
}
