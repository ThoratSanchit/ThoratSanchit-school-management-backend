package com.jwt.implementation.dto;

import com.jwt.implementation.model.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private User.Role role;
    private String phone;
    private LocalDateTime createdAt;
}