package com.studentportal.service;

import com.studentportal.dto.CourseDto;
import com.studentportal.entity.Course;
import com.studentportal.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public String addCourse(CourseDto dto) {
        Course course = Course.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .className(dto.getClassName())
                .price(dto.getPrice())
                .duration(dto.getDuration())
                .instructorName(dto.getInstructorName())
                .courseUrl(dto.getCourseUrl())
                .thumbnailUrl(dto.getThumbnailUrl())
                .build();

        courseRepository.save(course);
        return "Course added successfully!";
    }

    public List<Course> getCoursesByClass(String className) {
        return courseRepository.findByClassName(className);
    }
}
