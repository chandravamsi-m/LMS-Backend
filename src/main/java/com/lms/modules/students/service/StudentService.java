package com.lms.modules.students.service;

import com.lms.modules.students.dto.StudentRequest;
import com.lms.modules.students.dto.StudentResponse;
import com.lms.modules.students.entity.Student;
import com.lms.modules.students.mapper.StudentMapper;
import com.lms.modules.students.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentResponse saveStudent(String userId, StudentRequest request) {
        Student student = studentRepository.findByUserId(userId)
                .orElse(new Student());
        
        studentMapper.updateEntity(request, student);
        student.setUserId(userId);
        student.setFormSubmitted(true);
        
        return studentMapper.toResponse(studentRepository.save(student));
    }

    public StudentResponse getStudentByUserId(String userId) {
        return studentRepository.findByUserId(userId)
                .map(studentMapper::toResponse)
                .orElse(null);
    }

    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toResponse)
                .collect(Collectors.toList());
    }

    public StudentResponse updateStudentById(String id, StudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        studentMapper.updateEntity(request, student);
        return studentMapper.toResponse(studentRepository.save(student));
    }

    public void deleteStudentById(String id) {
        studentRepository.deleteById(id);
    }

    public long count() {
        return studentRepository.count();
    }
}
