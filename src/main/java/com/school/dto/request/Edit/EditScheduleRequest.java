package com.school.dto.request.Edit;

import lombok.Data;


import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class EditScheduleRequest {

    @Size(min = 1, max = 50, message = "Неверная длина названия урока")
    @Pattern(
            regexp = "^[А-Яа-я]+$",
            message = "Урок должно состоять только из русского алфавита"
    )
    private String subject;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    @Size(min = 1, max = 50, message = "Неверная длина номера комнаты")
    @Pattern(
            regexp = "^[A-Za-zА-Яа-я0-9]+$",
            message = "Номер комнаты должен содержать только буквы и цифры"
    )
    private String roomNumber;
    private Long teacherId;
    private Long schoolClassId;
}
