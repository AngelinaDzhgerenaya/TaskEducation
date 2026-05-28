package com.school.repository;

import com.school.model.Schedule;
import com.school.model.SchoolClass;
import com.school.model.Teacher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findBySchoolClassAndDayOfWeek(SchoolClass schoolClass, DayOfWeek dayOfWeek, Pageable pageable);

    List<Schedule> findByTeacherAndDayOfWeek(Teacher teacher, DayOfWeek dayOfWeek, Pageable pageable);

    List<Schedule> findBySchoolClass(SchoolClass schoolClass);

    List<Schedule> findByTeacher(Teacher teacher);
}
