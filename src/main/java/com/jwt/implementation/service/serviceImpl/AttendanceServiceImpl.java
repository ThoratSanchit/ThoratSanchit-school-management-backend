package com.jwt.implementation.service.impl;

import com.jwt.implementation.model.Attendance;
import com.jwt.implementation.model.ClassRoom;
import com.jwt.implementation.model.StudentProfile;
import com.jwt.implementation.repository.AttendanceRepository;
import com.jwt.implementation.repository.ClassRoomRepository;
import com.jwt.implementation.repository.StudentProfileRepository;
import com.jwt.implementation.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private StudentProfileRepository studentProfileRepository;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Override
    public Attendance markAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    @Override
    public List<Attendance> getAttendanceByStudent(Long studentId) {
        StudentProfile student = studentProfileRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return attendanceRepository.findByStudent(student);
    }

    @Override
    public List<Attendance> getAttendanceByClassRoomAndDate(Long classRoomId, String date) {
        ClassRoom classRoom = classRoomRepository.findById(classRoomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));
        return attendanceRepository.findByClassRoomAndDate(classRoom, LocalDate.parse(date));
    }
}
