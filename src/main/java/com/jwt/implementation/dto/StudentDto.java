package com.jwt.implementation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentDto {
    private String name;
    private String email;
    private String password;  // You'll encrypt this later
    private String phone;

    private String rollNumber;
    private Long classRoomId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMMM d, yyyy")
    private LocalDate dateOfBirth;

    private  String address;
}
