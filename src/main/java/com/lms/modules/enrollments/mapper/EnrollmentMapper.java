package com.lms.modules.enrollments.mapper;

import com.lms.modules.enrollments.dto.EnrollmentRequest;
import com.lms.modules.enrollments.dto.EnrollmentResponse;
import com.lms.modules.enrollments.entity.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EnrollmentMapper {
    Enrollment toEntity(EnrollmentRequest request);
    EnrollmentResponse toResponse(Enrollment entity);
}
