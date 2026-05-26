package com.lms.modules.students.mapper;

import com.lms.modules.students.dto.StudentRequest;
import com.lms.modules.students.dto.StudentResponse;
import com.lms.modules.students.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {
    Student toEntity(StudentRequest request);
    StudentResponse toResponse(Student entity);
    void updateEntity(StudentRequest request, @MappingTarget Student entity);
}
