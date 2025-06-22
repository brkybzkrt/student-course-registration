package com.student.course.registration.feingClients;

import com.student.course.registration.base.response.SuccessResponse;
import com.student.course.registration.entitycommon.dtos.CourseResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "course-service", url = "http://localhost:8081")
public interface CourseFeignClient {

    @PostMapping("/api/v1/courses/list")
    SuccessResponse<List<CourseResponseDto>> getCoursesByIds(@RequestBody List<Long> courseIds);
}
