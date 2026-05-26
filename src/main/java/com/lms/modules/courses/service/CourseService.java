package com.lms.modules.courses.service;

import com.lms.modules.courses.dto.CourseRequest;
import com.lms.modules.courses.dto.CourseResponse;
import com.lms.modules.courses.entity.Course;
import com.lms.modules.courses.mapper.CourseMapper;
import com.lms.modules.courses.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseResponse addCourse(CourseRequest request) {
        Course course = courseMapper.toEntity(request);
        return courseMapper.toResponse(courseRepository.save(course));
    }

    public List<CourseResponse> getCoursesByClass(String className) {
        return courseRepository.findAll().stream()
                .filter(c -> c.getClassName().equalsIgnoreCase(className))
                .map(courseMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CourseResponse getCourseById(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return courseMapper.toResponse(course);
    }

    public CourseResponse updateCourse(String id, CourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        courseMapper.updateEntity(request, course);
        return courseMapper.toResponse(courseRepository.save(course));
    }

    public void deleteCourse(String id) {
        courseRepository.deleteById(id);
    }

    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(courseMapper::toResponse)
                .collect(Collectors.toList());
    }

    public long count() {
        return courseRepository.count();
    }
}
