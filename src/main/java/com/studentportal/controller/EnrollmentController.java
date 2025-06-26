package com.studentportal.controller;

import com.studentportal.dto.EnrollRequest;
import com.studentportal.dto.EnrolledCourseResponse;
import com.studentportal.service.EnrollmentService;
import com.studentportal.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enroll")
@RequiredArgsConstructor
@CrossOrigin
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<String> enroll(@RequestHeader("Authorization") String token,
                                         @RequestBody EnrollRequest request) {
        String userId = jwtService.extractUsername(token.substring(7));
        String result = enrollmentService.enrollStudent(userId, request);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<EnrolledCourseResponse>> getEnrolledCourses(@RequestHeader("Authorization") String token) {
        String userId = jwtService.extractUsername(token.substring(7));
        return ResponseEntity.ok(enrollmentService.getEnrolledCourses(userId));
    }

}
