package com.school.service;

import com.school.dto.request.Edit.EditScheduleRequest;
import com.school.dto.request.create.CreateScheduleRequest;
import com.school.dto.response.ScheduleResponse;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    List<ScheduleResponse> findByClassAndDate(Long classId, LocalDate date, Pageable pageable);
    List<ScheduleResponse> findByTeacherAndDate(Long teacherId, LocalDate date, Pageable pageable);
    ScheduleResponse create(CreateScheduleRequest request);
    ScheduleResponse update(Long id, EditScheduleRequest request);
    String delete(Long id);
}