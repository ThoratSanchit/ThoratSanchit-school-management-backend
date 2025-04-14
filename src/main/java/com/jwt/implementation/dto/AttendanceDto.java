package com.jwt.implementation.dto;

import com.jwt.implementation.model.Attendance.Status;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AttendanceDto {
    private Long studentId;
    private Long classRoomId;
    private String date;
    private String status;
}
