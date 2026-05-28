package com.school.service;

import com.school.dto.request.Edit.EditScheduleRequest;
import com.school.dto.request.create.CreateScheduleRequest;
import com.school.dto.response.ScheduleResponse;
import com.school.exception.NotFoundException;
import com.school.model.Schedule;
import com.school.model.SchoolClass;
import com.school.model.Teacher;
import com.school.repository.ScheduleRepository;
import com.school.repository.SchoolClassRepository;
import com.school.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final TeacherRepository teacherRepository;
    private final SchoolClassRepository schoolClassRepository;

    @Override
    public List<ScheduleResponse> findByClassAndDate(Long classId, LocalDate date, Pageable pageable) {
        SchoolClass schoolClass = schoolClassRepository.findById(classId)
                .orElseThrow(() ->
                new NotFoundException("Такого класса не существует"));
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        List<Schedule> schedule = scheduleRepository.findBySchoolClassAndDayOfWeek(schoolClass, dayOfWeek, pageable);

        return schedule.stream()
                .map(ScheduleResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleResponse> findByTeacherAndDate(Long teacherId, LocalDate date, Pageable pageable) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() ->
                new NotFoundException("Такого учителя не существует"));
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        List<Schedule> schedule = scheduleRepository.findByTeacherAndDayOfWeek(teacher,dayOfWeek, pageable);

        return schedule.stream()
                .map(ScheduleResponse::of)
                .collect(Collectors.toList());

    }

    @Override
    public ScheduleResponse create(CreateScheduleRequest request) {
        Schedule schedule = request.entity();
        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() ->
                new NotFoundException("Такого учителя не существует"));
        SchoolClass schoolClass = schoolClassRepository.findById(request.getSchoolClassId())
                .orElseThrow(() ->
                new NotFoundException("Такого класса не существует"));
        schedule.setTeacher(teacher);
        schedule.setSchoolClass(schoolClass);
        return ScheduleResponse.of(scheduleRepository.save(schedule));
    }

    @Override
    public ScheduleResponse update(Long id, EditScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() ->
                new NotFoundException("Такого урока не существует"));

        if (request.getTeacherId() != null) {
            Teacher teacher = teacherRepository.findById(request.getTeacherId())
                    .orElseThrow(() ->
                    new NotFoundException("Такого учителя не существует"));
            schedule.setTeacher(teacher);
        }

        if (request.getSchoolClassId() != null) {
            SchoolClass schoolClass = schoolClassRepository.findById(request.getSchoolClassId())
                    .orElseThrow(() ->
                    new NotFoundException("Такого класса не существует"));
            schedule.setSchoolClass(schoolClass);
        }

        if (request.getSubject() != null) {
            schedule.setSubject(request.getSubject());
        }

        if (request.getDayOfWeek() != null) {
            schedule.setDayOfWeek(request.getDayOfWeek());
        }
        if (request.getRoomNumber() != null) {
            schedule.setRoomNumber(request.getRoomNumber());
        }

        if (request.getStartTime() != null) {
            schedule.setStartTime(request.getStartTime());
        }

        if (request.getEndTime() != null) {
            schedule.setEndTime(request.getEndTime());
        }

        return ScheduleResponse.of(scheduleRepository.save(schedule));
    }

    @Override
    public String delete(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() ->
                new NotFoundException("Такого урока не существует"));
        scheduleRepository.delete(schedule);
        return "Урок удален";
    }
}