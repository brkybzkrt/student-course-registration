package com.student.course.registration.feignClients;


import com.student.course.registration.base.response.SuccessResponse;
import com.student.course.registration.entitycommon.dtos.PendingRegistrationNotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "registration-service", url = "http://localhost:8086")
public interface RegistrationFeignClient {

    @GetMapping("/api/v1/registrations/get-pending-for-notification")
    SuccessResponse<List<PendingRegistrationNotificationDto>> getPendingGroupedRegistrations();
}
