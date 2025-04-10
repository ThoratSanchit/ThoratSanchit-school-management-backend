package com.jwt.implementation.dto;

import com.jwt.implementation.model.User;
import lombok.Data;

@Data
public class TeacherProfileDto {
    private String name;
    private String email;
    private String password;
    private String qualification;
    private String department;
    private Long schoolId;
    private  String phoneNo;
    private User.Role role;
}
