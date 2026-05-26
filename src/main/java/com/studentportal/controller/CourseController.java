package com.studentportal.controller;

import com.studentportal.dto.CourseDto;
import com.studentportal.entity.Course;
import com.studentportal.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@CrossOrigin
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<String> addCourse(@RequestBody CourseDto dto,
                                            @RequestHeader("Authorization") String token) {
        // 🔐 Token validation optional for now, we'll restrict to admin later
        String response = courseService.addCourse(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{className}")
    public ResponseEntity<List<Course>> getCoursesByClass(@PathVariable String className,
                                                          @RequestHeader("Authorization") String token) {
        List<Course> courses = courseService.getCoursesByClass(className);
        return ResponseEntity.ok(courses);
    }
}
