package com.school.controller;

import com.school.dto.request.Edit.EditScheduleRequest;
import com.school.dto.request.create.CreateScheduleRequest;
import com.school.dto.response.ScheduleResponse;
import com.school.routes.EducationRoutes;
import com.school.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "Расписание", description = "Работа с расписанием")
@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(summary = "Получить список уроков для класса на день недели")
    @ApiResponse(responseCode = "200", description = "Список уроков для классов на день недели получен")
    @ApiResponse(responseCode = "404", description = "Класс не найден")

    @GetMapping(EducationRoutes.SCHEDULECLASS)
    public List<ScheduleResponse> getByClassAndDate(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PageableDefault(size = 10, page =0) Pageable pageable) {
        return scheduleService.findByClassAndDate(id, date, pageable);
    }

    @Operation(summary = "Получить список уроков для учителя на день недели")
    @ApiResponse(responseCode = "200", description = "Список уроков для учителя на день недели получен")
    @ApiResponse(responseCode = "404", description = "Урок не найден")

    @GetMapping(EducationRoutes.SCHEDULETEACHER)
    public List<ScheduleResponse> getByTeacherAndDate(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PageableDefault(size = 10, page =0) Pageable pageable) {
        return scheduleService.findByTeacherAndDate(id, date, pageable);
    }

    @Operation(summary = "Создать урок")
    @ApiResponse(responseCode = "200", description = "Урок создан")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    @PostMapping(EducationRoutes.SCHEDULECREATE)
    public ScheduleResponse create(@Valid @RequestBody CreateScheduleRequest request) {
        return scheduleService.create(request);
    }

    @Operation(summary = "Обновить урок")
    @ApiResponse(responseCode = "200", description = "Урок обновлен")
    @ApiResponse(responseCode = "404", description = "Урок не найден")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    @PatchMapping(EducationRoutes.SCHEDULEBYID)
    public ScheduleResponse update(@PathVariable Long id,@Valid  @RequestBody EditScheduleRequest request) {
        return scheduleService.update(id, request);
    }

    @Operation(summary = "Удалить урок")
    @ApiResponse(responseCode = "200", description = "Урок удалён")
    @ApiResponse(responseCode = "404", description = "Урок не найден")
    @DeleteMapping(EducationRoutes.SCHEDULEBYID)
    public String delete(@PathVariable Long id) {
        return scheduleService.delete(id);
    }
}