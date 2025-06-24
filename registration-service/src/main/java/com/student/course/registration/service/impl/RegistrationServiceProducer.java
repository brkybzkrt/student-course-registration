package com.student.course.registration.service.impl;


import com.student.course.registration.dtos.EnrolledStudentsUpdateEvent;
import com.student.course.registration.topics.KafkaTopics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationServiceProducer {

    @Autowired
    private KafkaTemplate<String, EnrolledStudentsUpdateEvent> kafkaTemplate;


    public void sendEnrolledStudentUpdate(List<Long> courseIds) {
        kafkaTemplate.send(KafkaTopics.ENROLLED_STUDENTS_UPDATE, new EnrolledStudentsUpdateEvent(courseIds));
    }
}
