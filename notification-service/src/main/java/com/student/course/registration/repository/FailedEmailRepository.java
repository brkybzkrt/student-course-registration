package com.student.course.registration.repository;

import com.student.course.registration.entity.FailedEmail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FailedEmailRepository extends MongoRepository<FailedEmail,String> {
    List<FailedEmail> findByAttemptCountLessThan(int maxAttempts);

}
