package com.lms.modules.enrollments.entity;

import com.lms.shared.entity.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("enrollments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment extends BaseEntity {
    @Id
    private String id;
    private String userId;
    private String courseId;
    private String courseTitle;
    private String paymentStatus;
}
