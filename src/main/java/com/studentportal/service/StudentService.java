package com.studentportal.service;

import com.studentportal.dto.StudentDto;
import com.studentportal.entity.Student;
import com.studentportal.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public String saveStudent(String userId, StudentDto dto) {
        if (studentRepository.findByUserId(userId).isPresent()) {
            return "Form already submitted!";
        }

        Student student = Student.builder()
                .userId(userId)
                .fullName(dto.getFullName())
                .rollNumber(dto.getRollNumber())
                .schoolName(dto.getSchoolName())
                .className(dto.getClassName())
                .familyDetails(dto.getFamilyDetails())
                .formSubmitted(true)
                .build();

        studentRepository.save(student);
        return "Student form submitted successfully!";
    }

    public Student getStudentByUserId(String userId) {
        return studentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }
}
