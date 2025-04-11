package com.jwt.implementation.controller;

import com.jwt.implementation.authService.AuthService;
import com.jwt.implementation.dto.AttendanceDto;
import com.jwt.implementation.model.Attendance;
import com.jwt.implementation.model.StudentProfile;
import com.jwt.implementation.model.User;
import com.jwt.implementation.repository.StudentProfileRepository;
import com.jwt.implementation.repository.UserRepository;
import com.jwt.implementation.responces.GenerateResponces;
import com.jwt.implementation.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentProfileRepository studentProfileRepository;

    // ✅ Mark attendance (Only by teacher)
    @PostMapping("/mark")
    public ResponseEntity<?> markAttendance(@RequestHeader("Authorization") String token,
                                            @RequestBody AttendanceDto dto) {
        try {
            String email = AuthService.getEmailFromToken(token);
            User teacher = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));

            if (!teacher.getRole().equals(User.Role.TEACHER)) {
                return GenerateResponces.generateResponse(
                        "Only teachers can mark attendance",
                        HttpStatus.FORBIDDEN,
                        null);
            }

            StudentProfile student = studentProfileRepository.findById(dto.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            Attendance attendance = new Attendance();
            attendance.setStudent(student);
            attendance.setClassRoom(student.getClassRoom());
            attendance.setDate(LocalDate.parse(dto.getDate()));
            attendance.setStatus(dto.getStatus());

            Attendance saved = attendanceService.markAttendance(attendance);
            return GenerateResponces.generateResponse(
                    "Attendance marked successfully",
                    HttpStatus.CREATED,
                    saved.getAttendanceId()
            );

        } catch (Exception e) {
            return GenerateResponces.generateResponse(
                    "Error marking attendance: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }

    // ✅ Get attendance for student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getAttendanceByStudent(@PathVariable Long studentId) {
        try {
            return GenerateResponces.generateResponse(
                    "Attendance fetched successfully",
                    HttpStatus.OK,
                    attendanceService
                            .getAttendanceByStudent(studentId));
        } catch (Exception e) {
            return GenerateResponces.generateResponse(
                    "Error fetching student attendance",
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }

    // ✅ Get attendance by class and date
    @GetMapping("/classroom/{classRoomId}/date/{date}")
    public ResponseEntity<?> getAttendanceByClassAndDate(@PathVariable Long classRoomId, @PathVariable String date) {
        try {
            return GenerateResponces.generateResponse(
                    "Attendance fetched successfully",
                    HttpStatus.OK,
                    attendanceService.getAttendanceByClassRoomAndDate(classRoomId, date));
        } catch (Exception e) {
            return GenerateResponces.generateResponse(
                    "Error fetching attendance",
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }
}
