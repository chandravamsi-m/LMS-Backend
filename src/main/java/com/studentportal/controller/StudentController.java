package com.studentportal.controller;

import com.studentportal.dto.StudentDto;
import com.studentportal.entity.Student;
import com.studentportal.service.JwtService;
import com.studentportal.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
@CrossOrigin
public class StudentController {

    private final StudentService studentService;
    private final JwtService jwtService;

    @PostMapping("/submit")
    public ResponseEntity<Map<String, String>> submitStudentForm(
            @RequestHeader("Authorization") String token,
            @RequestBody StudentDto dto) {
        try {
            log.info("Student form endpoint called");
            String userId = jwtService.extractUsername(token.substring(7));
            String message = studentService.saveStudent(userId, dto);
            Map<String, String> response = new HashMap<>();
            response.put("message", message);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Failed to save student: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }



    // ✅ Fetching Student Details
    @GetMapping("/details")
    public ResponseEntity<Student> getStudent(@RequestHeader("Authorization") String token) {
        try {
            String userId = jwtService.extractUsername(token.substring(7));
            Student student = studentService.getStudentByUserId(userId);
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
