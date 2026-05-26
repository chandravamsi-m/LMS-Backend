package com.lms.modules.enrollments.controller;

import com.lms.modules.enrollments.dto.EnrollmentResponse;
import com.lms.modules.enrollments.service.EnrollmentService;
import com.lms.shared.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
@RequiredArgsConstructor
@Tag(name = "Enrollments", description = "Endpoints for managing student course enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping("/my-courses")
    @Operation(summary = "Get enrollments for logged-in student")
    public ResponseEntity<ApiResponse<List<EnrollmentResponse>>> getMyEnrollments(Authentication auth) {
        List<EnrollmentResponse> response = enrollmentService.getEnrollmentsByUserId(auth.getName());
        return ResponseEntity.ok(ApiResponse.success(response, "Enrolled courses fetched successfully"));
    }
}
