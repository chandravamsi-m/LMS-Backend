package com.studentportal.dto;

import lombok.Data;

@Data
public class EnrolledCourseResponse {
    private String courseId;
    private String title;
    private String description;
    private String className;
    private String enrolledAt;
}
