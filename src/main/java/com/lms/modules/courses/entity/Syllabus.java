package com.lms.modules.courses.entity;

import com.lms.shared.entity.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document("syllabus")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Syllabus extends BaseEntity {
    @Id
    private String id;
    private String className;
    private Map<String, String> subjects;
}
