package com.school.service;

import com.school.dto.request.Edit.EditTeacherRequest;
import com.school.dto.request.create.CreateTeacherRequest;
import com.school.dto.response.TeacherResponse;
import com.school.model.Teacher;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeacherService {
    List<TeacherResponse> findAll(Pageable pageable);
    TeacherResponse findById(Long id);
    TeacherResponse create(CreateTeacherRequest request);
    TeacherResponse createRandom();
    TeacherResponse update(Long id, EditTeacherRequest request);
    String delete(Long id);
}