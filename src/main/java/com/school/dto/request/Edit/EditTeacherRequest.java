package com.school.dto.request.Edit;

import lombok.Data;


import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class EditTeacherRequest {
    @Size(min = 2, max = 50, message = "Неверная длина имени")
    @Pattern(
            regexp = "^[А-Яа-я]+$",
            message = "Имя должно состоять только из русского алфавита"
    )
    private String firstName;

    @Size(min = 2, max = 50, message = " Неверная длина фамилии")
    @Pattern(
            regexp = "^[А-Яа-я]+$",
            message = "Фамилия должна состоять только из русского алфавита"
    )
    private String lastName;

    @Size(max = 50, message = "Неверная длина отчества")
    @Pattern(
            regexp = "^[А-Яа-я]+$",
            message = "Отчество должно состоять только из русского алфавита"
    )
    private String middleName;
}
