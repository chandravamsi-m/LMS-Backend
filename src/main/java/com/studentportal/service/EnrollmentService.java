package com.studentportal.service;

import com.studentportal.dto.EnrollRequest;
import com.studentportal.entity.Enrollment;
import com.studentportal.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.studentportal.dto.EnrolledCourseResponse;
import com.studentportal.entity.Course;
import com.studentportal.repository.CourseRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository repository;
    private final CourseRepository courseRepository;

    public String enrollStudent(String userId, EnrollRequest request) {
        if (repository.existsByUserIdAndCourseId(userId, request.getCourseId())) {
            return "You are already enrolled in this course.";
        }

        Enrollment enrollment = Enrollment.builder()
                .userId(userId)
                .courseId(request.getCourseId())
                .enrolledAt(LocalDateTime.now().toString())
                .build();

        repository.save(enrollment);
        return "Enrollment successful!";
    }

    public List<EnrolledCourseResponse> getEnrolledCourses(String userId) {
        List<Enrollment> enrollments = repository.findByUserId(userId);

        return enrollments.stream()
                .map(enrollment -> {
                    Course course = courseRepository.findById(enrollment.getCourseId())
                            .orElse(null);
                    if (course == null) return null;

                    EnrolledCourseResponse response = new EnrolledCourseResponse();
                    response.setCourseId(course.getId());
                    response.setTitle(course.getTitle());
                    response.setDescription(course.getDescription());
                    response.setClassName(course.getClassName());
                    response.setEnrolledAt(enrollment.getEnrolledAt());
                    return response;
                })
                .filter(e -> e != null)
                .collect(Collectors.toList());
    }
}
