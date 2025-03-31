package com.jwt.implementation.controller;

import com.jwt.implementation.authService.AuthService;
import com.jwt.implementation.model.Admin;
import com.jwt.implementation.model.ClassEntity;
import com.jwt.implementation.model.School;
import com.jwt.implementation.model.Subject;
import com.jwt.implementation.repository.AdminRepository;
import com.jwt.implementation.repository.SchoolServiceRepository;

import com.jwt.implementation.responces.GenerateResponces;
import com.jwt.implementation.service.ClassModelService;
import com.jwt.implementation.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/class")
public class ClassModelController {
    @Autowired
    private ClassModelService classModelService;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private SchoolServiceRepository schoolRepository;
    @Autowired
    private SubjectService subjectService;

    @PostMapping("/create-class")
    public ResponseEntity<?> createClass(@RequestHeader("Authorization") String token, @RequestBody ClassEntity classEntity) {
        try {
            String email = AuthService.getEmailFromToken(token);
            Admin admin = adminRepository.findByEmail(email);
            School school = admin.getSchool();

            // Set admin and school
            classEntity.setAdmin(admin);
            classEntity.setSchool(school);

            // Handle subjects if they exist
            List<Subject> subjects = classEntity.getSubjects();
            if (subjects != null && !subjects.isEmpty()) {
                // Set classEntity and school for each subject
                subjects.forEach(subject -> {
                    subject.setClassEntity(classEntity);  // Note: using classEntity before save
                    subject.setSchool(school);
                });
            }

            // Save the class (which will cascade to subjects if cascade is properly set)
            ClassEntity savedClass = classModelService.createClass(classEntity);

            return GenerateResponces.generateResponse("Class and Subjects created successfully",
                    HttpStatus.OK, savedClass.getClassId());
        } catch (Exception e) {
            e.printStackTrace();
            return GenerateResponces.generateResponse("Error creating class and subjects",
                    HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }


}
