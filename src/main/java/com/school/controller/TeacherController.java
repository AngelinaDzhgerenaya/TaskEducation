package com.school.controller;

import com.school.dto.request.Edit.EditTeacherRequest;
import com.school.dto.request.create.CreateTeacherRequest;
import com.school.dto.response.TeacherResponse;
import com.school.model.Teacher;
import com.school.routes.EducationRoutes;
import com.school.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Преподаватели", description = "Работа с учителями")
@RestController
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @Operation(summary = "Получить всех учителей")
    @ApiResponse(responseCode = "200", description = "Список учителей получен")
    @GetMapping(EducationRoutes.TEACHERS)
    public List<TeacherResponse> getAll(@PageableDefault(size = 10, page =0) Pageable pageable) {
        return teacherService.findAll(pageable);
    }

    @Operation(summary = "Получить учителя по ID")
    @ApiResponse(responseCode = "200", description = "Учитель найден")
    @ApiResponse(responseCode = "404", description = "Учитель не найден")
    @GetMapping(EducationRoutes.TEACHERSBYID)
    public TeacherResponse getById(@PathVariable Long id) {
        return teacherService.findById(id);
    }


    @Operation(summary = "Создать учителя")
    @ApiResponse(responseCode = "200", description = "Учитель создан")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    @PostMapping(EducationRoutes.TEACHERS)
    public TeacherResponse create(@Valid @RequestBody CreateTeacherRequest request) {
        return teacherService.create(request);
    }

    @Operation(summary = "Создать случайного учителя")
    @ApiResponse(responseCode = "200", description = "Учитель создан")
    @PostMapping(EducationRoutes.TEACHERANDOM)
    public TeacherResponse createRandom() {
        return teacherService.createRandom();
    }

    @Operation(summary = "Обновить учителя")
    @ApiResponse(responseCode = "200", description = "Учитель обновлен")
    @ApiResponse(responseCode = "404", description = "Учитель не найден")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    @PatchMapping(EducationRoutes.TEACHERSBYID)
    public TeacherResponse update(@PathVariable Long id,@Valid @RequestBody EditTeacherRequest teacher) {
        return teacherService.update(id, teacher);
    }

    @Operation(summary = "Удалить учителя")
    @ApiResponse(responseCode = "200", description = "Учитель удалён")
    @ApiResponse(responseCode = "404", description = "Учитель не найден")
    @ApiResponse(responseCode = "409", description = "У учителя есть уроки")
    @DeleteMapping(EducationRoutes.TEACHERSBYID)
    public String delete(@PathVariable Long id) {
        return teacherService.delete(id);
    }
}