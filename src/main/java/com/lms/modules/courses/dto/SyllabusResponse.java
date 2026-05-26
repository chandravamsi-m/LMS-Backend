package com.lms.modules.courses.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SyllabusResponse {
    private String id;
    private String className;
    private Map<String, String> subjects;
}
