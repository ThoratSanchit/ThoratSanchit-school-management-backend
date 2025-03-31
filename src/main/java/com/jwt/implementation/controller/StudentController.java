package com.jwt.implementation.controller;

import com.jwt.implementation.authService.AuthService;
import com.jwt.implementation.model.ClassEntity;
import com.jwt.implementation.model.School;
import com.jwt.implementation.model.Student;
import com.jwt.implementation.model.Teacher;
import com.jwt.implementation.repository.TeacherRepository;
import com.jwt.implementation.responces.GenerateResponces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private TeacherRepository teacherRepository;

    //teacher create the student
    @PostMapping("/create-student")
    public ResponseEntity<?> creatreStudent(@RequestHeader("Authorization") String token, Student student){
        try{ //school teacher class
            String email= AuthService.getEmailFromToken(token);
            Teacher teacher=teacherRepository.findByEmail(email);
            if(teacher==null){
                return GenerateResponces.generateResponse("", HttpStatus.NOT_FOUND,null);
            }
            School school=teacher.getAdmin().getSchool();
            student.setSchool(school);
            student.setTeacher(teacher);
            student.setClassName(teacher.getClassEntity());
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return GenerateResponces.generateResponse("Error creating teacher", HttpStatus.INTERNAL_SERVER_ERROR,null);
        }
    }
}
