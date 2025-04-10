package com.jwt.implementation.controller;

import com.jwt.implementation.authService.AuthService;
import com.jwt.implementation.config.JwtGeneratorValidator;
import com.jwt.implementation.dto.TeacherProfileDto;
import com.jwt.implementation.model.*;
import com.jwt.implementation.repository.*;
import com.jwt.implementation.responces.GenerateResponces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher-profile")
public class TeacherProfileConroller {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtGeneratorValidator jwtGenVal;
    @Autowired

    private SchoolServiceRepository schoolServiceRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TeacherProfileRepository teacherProfileRepository;


    @PostMapping("/create-teacher")
    public ResponseEntity<?> createTeacher(@RequestHeader("Authorization") String token, @RequestBody TeacherProfileDto teacher) {
        try {
            String email = AuthService.getEmailFromToken(token);
            if (email == null || email.isEmpty()) {
                return GenerateResponces.generateResponse("Invalid token", HttpStatus.UNAUTHORIZED, null);
            }

            User admin = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            School school = schoolServiceRepository.findById(admin.getSchool().getSchoolId())
                    .orElseThrow(() -> new RuntimeException("School not found"));


            User user=new User();

            user.setName(teacher.getName());
            user.setPhone(teacher.getPhoneNo());
            user.setEmail(teacher.getEmail());
            user.setPassword(passwordEncoder.encode(teacher.getPassword()));
            user.setRole(User.Role.TEACHER);
            user.setSchool(school);
            user = userRepository.save(user);

            System.out.println("School id is "+admin.getSchool().getSchoolId());


            TeacherProfile profile = new TeacherProfile();
            profile.setUser(user);
            profile.setQualification(teacher.getQualification());
            profile.setDepartment(teacher.getDepartment());
            profile.setSchool(school);


            TeacherProfile saved = teacherProfileRepository.save(profile);
            return GenerateResponces.generateResponse("Teacher created successfully", HttpStatus.CREATED, saved.getId());

        } catch (Exception e) {
            e.printStackTrace();
            return GenerateResponces.generateResponse("Error creating teacher", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
