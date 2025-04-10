package com.jwt.implementation.controller;

import com.jwt.implementation.authService.AuthService;
import com.jwt.implementation.dto.ClassRoomDto;
import com.jwt.implementation.model.ClassRoom;
import com.jwt.implementation.model.School;
import com.jwt.implementation.model.TeacherProfile;
import com.jwt.implementation.model.User;
import com.jwt.implementation.repository.ClassRoomRepository;
import com.jwt.implementation.repository.SchoolServiceRepository;
import com.jwt.implementation.repository.TeacherProfileRepository;
import com.jwt.implementation.repository.UserRepository;
import com.jwt.implementation.responces.GenerateResponces;
import com.jwt.implementation.service.ClassRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classroom")
public class ClassRoomController {

    @Autowired
    private SchoolServiceRepository schoolServiceRepository;

    @Autowired
    private TeacherProfileRepository teacherProfileRepository;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private ClassRoomService classRoomService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createClassRoom(@RequestHeader("Authorization") String token, @RequestBody ClassRoomDto dto) {

        String email = AuthService.getEmailFromToken(token);
        if (email == null || email.isEmpty()) {
            return GenerateResponces.generateResponse("Invalid token", HttpStatus.UNAUTHORIZED, null);
        }
        User admin=userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        School school = schoolServiceRepository.findById(admin.getSchool().getSchoolId())
                .orElseThrow(() -> new RuntimeException("School not found"));

        TeacherProfile teacher = teacherProfileRepository.findById(dto.classTeacherId)   //get the clasteacher id  by using the dropdoun getAll teacher api
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        ClassRoom classRoom = new ClassRoom();
        classRoom.setClassName(dto.className);
        classRoom.setSection(dto.section);
        classRoom.setSchool(school);
        classRoom.setClassTeacher(teacher);

       classRoomService.create(classRoom);
       return GenerateResponces.generateResponse("ClassRoom created successfully", HttpStatus.CREATED,classRoom.getId());

    }
}
