package com.studentportal.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
    @Id
    private String id;

    private String title;
    private String description;
    private String className;       // e.g., "10", "11", "12"
    private double price;
    private String duration;        // e.g., "3 months", "10 weeks"
    private String instructorName;
    private String courseUrl;       // Link to course video page or platform
    private String thumbnailUrl;    // Optional: for frontend display
}
