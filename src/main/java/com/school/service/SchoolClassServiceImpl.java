package com.school.service;

import com.school.dto.request.Edit.EditSchoolClassRequest;
import com.school.dto.request.create.CreateSchoolClassRequest;
import com.school.dto.response.SchoolClassResponse;
import com.school.exception.ConflictException;
import com.school.exception.NotFoundException;
import com.school.model.Schedule;
import com.school.model.SchoolClass;
import com.school.repository.ScheduleRepository;
import com.school.repository.SchoolClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchoolClassServiceImpl implements SchoolClassService {

    private final ScheduleRepository scheduleRepository;
    private final SchoolClassRepository schoolClassRepository;

    @Override
    public List<SchoolClassResponse> findAll(Pageable pageable) {
        return  schoolClassRepository.findAll(pageable)
                .stream()
                .map(SchoolClassResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public SchoolClassResponse findById(Long id) {
        SchoolClass  schoolClass = schoolClassRepository.findById(id)
                .orElseThrow(() ->
                new NotFoundException("Такого класса не существует"));
        return SchoolClassResponse.of(schoolClass);
    }

    @Override
    public SchoolClassResponse create(CreateSchoolClassRequest request) {
        List<SchoolClass> schoolClass = schoolClassRepository.findByClassName(request.getClassName());
        if (!schoolClass.isEmpty()) {
            throw new ConflictException("Такой класс уже существует");
        }
        return SchoolClassResponse.of(schoolClassRepository.save(request.entity()));

    }

    @Override
    public SchoolClassResponse update(Long id, EditSchoolClassRequest request) {
        SchoolClass  schoolClass = schoolClassRepository.findById(id)
                .orElseThrow(() ->
                new NotFoundException("Такого класса не существует"));
        if (request.getClassName()!=null) {
            List<SchoolClass> schoolClasses = schoolClassRepository.findByClassName(request.getClassName());
        if (!schoolClasses.isEmpty()) {
            throw new ConflictException("Такой класс уже существует");
        }
            else{
            schoolClass.setClassName(request.getClassName());
        }
        }
        
        return SchoolClassResponse.of(schoolClassRepository.save(schoolClass));
    }

    @Override
    public String delete(Long id) {
        SchoolClass  schoolClass = schoolClassRepository.findById(id)
                .orElseThrow(() ->
                new NotFoundException("Такого класса не существует"));
        List<Schedule> schedule = scheduleRepository.findBySchoolClass(schoolClass);
        if (!schedule.isEmpty())
        {
            throw new ConflictException("У этого класса есть уроки, нельзя удалить");
        }
        schoolClassRepository.delete(schoolClass);
        return "Класс удален";
    }
}
