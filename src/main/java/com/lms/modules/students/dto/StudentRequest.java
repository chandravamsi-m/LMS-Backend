package com.lms.modules.students.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {
    private String fullName;
    private String rollNumber;
    private String schoolName;
    private String className;
    private String fatherName;
    private String motherName;
}
