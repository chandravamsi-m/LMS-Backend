package com.lms.modules.enrollments.service;

import com.lms.modules.enrollments.dto.EnrollmentRequest;
import com.lms.modules.enrollments.dto.EnrollmentResponse;
import com.lms.modules.enrollments.entity.Enrollment;
import com.lms.modules.enrollments.mapper.EnrollmentMapper;
import com.lms.modules.enrollments.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentMapper enrollmentMapper;

    public EnrollmentResponse enroll(EnrollmentRequest request) {
        Enrollment enrollment = enrollmentMapper.toEntity(request);
        return enrollmentMapper.toResponse(enrollmentRepository.save(enrollment));
    }

    public List<EnrollmentResponse> getEnrollmentsByUserId(String userId) {
        return enrollmentRepository.findByUserId(userId).stream()
                .map(enrollmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    public long count() {
        return enrollmentRepository.count();
    }
}
