package com.school.dto.request.create;

import com.school.model.Teacher;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
public class CreateTeacherRequest {
    @NotBlank(message = "Имя обязательно")
    @Size(min = 2, max = 50, message = "Неверная длина имени")
    @Pattern(
            regexp = "^[А-Яа-я]+$",
            message = "Имя должно состоять только из русского алфавита"
    )
    private String firstName;

    @NotBlank(message = "Фамилия обязательна")
    @Size(min = 2, max = 50, message = "Неверная длина имени")
    @Pattern(
            regexp = "^[А-Яа-я]+$",
            message = "Фамилия должна состоять только из русского алфавита"
    )
    private String lastName;

    @Size(max = 50, message = "Неверная длина имени")
    @Pattern(
            regexp = "^[А-Яа-я]+$",
            message = "Отчество должно состоять только из русского алфавита"
    )
    private String middleName;

    public Teacher entity(){
        return Teacher.builder()
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .build();
    }
}
