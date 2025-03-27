package com.jwt.implementation.controller;

import com.jwt.implementation.authService.AuthService;
import com.jwt.implementation.config.JwtGeneratorValidator;
import com.jwt.implementation.model.Admin;
import com.jwt.implementation.model.School;
import com.jwt.implementation.model.Teacher;
import com.jwt.implementation.repository.AdminRepository;
import com.jwt.implementation.repository.SchoolServiceRepository;
import com.jwt.implementation.repository.TeacherRepository;
import com.jwt.implementation.responces.GenerateResponces;
import com.jwt.implementation.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JwtGeneratorValidator jwtGenVal;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TeacherRepository teacherRepository;

    @PostMapping("/create-teacher")
    public ResponseEntity<?> createTeacher(@RequestHeader("Authorization") String token, @RequestBody Teacher teacher) {
        try {
            // Extract email from token
            String email = AuthService.getEmailFromToken(token);
            Admin admin = adminRepository.findByEmail(email);

            if (admin == null) {
                return GenerateResponces.generateResponse("Admin not found", HttpStatus.BAD_REQUEST, null);
            }

            // Set admin and school for the teacher
            teacher.setAdmin(admin);


            // Hash password before saving
            teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));

            // Save teacher in DB
            Teacher savedTeacher = teacherService.saveTeacher(teacher);
            System.out.println("School Id " + admin.getSchool().getSchoolId());
            return GenerateResponces.generateResponse("Teacher created successfully", HttpStatus.OK, savedTeacher.getTeacherId());
        } catch (Exception e) {
            e.printStackTrace();
            return GenerateResponces.generateResponse("Error creating teacher", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PostMapping("/login")
    public String loginTeacher(@RequestBody Teacher teacher) {
        try {
            Teacher getTeacher = this.teacherRepository.findByEmail(teacher.getEmail());
            if (getTeacher == null) {
                return "Teacher not exist";
            }
            return jwtGenVal.generateToken(teacher.getFullName(), teacher.getEmail(), String.valueOf(teacher.getTeacherId()), String.valueOf(teacher.getTypeOfUser()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error login teacher";
    }

}
