package com.jwt.implementation.controller;

import com.jwt.implementation.model.School;
import com.jwt.implementation.model.User;
import com.jwt.implementation.repository.UserRepository;
import com.jwt.implementation.responces.GenerateResponces;
import com.jwt.implementation.service.SchoolService;
import com.jwt.implementation.service.UserService;
//import com.jwt.implementation.enums.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/create-school")
    public ResponseEntity<?> createSchool(@RequestBody School school) {
        try {
            // Validate school data
            if (school.getSchoolName() == null || school.getSchoolAddress() == null || school.getSchoolContact() == null) {
                return GenerateResponces.generateResponse("School details are required.", HttpStatus.BAD_REQUEST, null);
            }

            if (school.getAdmins() == null || school.getAdmins().isEmpty()) {
                return GenerateResponces.generateResponse("At least one admin is required.", HttpStatus.BAD_REQUEST, null);
            }

            // Save school first
            School savedSchool = schoolService.ctreateSchool(school);

            // Process each admin
            for (User admin : school.getAdmins()) {
                // Set required fields
                admin.setSchool(savedSchool);
                admin.setPassword(passwordEncoder.encode(admin.getPassword()));
                admin.setRole(User.Role.ADMIN);  // ← THIS IS CRUCIAL
                admin.setCreatedAt(LocalDateTime.now());

                userRepository.save(admin);
            }

            return GenerateResponces.generateResponse(
                    "School created successfully with School ID: " + savedSchool.getSchoolId(),
                    HttpStatus.OK,
                    savedSchool.getSchoolId()
            );
        } catch (Exception e) {
            return GenerateResponces.generateResponse(
                    "Error creating school: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }
}
