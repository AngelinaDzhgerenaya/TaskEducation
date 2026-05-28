package com.school.dto.response;

import com.school.model.Teacher;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherResponse {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;

    public static TeacherResponse of(Teacher item) {
        return  TeacherResponse.builder()
                .id(item.getId())
                .firstName(item.getFirstName())
                .middleName(item.getMiddleName())
                .lastName(item.getLastName())
                .build();
    }
}