package com.lms.modules.courses.entity;

import com.lms.shared.entity.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course extends BaseEntity {
    @Id
    private String id;
    private String title;
    private String description;
    private String className;
    private double price;
    private String duration;
    private String instructorName;
    private String courseUrl;
    private String thumbnailUrl;
}
