package com.jwt.implementation.controller;

import com.jwt.implementation.model.Admin;
import com.jwt.implementation.model.School;
import com.jwt.implementation.responces.GenerateResponces;
import com.jwt.implementation.service.AdminService;
import com.jwt.implementation.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SchoolController {
    @Autowired
    private SchoolService schoolService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AdminService adminService;

    @PostMapping("/create-school")
    public ResponseEntity<?> createSchool(@RequestBody School school) {
        // Validate if school details are provided
        if (school.getSchoolName() == null || school.getSchoolAddress() == null || school.getSchoolContact() == null) {
            return GenerateResponces.generateResponse("School details are required.", HttpStatus.BAD_REQUEST,null);
        }


        if (school.getAdmins() == null || school.getAdmins().isEmpty()) {
            return GenerateResponces.generateResponse("At least one admin is required.", HttpStatus.BAD_REQUEST,null);
        }


        for (Admin admin : school.getAdmins()) {
            admin.setSchool(school);
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        }


        School savedSchool = schoolService.ctreateSchool(school);

        return GenerateResponces.generateResponse(
                "School created successfully with Admin ID: " + savedSchool.getAdmins().get(0).getAdminId() + " School id" + savedSchool.getSchoolId(),
                HttpStatus.OK,
                savedSchool.getSchoolId()
        );
    }


}
