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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ScheduleServiceImplTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private SchoolClassRepository schoolClassRepository;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    private Teacher teacher;
    private SchoolClass schoolClass;
    private Schedule schedule;

    private CreateScheduleRequest createScheduleRequest;

    private EditScheduleRequest editScheduleRequest;

    @BeforeEach
    void setUp() {
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("Иван");
        teacher.setLastName("Иванов");

        schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setClassName("9А");

        schedule = new Schedule();
        schedule.setId(1L);
        schedule.setSubject("Математика");
        schedule.setTeacher(teacher);
        schedule.setSchoolClass(schoolClass);
        schedule.setDayOfWeek(DayOfWeek.MONDAY);
        schedule.setStartTime(LocalTime.of(9, 0));
        schedule.setEndTime(LocalTime.of(10, 0));
        schedule.setRoomNumber("101");
    }

    @BeforeEach
    void setUpCreate() {
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("Иван");
        teacher.setLastName("Иванов");

        schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setClassName("9А");

        createScheduleRequest = new CreateScheduleRequest();
        createScheduleRequest.setSubject("Математика");
        createScheduleRequest.setTeacherId(teacher.getId());
        createScheduleRequest.setSchoolClassId(schoolClass.getId());
        createScheduleRequest.setDayOfWeek(DayOfWeek.MONDAY);
        createScheduleRequest.setStartTime(LocalTime.of(9, 0));
        createScheduleRequest.setEndTime(LocalTime.of(10, 0));
        createScheduleRequest.setRoomNumber("101");
    }

    @BeforeEach
    void setUpEdit() {
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("Иван");
        teacher.setLastName("Иванов");

        schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setClassName("9А");

        editScheduleRequest = new EditScheduleRequest();
        editScheduleRequest.setSubject("Русский");
        editScheduleRequest.setTeacherId(teacher.getId());
        editScheduleRequest.setSchoolClassId(schoolClass.getId());
        editScheduleRequest.setDayOfWeek(DayOfWeek.MONDAY);
        editScheduleRequest.setStartTime(LocalTime.of(9, 0));
        editScheduleRequest.setEndTime(LocalTime.of(10, 0));
        editScheduleRequest.setRoomNumber("101");
    }

    @Test
    void findByClassAndDate_shouldReturnSchedule_whenExists() {
        Pageable pageable = PageRequest.of(0, 10);
        LocalDate date = LocalDate.of(2024, 1, 15);
        when(schoolClassRepository.findById(1L)).thenReturn(Optional.of(schoolClass));
        when(scheduleRepository.findBySchoolClassAndDayOfWeek(schoolClass,DayOfWeek.MONDAY, pageable)).thenReturn(List.of(schedule));
        List<ScheduleResponse> result = scheduleService.findByClassAndDate(1L, date, pageable);
        assertNotNull(result);
    }

    @Test
    void findByClassAndDate_shouldThrowException_whenClassNotFound() {
        assertThrows(NotFoundException.class, () -> scheduleService.findByClassAndDate(999L, LocalDate.of(2024, 1, 15), null));
    }

    @Test
    void findByTeacherAndDate_shouldReturnSchedule_whenExists() {
        Pageable pageable = PageRequest.of(0, 10);
        LocalDate date = LocalDate.of(2024, 1, 15);
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(scheduleRepository.findByTeacherAndDayOfWeek(teacher,DayOfWeek.MONDAY, pageable)).thenReturn(List.of(schedule));
        List<ScheduleResponse> result = scheduleService.findByTeacherAndDate(1L, date, pageable);
        assertNotNull(result);
    }


    @Test
    void create_shouldSaveSchedule_whenAllFound() {
        when(schoolClassRepository.findById(1L)).thenReturn(Optional.of(schoolClass));
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);
        ScheduleResponse result = scheduleService.create(createScheduleRequest);
        assertNotNull(result);
    }

    @Test
    void create_shouldThrowException_whenTeacherNotFound() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> scheduleService.create(createScheduleRequest));
    }


    @Test
    void update_shouldUpdateSchedule_whenExists() {
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(schoolClassRepository.findById(1L)).thenReturn(Optional.of(schoolClass));
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);
        ScheduleResponse result = scheduleService.update(1L, editScheduleRequest);
        assertEquals("Русский", result.getSubject());

    }


    @Test
    void delete_shouldDeleteSchedule_whenExists() {
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        scheduleService.delete(1L);
        verify(scheduleRepository).delete(schedule);
    }
}