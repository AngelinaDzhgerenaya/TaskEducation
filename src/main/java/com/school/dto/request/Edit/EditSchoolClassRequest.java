package com.school.dto.request.Edit;

import lombok.Data;


import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class EditSchoolClassRequest {

    @Size(min = 2, max = 10, message = "Неверная длина названия класса")
    @Pattern(
            regexp = "^[A-Za-zА-Яа-я0-9]+$",
            message = "Название класса должно содержать только буквы и цифры"
    )
    private String className;
}
