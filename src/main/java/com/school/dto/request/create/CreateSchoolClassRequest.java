package com.school.dto.request.create;

import com.school.model.SchoolClass;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class CreateSchoolClassRequest {

    @NotBlank(message = "Название класса обязательно")
    @Size(min = 2, max = 10, message = "Неверная длина названия класса")
    @Pattern(
            regexp = "^[A-Za-zА-Яа-я0-9]+$",
            message = "Название класса должно содержать только буквы и цифры"
    )
    private String className;

    public SchoolClass entity(){
        return SchoolClass.builder()
                .className(className)
                .build();
    }
}
