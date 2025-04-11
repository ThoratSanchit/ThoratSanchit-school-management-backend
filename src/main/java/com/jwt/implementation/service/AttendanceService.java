package com.jwt.implementation.service;

import com.jwt.implementation.model.Attendance;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AttendanceService {
    Attendance markAttendance(Attendance attendance);
    List<Attendance> getAttendanceByStudent(Long studentId);
    List<Attendance> getAttendanceByClassRoomAndDate(Long classRoomId, String date);
}
