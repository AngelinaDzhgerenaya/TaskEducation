package com.school.dto.response;

import com.school.model.Schedule;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Builder
public class ScheduleResponse {
    private Long id;
    private String subject;
    private String teacherFullName;
    private String schoolClassName;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private String roomNumber;

    public static ScheduleResponse of(Schedule item) {
        return  ScheduleResponse.builder()
                .id(item.getId())
                .subject(item.getSubject())
                .dayOfWeek(item.getDayOfWeek())
                .teacherFullName(item.getTeacher().getFirstName() + " " + (item.getTeacher().getMiddleName() != null ? item.getTeacher().getMiddleName() + " " : "") + item.getTeacher().getLastName())
                .schoolClassName(item.getSchoolClass().getClassName())
                .startTime(item.getStartTime())
                .endTime(item.getEndTime())
                .roomNumber(item.getRoomNumber())
                .build();
    }
}