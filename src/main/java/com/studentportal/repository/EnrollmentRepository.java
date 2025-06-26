package com.studentportal.repository;

import com.studentportal.entity.Enrollment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EnrollmentRepository extends MongoRepository<Enrollment, String> {
    List<Enrollment> findByUserId(String userId);
    boolean existsByUserIdAndCourseId(String userId, String courseId);
}
