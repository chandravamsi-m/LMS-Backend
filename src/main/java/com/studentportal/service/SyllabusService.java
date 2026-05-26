package com.studentportal.service;

import com.studentportal.dto.SyllabusDto;
import com.studentportal.entity.Student;
import com.studentportal.entity.Syllabus;
import com.studentportal.repository.StudentRepository;
import com.studentportal.repository.SyllabusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SyllabusService {

    private final SyllabusRepository syllabusRepository;

    private final StudentRepository studentRepository;

    public String addSyllabus(SyllabusDto dto) {
        if (syllabusRepository.findByClassName(dto.getClassName()).isPresent()) {
            return "Syllabus already exists for class " + dto.getClassName();
        }

        Syllabus syllabus = Syllabus.builder()
                .className(dto.getClassName())
                .subjects(dto.getSubjects())
                .build();

        syllabusRepository.save(syllabus);
        return "Syllabus added successfully!";
    }

    public SyllabusDto getSyllabusByClassName(String className) {
        Syllabus syllabus = syllabusRepository.findByClassName(className)
                .orElseThrow(() -> new RuntimeException("Syllabus not found for class: " + className));

        SyllabusDto dto = new SyllabusDto();
        dto.setClassName(syllabus.getClassName());
        dto.setSubjects(syllabus.getSubjects());
        return dto;
    }

    public SyllabusDto getSyllabusForStudent(String userId) {
        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        String className = student.getClassName();

        Syllabus syllabus = syllabusRepository.findByClassName(className)
                .orElseThrow(() -> new RuntimeException("Syllabus not found for class: " + className));

        SyllabusDto dto = new SyllabusDto();
        dto.setClassName(className);
        dto.setSubjects(syllabus.getSubjects());
        return dto;
    }

}
