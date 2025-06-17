package com.student.course.registration.emailcommon.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${email.sender}")
    private String sender;


    public ResponseEntity<Object> sendEmail(String to, String subject, String text){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);


            mailSender.send(message);

            return  ResponseEntity.ok().body("Email was sent successfully");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public ResponseEntity<Object> sendEmail(String to, String subject, String text, MultipartFile file){
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true);

            helper.setFrom(sender);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            if (file != null && !file.isEmpty()) {
                helper.addAttachment(file.getOriginalFilename(), file);
            }

            mailSender.send(message);
            return  ResponseEntity.ok().body("Email was sent successfully");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public ResponseEntity<Object> sendHtmlEmail(String to, String subject, String templatePath) {
        try {
            String htmlContent = loadTemplate(templatePath);

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(sender);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(message);

            return ResponseEntity.ok().body("HTML email sent successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public ResponseEntity<Object> sendHtmlEmail(String to, String subject, String templatePath, Object variablesObject) {
        try {
            String htmlContent = loadTemplate(templatePath);
            htmlContent = replaceTemplatePlaceholders(htmlContent, variablesObject);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(sender);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            return ResponseEntity.ok().body("Dynamic HTML email sent successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private String loadTemplate(String path) {
        try {
            ClassPathResource resource = new ClassPathResource(path);
            return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Template couldn't be loaded: " + path, e);
        }
    }

    private String replaceTemplatePlaceholders(String template, Object variablesObject) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> variables = mapper.convertValue(variablesObject, new TypeReference<>() {});

        for (Map.Entry<String, String> entry : variables.entrySet()) {
            template = template.replace("[[" + entry.getKey() + "]]", entry.getValue());
        }

        return template;
    }




}
