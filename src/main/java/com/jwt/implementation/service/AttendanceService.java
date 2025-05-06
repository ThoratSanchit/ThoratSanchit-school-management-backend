package com.jwt.implementation.service;

import com.jwt.implementation.model.Attendance;

import java.util.List;
import java.util.Map;

public interface AttendanceService {
    Attendance markAttendance(Attendance attendance);

    List<Attendance> getAttendanceByStudent(Long studentId);

    List<Attendance> getAttendanceByClassRoomAndDate(Long classRoomId, String date);

    Attendance updateAttendance(Long attendanceId, String newStatus);

    void deleteAttendance(Long attendanceId);

    public Map<String, Object> calculateAttendanceData(Long studentId, int month, int year);


    Map<String, Object> getMonthlyAttendance(Long studentId, int month, int year);


}
