package com.jwt.implementation.repository;

import com.jwt.implementation.model.Attendance;
import com.jwt.implementation.model.ClassRoom;
import com.jwt.implementation.model.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudent(StudentProfile student);
    List<Attendance> findByClassRoomAndDate(ClassRoom classRoom, LocalDate date);
}
