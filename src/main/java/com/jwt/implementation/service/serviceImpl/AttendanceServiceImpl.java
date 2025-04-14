package com.jwt.implementation.service.serviceImpl;

import com.jwt.implementation.model.Attendance;
import com.jwt.implementation.repository.AttendanceRepository;
import com.jwt.implementation.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public Attendance markAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    @Override
    public List<Attendance> getAttendanceByStudent(Long studentId) {
        return attendanceRepository.findByStudentStudentProfileId(studentId);
    }

    @Override
    public List<Attendance> getAttendanceByClassRoomAndDate(Long classRoomId, String date) {
        LocalDate localDate = LocalDate.parse(date);
        return attendanceRepository.findByClassRoomIdAndDate(classRoomId, localDate);
    }

    @Override
    public Attendance updateAttendance(Long attendanceId, String newStatus) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        attendance.setStatus(Attendance.Status.valueOf(newStatus));
        return attendanceRepository.save(attendance);
    }

    @Override
    public void deleteAttendance(Long attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        attendanceRepository.delete(attendance);
    }
}
