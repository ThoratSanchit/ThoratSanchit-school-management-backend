package com.jwt.implementation.dto;

import com.jwt.implementation.enums.AttendanceStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AttendanceRequest {
    private Long studentId;
    private LocalDate date;
    private AttendanceStatus status;
}
