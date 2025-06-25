package com.student.course.registration.service;


import com.student.course.registration.emailcommon.service.EmailService;
import com.student.course.registration.entitycommon.dtos.AdminResponseDto;
import com.student.course.registration.entitycommon.dtos.PendingRegistrationNotificationDto;
import com.student.course.registration.feignClients.AdminFeignClient;
import com.student.course.registration.feignClients.RegistrationFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private AdminFeignClient adminFeignClient;

    @Autowired
    private RegistrationFeignClient registrationFeignClient;


    @Scheduled(fixedRate = 60000)
    public void scheduledNotification() {

        try {
            var pendingResponse = registrationFeignClient.getPendingGroupedRegistrations();
            var adminResponse = adminFeignClient.getAdminList();

            List<PendingRegistrationNotificationDto> pendingList = pendingResponse.getData();
            List<AdminResponseDto> admins = adminResponse.getData();

            if (pendingList == null || pendingList.isEmpty()) return;

            String listItems = pendingList.stream()
                    .map(p -> "<li><b>" + p.getStudentUsername() + "</b> → Group ID: " + p.getRegistrationGroupId() + "</li>")
                    .collect(Collectors.joining());

            Map<String, String> templateVariables = Map.of(
                    "registrationList", listItems
            );

            for (AdminResponseDto admin : admins) {
                emailService.sendHtmlEmail(
                        admin.getEmail(),
                        "Pending Course Registrations Reminder",
                        "templates/pending-notification.html",
                        templateVariables
                );
            }

        } catch (Exception e) {
            throw new RuntimeException("Error while sending scheduled emails", e);
        }

    }


}