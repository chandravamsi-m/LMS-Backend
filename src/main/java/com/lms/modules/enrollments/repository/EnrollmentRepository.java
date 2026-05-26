package com.lms.modules.enrollments.repository;

import com.lms.modules.enrollments.entity.Enrollment;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface EnrollmentRepository extends MongoRepository<Enrollment, String> {
    List<Enrollment> findByUserId(String userId);
}
