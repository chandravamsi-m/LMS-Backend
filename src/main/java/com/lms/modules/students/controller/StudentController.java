package com.lms.modules.students.controller;

import com.lms.modules.students.dto.StudentRequest;
import com.lms.modules.students.dto.StudentResponse;
import com.lms.modules.students.service.StudentService;
import com.lms.shared.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@Tag(name = "Students", description = "Endpoints for managing student profiles")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/profile")
    @Operation(summary = "Submit or update student profile")
    public ResponseEntity<ApiResponse<StudentResponse>> submitProfile(
            Authentication auth, @RequestBody StudentRequest request) {
        StudentResponse response = studentService.saveStudent(auth.getName(), request);
        return ResponseEntity.ok(ApiResponse.success(response, "Profile updated successfully"));
    }

    @GetMapping("/me")
    @Operation(summary = "Get current student profile")
    public ResponseEntity<ApiResponse<StudentResponse>> getMyProfile(Authentication auth) {
        StudentResponse response = studentService.getStudentByUserId(auth.getName());
        return ResponseEntity.ok(ApiResponse.success(response, "Profile fetched successfully"));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all students (Admin only)")
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getAllStudents() {
        List<StudentResponse> response = studentService.getAllStudents();
        return ResponseEntity.ok(ApiResponse.success(response, "All students fetched successfully"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update student by ID (Admin only)")
    public ResponseEntity<ApiResponse<StudentResponse>> updateStudent(
            @PathVariable String id, @RequestBody StudentRequest request) {
        StudentResponse response = studentService.updateStudentById(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Student updated successfully"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete student (Admin only)")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable String id) {
        studentService.deleteStudentById(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Student deleted successfully"));
    }
}
