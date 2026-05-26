package com.lms.modules.students.entity;

import com.lms.shared.entity.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student extends BaseEntity {
    @Id
    private String id;

    private String userId;
    private String fullName;
    private String rollNumber;
    private String schoolName;
    private String className;
    private String fatherName;
    private String motherName;

    private boolean formSubmitted;
}
