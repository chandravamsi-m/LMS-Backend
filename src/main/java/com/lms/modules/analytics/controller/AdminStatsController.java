package com.lms.modules.analytics.controller;

import com.lms.modules.courses.service.CourseService;
import com.lms.modules.enrollments.service.EnrollmentService;
import com.lms.modules.students.service.StudentService;
import com.lms.shared.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin/stats")
@RequiredArgsConstructor
@Tag(name = "Admin Stats", description = "Endpoints for administrative analytics")
public class AdminStatsController {

    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get high-level dashboard statistics (Admin only)")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getStats() {
        Map<String, Long> stats = Map.of(
                "students", studentService.count(),
                "courses", courseService.count(),
                "enrollments", enrollmentService.count()
        );
        return ResponseEntity.ok(ApiResponse.success(stats, "Statistics fetched successfully"));
    }
}
