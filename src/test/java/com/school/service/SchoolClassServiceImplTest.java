package com.school.service;

import com.school.dto.request.Edit.EditSchoolClassRequest;
import com.school.dto.request.create.CreateSchoolClassRequest;
import com.school.dto.response.ScheduleResponse;
import com.school.dto.response.SchoolClassResponse;
import com.school.dto.response.TeacherResponse;
import com.school.exception.ConflictException;
import com.school.exception.NotFoundException;
import com.school.model.Schedule;
import com.school.model.SchoolClass;
import com.school.repository.ScheduleRepository;
import com.school.repository.SchoolClassRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SchoolClassServiceImplTest {

    @Mock
    private SchoolClassRepository schoolClassRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private SchoolClassServiceImpl schoolClassService;

    private SchoolClass schoolClass;

    private CreateSchoolClassRequest createSchoolClassRequest;

    private EditSchoolClassRequest editSchoolClassRequest;

    @BeforeEach
    void setUp() {
        schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setClassName("9А");
    }

    @BeforeEach
    void setUpCreate() {
        createSchoolClassRequest = new CreateSchoolClassRequest();
        createSchoolClassRequest.setClassName("9А");
    }

    @BeforeEach
    void setUpEdit() {
        editSchoolClassRequest = new EditSchoolClassRequest();
        editSchoolClassRequest.setClassName("11Б");
    }

    @Test
    void findById_shouldReturnClass_whenExists() {
        when(schoolClassRepository.findById(1L)).thenReturn(Optional.of(schoolClass));
        SchoolClassResponse result = schoolClassService.findById(1L);
        assertEquals("9А", result.getClassName());
    }

    @Test
    void findById_shouldThrowNotFoundException_whenNotExists() {
        when(schoolClassRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> schoolClassService.findById(999L));
    }

    @Test
    void findAll_shouldReturnListOfClasses() {
        Pageable pageable = PageRequest.of(0, 10);
        when(schoolClassRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(schoolClass)));
        List<SchoolClassResponse> result = schoolClassService.findAll(pageable);
        assertEquals(1, result.size());
    }

    @Test
    void create_shouldSaveClass_whenNotExists() {
        when(schoolClassRepository.save(any(SchoolClass.class))).thenReturn(schoolClass);
        SchoolClassResponse result = schoolClassService.create(createSchoolClassRequest);
        assertNotNull(result);
    }

    @Test
    void create_shouldThrowConflict_whenAlreadyExists() {
        when(schoolClassRepository.save(any(SchoolClass.class))).thenThrow(ConflictException.class);
        assertThrows(ConflictException.class, () -> schoolClassService.create(createSchoolClassRequest));
    }

    @Test
    void update_shouldUpdateSchoolClass_whenExists() {
        when(schoolClassRepository.findById(1L)).thenReturn(Optional.of(schoolClass));
        when(schoolClassRepository.save(schoolClass)).thenReturn(schoolClass);
        SchoolClassResponse result = schoolClassService.update(1L, editSchoolClassRequest);
        assertEquals("11Б", result.getClassName());
    }

    @Test
    void delete_shouldDeleteClass_whenNoSchedules() {
        when(schoolClassRepository.findById(1L)).thenReturn(Optional.of(schoolClass));
        schoolClassService.delete(1L);
        verify(schoolClassRepository).delete(schoolClass);
    }

    @Test
    void delete_shouldThrowException_whenHasSchedules() {
        when(schoolClassRepository.findById(1L)).thenReturn(Optional.of(schoolClass));
        when(scheduleRepository.findBySchoolClass(schoolClass)).thenReturn(List.of(new Schedule()));
        assertThrows(ConflictException.class, () -> schoolClassService.delete(1L));
    }
}