package com.jwt.implementation.dto;

import lombok.Data;

@Data
public class StudentDto {
    private String name;
    private String email;
    private String password;  // You'll encrypt this later
    private String phone;

    private String rollNumber;
    private Long classRoomId;
}
