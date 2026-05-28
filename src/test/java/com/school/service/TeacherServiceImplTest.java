package com.school.service;

import com.school.dto.request.Edit.EditTeacherRequest;
import com.school.dto.request.create.CreateTeacherRequest;
import com.school.dto.response.TeacherResponse;
import com.school.exception.ConflictException;
import com.school.exception.NotFoundException;
import com.school.model.Schedule;
import com.school.model.Teacher;
import com.school.repository.ScheduleRepository;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherServiceImplTest {

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    private Teacher teacher;

    private CreateTeacherRequest createTeacherRequest;

    private EditTeacherRequest editTeacherRequest;



    @BeforeEach
    void setUp() {
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("Иван");
        teacher.setLastName("Иванов");
        teacher.setMiddleName("Иванович");
    }

    @BeforeEach
    void setUpCreate() {
        createTeacherRequest = new CreateTeacherRequest();
        createTeacherRequest.setFirstName("Иван");
        createTeacherRequest.setLastName("Иванов");
        createTeacherRequest.setMiddleName("Иванович");
    }

    @BeforeEach
    void setUpEdit() {
        editTeacherRequest = new EditTeacherRequest();
        editTeacherRequest.setFirstName("Петя");
        editTeacherRequest.setLastName("Петров");
        editTeacherRequest.setMiddleName("Петрович");
    }

    @Test
    void findById_shouldReturnTeacher_whenExists() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        TeacherResponse result = teacherService.findById(1L);
        assertEquals("Иван", result.getFirstName());

    }

    @Test
    void findById_shouldThrowNotFoundException_whenNotExists() {
        when(teacherRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> teacherService.findById(999L));
    }

    @Test
    void findAll_shouldReturnListOfTeachers() {
        Pageable pageable = PageRequest.of(0, 10);
        when(teacherRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(teacher)));
        List<TeacherResponse> result = teacherService.findAll(pageable);
        assertEquals(1, result.size());
    }

    @Test
    void create_shouldSaveAndReturnTeacher() {
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);
        TeacherResponse result = teacherService.create(createTeacherRequest);
        assertNotNull(result);
    }


    @Test
    void update_shouldUpdateTeacher_whenExists() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(teacher)).thenReturn(teacher);
        TeacherResponse result = teacherService.update(1L, editTeacherRequest);
        assertEquals("Петя", result.getFirstName());
    }

    @Test
    void delete_shouldDeleteTeacher_whenNoSchedules() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        teacherService.delete(1L);
        verify(teacherRepository).delete(teacher);
    }

    @Test
    void delete_shouldThrowException_whenHasSchedules() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(scheduleRepository.findByTeacher(teacher)).thenReturn(List.of(new Schedule()));
        assertThrows(ConflictException.class, () -> teacherService.delete(1L));
    }
}