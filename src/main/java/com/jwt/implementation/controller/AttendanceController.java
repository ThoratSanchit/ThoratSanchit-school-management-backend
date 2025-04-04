package com.jwt.implementation.controller;

import com.jwt.implementation.authService.AuthService;
import com.jwt.implementation.dto.AttendanceRequest;
import com.jwt.implementation.model.Attendance;
import com.jwt.implementation.model.Student;
import com.jwt.implementation.model.Teacher;
import com.jwt.implementation.repository.ClassModelRepository;
import com.jwt.implementation.repository.StudentRepository;
import com.jwt.implementation.repository.TeacherRepository;
import com.jwt.implementation.responces.GenerateResponces;
import com.jwt.implementation.service.AttendenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendenceService attendanceService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ClassModelRepository classModelRepository;

    @PostMapping("/mark")
    public ResponseEntity<?> markAttendance(@RequestHeader("Authorization") String token,
                                            @RequestBody AttendanceRequest request) {
        try {
            // Validate Token & Get Teacher
            String email = AuthService.getEmailFromToken(token);
            Teacher teacher = teacherRepository.findByEmail(email);
            if (teacher == null) {
                return GenerateResponces.generateResponse("Teacher not found", HttpStatus.NOT_FOUND, null);
            }

            // Find Student
            Student student = studentRepository.findById(request.getStudentId()).orElse(null);
            if (student == null) {
                return GenerateResponces.generateResponse("Student not found", HttpStatus.NOT_FOUND, null);
            }

            // Check if Student belongs to Teacher's Class
            if (!student.getClassName().equals(teacher.getClassEntity())) {
                return GenerateResponces.generateResponse("Unauthorized: Student does not belong to your class", HttpStatus.FORBIDDEN, null);
            }

            // Create Attendance Record
            Attendance attendance = new Attendance();
            attendance.setStudent(student);
            attendance.setClassName(student.getClassName());
            attendance.setDate(request.getDate());
            attendance.setStatus(request.getStatus());

            // Save Attendance
            attendanceService.markAttendance(attendance);

            return GenerateResponces.generateResponse("Attendance marked successfully", HttpStatus.OK, null);
        } catch (Exception e) {
            e.printStackTrace();
            return GenerateResponces.generateResponse("Error marking attendance", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
