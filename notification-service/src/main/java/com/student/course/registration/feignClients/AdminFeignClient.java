package com.student.course.registration.feignClients;


import com.student.course.registration.base.response.SuccessResponse;
import com.student.course.registration.entitycommon.dtos.AdminResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "admin-service", url = "http://localhost:8083")
public interface AdminFeignClient {


    @GetMapping("/api/v1/admins")
    SuccessResponse<List<AdminResponseDto>> getAdminList();

}
