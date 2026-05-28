package com.school.controller;


import com.school.dto.request.Edit.EditSchoolClassRequest;
import com.school.dto.request.create.CreateSchoolClassRequest;
import com.school.dto.response.SchoolClassResponse;
import com.school.routes.EducationRoutes;
import com.school.service.SchoolClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Tag(name = "Класс", description = "Работа с классами")
@RestController
@RequiredArgsConstructor
public class SchoolClassController {

    private final SchoolClassService schoolClassService;

    @Operation(summary = "Получить все классы")
    @ApiResponse(responseCode = "200", description = "Список классов получен")
    @GetMapping(EducationRoutes.SCHOOLCLASSES)
    public List<SchoolClassResponse> getAll(@PageableDefault(size = 10, page =0) Pageable pageable) {
        return schoolClassService.findAll(pageable);
    }

    @Operation(summary = "Получить класс по ID")
    @ApiResponse(responseCode = "200", description = "Класс найден")
    @ApiResponse(responseCode = "404", description = "Класс не найден")
    @GetMapping(EducationRoutes.SCHOOLCLASSESBYID)
    public SchoolClassResponse getById(@PathVariable Long id) {
        return schoolClassService.findById(id);
    }

    @Operation(summary = "Создать класс")
    @ApiResponse(responseCode = "200", description = "Класс создан")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    @PostMapping(EducationRoutes.SCHOOLCLASSES)
    public SchoolClassResponse create(@Valid @RequestBody CreateSchoolClassRequest request) {
        return schoolClassService.create(request);
    }

    @Operation(summary = "Обновить класс")
    @ApiResponse(responseCode = "200", description = "Класс обновлен")
    @ApiResponse(responseCode = "404", description = "Класс не найден")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    @PatchMapping(EducationRoutes.SCHOOLCLASSESBYID)
    public SchoolClassResponse update(@PathVariable Long id,@Valid @RequestBody EditSchoolClassRequest schoolClass) {
        return schoolClassService.update(id, schoolClass);
    }

    @Operation(summary = "Удалить класс")
    @ApiResponse(responseCode = "200", description = "Класс удалён")
    @ApiResponse(responseCode = "404", description = "Класс не найден")
    @ApiResponse(responseCode = "409", description = "У класса есть уроки")
    @DeleteMapping(EducationRoutes.SCHOOLCLASSESBYID)
    public String delete(@PathVariable Long id) {
        return schoolClassService.delete(id);
    }
}