package com.jwt.implementation.service;

import com.jwt.implementation.model.Attendance;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendenceService {
    public void markAttendance(Attendance attendance);
}
