package com.jwt.implementation.controller;

import com.jwt.implementation.authService.AuthService;
import com.jwt.implementation.model.Admin;
import com.jwt.implementation.model.ClassEntity;
import com.jwt.implementation.model.School;
import com.jwt.implementation.model.Teacher;
import com.jwt.implementation.repository.AdminRepository;
import com.jwt.implementation.repository.SchoolServiceRepository;
import com.jwt.implementation.repository.TeacherRepository;
import com.jwt.implementation.responces.GenerateResponces;
import com.jwt.implementation.service.ClassModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    private TeacherRepository teacherRepository;

    @PostMapping("/create-class")
    public ResponseEntity<?> createClass(@RequestHeader("Authorization") String token, @RequestBody ClassEntity classEntity) {
        try {

            String email = AuthService.getEmailFromToken(token);
            Teacher teacher = teacherRepository.findByEmail(email);
            classEntity.setTeacher(teacher);
            School school = teacher.getAdmin().getSchool();
            classEntity.setSchool(school);
            ClassEntity saveClass = classModelService.createClass(classEntity);
            return GenerateResponces.generateResponse("Teacher created successfully", HttpStatus.OK, saveClass.getClassId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GenerateResponces.generateResponse("Error creating class", HttpStatus.INTERNAL_SERVER_ERROR, null);
    }
}
