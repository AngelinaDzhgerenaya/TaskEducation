package com.school.service;

import com.school.dto.request.Edit.EditSchoolClassRequest;
import com.school.dto.request.create.CreateSchoolClassRequest;
import com.school.dto.response.SchoolClassResponse;
import com.school.model.SchoolClass;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SchoolClassService {
    List<SchoolClassResponse> findAll(Pageable pageable);
    SchoolClassResponse findById(Long id);
    SchoolClassResponse create(CreateSchoolClassRequest request);
    SchoolClassResponse update(Long id, EditSchoolClassRequest request);
    String delete(Long id);
}