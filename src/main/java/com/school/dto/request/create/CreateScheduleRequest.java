package com.school.dto.request.create;


import com.school.model.Schedule;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class CreateScheduleRequest {

    @NotBlank(message = "Название урока обязательно")
    @Size(min = 1, max = 50, message = "Неверная длина названия урока")
    @Pattern(
            regexp = "^[А-Яа-я]+$",
            message = "Урок должно состоять только из русского алфавита"
    )
    private String subject;
    @NotNull(message = "Дата урока обязательна")
    private DayOfWeek dayOfWeek;
    @NotNull(message = "Время начала урока обязательно")
    private LocalTime startTime;
    @NotNull(message = "Время окончания урока обязательно")
    private LocalTime endTime;
    @NotBlank(message = "Номер комнаты не может быть пустым")
    @Size(min = 1, max = 10, message = "Неверная длина номера комнаты")
    @Pattern(
            regexp = "^[A-Za-zА-Яа-я0-9]+$",
            message = "Номер комнаты должен содержать только буквы и цифры"
    )
    private String roomNumber;
    @NotNull(message = "У урока должен быть учитель")
    private Long teacherId;
    @NotNull(message = "У урока должен быть класс")
    private Long schoolClassId;


    public Schedule entity(){
        return Schedule.builder()
                .subject(subject)
                .dayOfWeek(dayOfWeek)
                .startTime(startTime)
                .endTime(endTime)
                .roomNumber(roomNumber)
                .build();
    }
}
