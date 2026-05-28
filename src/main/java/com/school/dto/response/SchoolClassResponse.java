package com.school.dto.response;

import com.school.model.SchoolClass;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SchoolClassResponse {
    private Long id;
    private String className;

    public static SchoolClassResponse of(SchoolClass item) {
        return  SchoolClassResponse.builder()
                .id(item.getId())
                .className(item.getClassName())
                .build();
    }
}