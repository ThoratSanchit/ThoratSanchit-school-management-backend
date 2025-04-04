package com.jwt.implementation.controller;

import com.jwt.implementation.authService.AuthService;
import com.jwt.implementation.config.JwtGeneratorValidator;
import com.jwt.implementation.model.Admin;
import com.jwt.implementation.model.ClassEntity;
import com.jwt.implementation.model.Teacher;
import com.jwt.implementation.repository.AdminRepository;
import com.jwt.implementation.repository.ClassModelRepository;
import com.jwt.implementation.repository.TeacherRepository;
import com.jwt.implementation.responces.GenerateResponces;
import com.jwt.implementation.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ClassModelRepository classModelRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JwtGeneratorValidator jwtGenVal;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TeacherRepository teacherRepository;

    @PostMapping("/create-teacher")
    public ResponseEntity<?> createTeacher(@RequestHeader("Authorization") String token,  @RequestBody Teacher teacher) {
        try {
            String email = AuthService.getEmailFromToken(token);
            if (email == null || email.isEmpty()) {
                return GenerateResponces.generateResponse("Invalid token", HttpStatus.UNAUTHORIZED, null);
            }

            Admin admin = adminRepository.findByEmail(email);
            if (admin == null) {
                return GenerateResponces.generateResponse("Admin not found", HttpStatus.NOT_FOUND, null);
            }

            // Validate Class Name
            if (teacher.getClassName() == null || teacher.getClassName().isEmpty()) {
                return GenerateResponces.generateResponse("Class name is required", HttpStatus.BAD_REQUEST, null);
            }


            ClassEntity classEntity = classModelRepository.findByClassName(teacher.getClassName());
            if (classEntity == null) {
                return GenerateResponces.generateResponse("Class not found", HttpStatus.NOT_FOUND, null);
            }

            teacher.setAdmin(admin);
            teacher.setClassEntity(classEntity);
            teacher.setSchool(admin.getSchool());
            teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));

            Teacher savedTeacher = teacherService.saveTeacher(teacher);
            return GenerateResponces.generateResponse("Teacher created successfully", HttpStatus.CREATED, savedTeacher.getTeacherId());

        } catch (Exception e) {
            e.printStackTrace();
            return GenerateResponces.generateResponse("Error creating teacher", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginTeacher(@RequestBody Teacher teacher) {
        try {
            if (teacher.getEmail() == null || teacher.getPassword() == null) {
                return GenerateResponces.generateResponse("Email and password are required", HttpStatus.BAD_REQUEST, null);
            }

            Teacher existingTeacher = teacherRepository.findByEmail(teacher.getEmail());
            if (existingTeacher == null) {
                return GenerateResponces.generateResponse("Teacher does not exist", HttpStatus.NOT_FOUND, null);
            }

            // Validate Password
            if (!passwordEncoder.matches(teacher.getPassword(), existingTeacher.getPassword())) {
                return GenerateResponces.generateResponse("Invalid password", HttpStatus.UNAUTHORIZED, null);
            }

            // Generate JWT Token
            String token = jwtGenVal.generateToken(
                    existingTeacher.getFullName(),
                    existingTeacher.getEmail(),
                    String.valueOf(existingTeacher.getTeacherId()),
                    String.valueOf(existingTeacher.getTypeOfUser())
            );

            return ResponseEntity.ok(token);

        } catch (Exception e) {
            e.printStackTrace();
            return GenerateResponces.generateResponse("Error logging in", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
