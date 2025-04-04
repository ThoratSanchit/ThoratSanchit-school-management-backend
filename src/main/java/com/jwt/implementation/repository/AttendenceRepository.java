package com.jwt.implementation.repository;

import com.jwt.implementation.model.Attendance;
import com.jwt.implementation.model.ClassEntity;
import com.jwt.implementation.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendenceRepository  extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudentAndDate(Student student, LocalDate date);
    List<Attendance> findByClassNameAndDate(ClassEntity classEntity, LocalDate date);
}
