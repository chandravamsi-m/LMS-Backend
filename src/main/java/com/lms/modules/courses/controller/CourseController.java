package com.lms.modules.courses.controller;

import com.lms.modules.courses.dto.CourseRequest;
import com.lms.modules.courses.dto.CourseResponse;
import com.lms.modules.courses.service.CourseService;
import com.lms.shared.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
@Tag(name = "Courses", description = "Endpoints for managing courses")
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Add a new course (Admin only)")
    public ResponseEntity<ApiResponse<CourseResponse>> addCourse(@RequestBody CourseRequest request) {
        CourseResponse response = courseService.addCourse(request);
        return ResponseEntity.ok(ApiResponse.success(response, "Course added successfully"));
    }

    @GetMapping("/class/{className}")
    @Operation(summary = "Get courses by class name")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getCoursesByClass(@PathVariable String className) {
        List<CourseResponse> response = courseService.getCoursesByClass(className);
        return ResponseEntity.ok(ApiResponse.success(response, "Courses fetched successfully"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course details by ID")
    public ResponseEntity<ApiResponse<CourseResponse>> getCourseDetails(@PathVariable String id) {
        CourseResponse response = courseService.getCourseById(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Course details fetched successfully"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update an existing course (Admin only)")
    public ResponseEntity<ApiResponse<CourseResponse>> updateCourse(@PathVariable String id, @RequestBody CourseRequest request) {
        CourseResponse response = courseService.updateCourse(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Course updated successfully"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a course (Admin only)")
    public ResponseEntity<ApiResponse<Void>> deleteCourse(@PathVariable String id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Course deleted successfully"));
    }
}
