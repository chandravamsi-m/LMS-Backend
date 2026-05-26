package com.lms.modules.courses.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequest {
    private String title;
    private String description;
    private String className;
    private double price;
    private String duration;
    private String instructorName;
    private String courseUrl;
    private String thumbnailUrl;
}
