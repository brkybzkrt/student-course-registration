package com.student.course.registration.feingClients;

import com.student.course.registration.base.response.SuccessResponse;
import com.student.course.registration.entitycommon.dtos.StudentResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "student-service", url = "http://localhost:8082")
public interface StudentFeignClient {

    @GetMapping("/api/v1/students/{id}")
    SuccessResponse<StudentResponseDto> getStudentById(@PathVariable(name = "id") String studentId);
}