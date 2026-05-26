package com.lms.modules.courses.repository;

import com.lms.modules.courses.entity.Syllabus;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface SyllabusRepository extends MongoRepository<Syllabus, String> {
    Optional<Syllabus> findByClassName(String className);
}
