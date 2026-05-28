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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final ScheduleRepository scheduleRepository;
    private final TeacherRepository teacherRepository;

    private final Random random = new Random();

    @Override
    public List<TeacherResponse> findAll(Pageable pageable) {
       return teacherRepository.findAll(pageable)
               .stream()
               .map(TeacherResponse::of)
               .collect(Collectors.toList());
    }

    @Override
    public TeacherResponse findById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() ->
                new NotFoundException("Такого учителя не существует"));
        return TeacherResponse.of(teacher);
    }

    @Override
    public TeacherResponse create(CreateTeacherRequest request) {
        return TeacherResponse.of(teacherRepository.save(request.entity()));
    }

    @Override
    public  TeacherResponse createRandom(){
        boolean isFemale = random.nextBoolean();
        Teacher teacher = new Teacher();

        String[] maleFirstNames = {"Александр", "Михаил", "Дмитрий", "Сергей", "Андрей", "Николай", "Иван", "Павел"};
        String[] femaleFirstNames = {"Александра", "Мария", "Елена", "Ольга", "Наталья", "Татьяна", "Светлана", "Анна"};
        String[] maleMiddlesNames = {"Александрович", "Михайлович", "Дмитриевич", "Сергеевич", "Андреевич", "Николаевич", "Павлович" };
        String[] femaleMiddlesNames = {"Александровна", "Михайловна", "Дмитриевна", "Сергеевна", "Андреевна", "Николаевна", "Ивановна", "Павловна"};
        String[] maleLastNames = {"Иванов", "Петров", "Сидоров", "Козлов", "Новиков", "Морозов", "Волков", "Соколов"};
        String[] femaleLastNames = {"Иванова", "Петрова", "Сидорова", "Козлова", "Новикова", "Морозова", "Волкова", "Соколова"};

        if (isFemale){
            teacher.setFirstName(femaleFirstNames[random.nextInt(maleFirstNames.length)]);
            teacher.setMiddleName(femaleMiddlesNames[random.nextInt(maleMiddlesNames.length)]);
            teacher.setLastName(femaleLastNames[random.nextInt(maleLastNames.length)]);
        }
        else {
            teacher.setFirstName(maleFirstNames[random.nextInt(maleFirstNames.length)]);
            teacher.setMiddleName(maleMiddlesNames[random.nextInt(maleMiddlesNames.length)]);
            teacher.setLastName(maleLastNames[random.nextInt(maleLastNames.length)]);
        }

        return TeacherResponse.of(teacherRepository.save(teacher));


    }

    @Override
    public TeacherResponse update(Long id, EditTeacherRequest request) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() ->
                new NotFoundException("Такого учителя не существует"));
        if(request.getFirstName()!=null) {
            teacher.setFirstName(request.getFirstName());
        }
        if(request.getLastName()!=null) {
            teacher.setLastName(request.getLastName());
        }
        if(request.getMiddleName()!=null) {
            teacher.setMiddleName(request.getMiddleName());
        }

        return TeacherResponse.of(teacherRepository.save(teacher));
    }

    @Override
    public String delete(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() ->
                new NotFoundException("Такого учителя не существует"));
        List<Schedule> schedule = scheduleRepository.findByTeacher(teacher);
        if (!schedule.isEmpty())
        {
            throw new ConflictException("У этого учителя есть уроки, нельзя удалить");
        }
        teacherRepository.delete(teacher);
        return "Учитель удален";
    }

}