package com.jwt.implementation.controller;

import com.jwt.implementation.authService.AuthService;
import com.jwt.implementation.dto.SubjectAssignmentDto;
import com.jwt.implementation.model.*;
import com.jwt.implementation.repository.*;
import com.jwt.implementation.responces.GenerateResponces;
import com.jwt.implementation.service.SubjectAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subject-assignment")
public class SubjectAssignmentController {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private TeacherProfileRepository teacherProfileRepository;

    @Autowired
    private SubjectAssignmentService subjectAssignmentService;

    @Autowired
    private UserRepository userRepository;
    @PostMapping("/assign")
    public ResponseEntity<?> assignSubject(@RequestHeader("Authorization") String token, @RequestBody SubjectAssignmentDto dto) {
        try {
            String email = AuthService.getEmailFromToken(token);
            if (email == null || email.isEmpty()) {
                return GenerateResponces.generateResponse("Invalid token", HttpStatus.UNAUTHORIZED, null);
            }
            User admin = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Admin not found"));

            Subject subject = subjectRepository.findById(dto.getSubjectId())
                    .orElseThrow(() -> new RuntimeException("Subject not found"));

            ClassRoom classRoom = classRoomRepository.findById(dto.getClassRoomId())
                    .orElseThrow(() -> new RuntimeException("ClassRoom not found"));

            TeacherProfile teacher = teacherProfileRepository.findById(dto.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));

            SubjectAssignment assignment = new SubjectAssignment();
            assignment.setSubject(subject);
            assignment.setClassRoom(classRoom);
            assignment.setTeacher(teacher);

            SubjectAssignment savedAssignment = subjectAssignmentService.create(assignment);

            return GenerateResponces.generateResponse(
                    "Subject '" + subject.getSubjectName() + "' assigned successfully to " +
                            "Teacher: " + teacher.getUser().getName() + " for " +
                            "Class: " + classRoom.getClassName() + "-" + classRoom.getSection(),
                    HttpStatus.CREATED,
                    savedAssignment.getId()
            );

        } catch (RuntimeException e) {
            return GenerateResponces.generateResponse(
                    "Error assigning subject: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST,
                    null
            );
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllAssignments() {
        try {
            return GenerateResponces.generateResponse(
                    "Successfully retrieved all subject assignments",
                    HttpStatus.OK,
                    subjectAssignmentService.getAllAssignment()
            );
        } catch (Exception e) {
            return GenerateResponces.generateResponse(
                    "Error retrieving assignments: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }
}