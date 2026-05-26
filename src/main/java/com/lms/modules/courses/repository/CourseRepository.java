package com.lms.modules.courses.repository;

import com.lms.modules.courses.entity.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface CourseRepository extends MongoRepository<Course, String> {
    Optional<Course> findByClassName(String className);
}
