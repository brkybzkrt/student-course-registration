package com.student.course.registration.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.course.registration.emailcommon.dto.EmailDto;
import com.student.course.registration.emailcommon.service.EmailService;
import com.student.course.registration.entity.FailedEmail;
import com.student.course.registration.repository.FailedEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaNotificationService {


    @Autowired
    private EmailService emailService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FailedEmailRepository failedEmailRepository;

    @Value("${retry-limit}")
    private int retryLimit;

    @KafkaListener(topics = "registration-approved", groupId = "notification-service")
    public void listen(String message) {
        try {
            EmailDto emailDto = objectMapper.readValue(message, EmailDto.class);
            emailService.sendEmail(emailDto);
        } catch (Exception e) {

            FailedEmail failedEmail = new FailedEmail();
            failedEmail.setEmailData(message);
            failedEmail.setAttemptCount(1);

            failedEmailRepository.save(failedEmail);
        }
    }


    @Scheduled(cron = "0 */10 * * * *") // every 10 minutes
    public void retryFailedEmails() {
        List<FailedEmail> failedEmails = failedEmailRepository.findByAttemptCountLessThan(retryLimit);

        for (FailedEmail email : failedEmails) {
            try {
                EmailDto emailDto = objectMapper.readValue(email.getEmailData(), EmailDto.class);
                emailService.sendEmail(emailDto);

                failedEmailRepository.deleteById(email.getId());
            } catch (Exception e) {

                email.setAttemptCount(email.getAttemptCount() + 1);

                failedEmailRepository.save(email);
            }
        }
    }



}
