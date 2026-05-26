package com.lms.modules.enrollments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentRequest {
    private String userId;
    private String courseId;
    private String courseTitle;
    private String paymentStatus;
}
