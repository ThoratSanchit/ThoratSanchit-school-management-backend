package com.jwt.implementation.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentResponseDto {
    private String name;
    private String email;
    private String phone;
    private String address;
    private LocalDate dateOfBirth;
    private String rollNumber;
    private Long classRoomId;
}
