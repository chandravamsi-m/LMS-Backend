package com.studentportal.controller;

import com.studentportal.dto.SyllabusDto;
import com.studentportal.service.JwtService;
import com.studentportal.service.SyllabusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/syllabus")
@RequiredArgsConstructor
@CrossOrigin
public class SyllabusController {

    private final SyllabusService syllabusService;

    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<String> addSyllabus(@RequestBody SyllabusDto dto,
                                              @RequestHeader("Authorization") String token) {
        // Optional: Validate token here if needed
        String response = syllabusService.addSyllabus(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{className}")
    public ResponseEntity<SyllabusDto> getSyllabus(@PathVariable String className) {
        SyllabusDto syllabus = syllabusService.getSyllabusByClassName(className);
        return ResponseEntity.ok(syllabus);
    }

    @GetMapping("/student")
    public ResponseEntity<?> getStudentSyllabus(@RequestHeader("Authorization") String token) {
        try {
            String userId = jwtService.extractUsername(token.substring(7));
            SyllabusDto syllabus = syllabusService.getSyllabusForStudent(userId);
            return ResponseEntity.ok(syllabus);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
