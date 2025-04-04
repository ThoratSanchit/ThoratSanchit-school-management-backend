package com.jwt.implementation.service.serviceImpl;

import com.jwt.implementation.model.Attendance;
import com.jwt.implementation.repository.AttendenceRepository;
import com.jwt.implementation.service.AttendenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendenceServiceImpl implements AttendenceService {

    @Autowired
    private AttendenceRepository attendenceRepository;

    public void markAttendance(Attendance attendance) {
        attendenceRepository.save(attendance);
    }
}
