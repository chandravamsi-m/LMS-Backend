package com.lms.modules.courses.mapper;

import com.lms.modules.courses.dto.CourseRequest;
import com.lms.modules.courses.dto.CourseResponse;
import com.lms.modules.courses.dto.SyllabusRequest;
import com.lms.modules.courses.dto.SyllabusResponse;
import com.lms.modules.courses.entity.Course;
import com.lms.modules.courses.entity.Syllabus;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseMapper {
    
    Course toEntity(CourseRequest request);
    CourseResponse toResponse(Course entity);
    void updateEntity(CourseRequest request, @MappingTarget Course entity);

    Syllabus toEntity(SyllabusRequest request);
    SyllabusResponse toResponse(Syllabus entity);
    void updateEntity(SyllabusRequest request, @MappingTarget Syllabus entity);
}
