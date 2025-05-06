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
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentProfileRepository studentProfileRepository;

    // ✅ 1. Mark Attendance (Only Teachers)
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
                        null
                );
            }

            StudentProfile student = studentProfileRepository.findById(dto.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            Attendance attendance = new Attendance();
            attendance.setStudent(student);
            attendance.setClassRoom(student.getClassRoom());
            attendance.setDate(LocalDate.parse(dto.getDate()));

            try {
                Attendance.Status status = Attendance.Status.valueOf(dto.getStatus().toUpperCase());
                attendance.setStatus(status);
            } catch (IllegalArgumentException e) {
                return GenerateResponces.generateResponse(
                        "Invalid status value",
                        HttpStatus.BAD_REQUEST,
                        null
                );
            }

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
                    null
            );
        }
    }

    // ✅ 2. Get Attendance for a Student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getAttendanceByStudent(@PathVariable Long studentId) {
        try {
            return GenerateResponces.generateResponse(
                    "Attendance fetched successfully",
                    HttpStatus.OK,
                    attendanceService.getAttendanceByStudent(studentId)
            );
        } catch (Exception e) {
            return GenerateResponces.generateResponse(
                    "Error fetching student attendance",
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }

    // ✅ 3. Get Attendance by Class and Date
    @GetMapping("/classroom/{classRoomId}/date/{date}")
    public ResponseEntity<?> getAttendanceByClassAndDate(@PathVariable Long classRoomId,
                                                         @PathVariable String date) {
        try {
            return GenerateResponces.generateResponse(
                    "Attendance fetched successfully",
                    HttpStatus.OK,
                    attendanceService.getAttendanceByClassRoomAndDate(classRoomId, date)
            );
        } catch (Exception e) {
            return GenerateResponces.generateResponse(
                    "Error fetching attendance",
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }

    // ✅ 4. Update Attendance
    @PutMapping("/update/{attendanceId}")
    public ResponseEntity<?> updateAttendanceStatus(@RequestHeader("Authorization") String token,
                                                    @PathVariable Long attendanceId,
                                                    @RequestBody AttendanceDto dto) {
        try {

            String email = AuthService.getEmailFromToken(token);
            User teacher = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));

            if (!teacher.getRole().equals(User.Role.TEACHER)) {
                return GenerateResponces.generateResponse(
                        "Only teachers can mark attendance",
                        HttpStatus.FORBIDDEN,
                        null
                );
            }

            Attendance updated = attendanceService.updateAttendance(attendanceId, dto.getStatus());
            return GenerateResponces.generateResponse(
                    "Attendance updated successfully",
                    HttpStatus.OK,
                    updated.getAttendanceId()
            );
        } catch (Exception e) {
            return GenerateResponces.generateResponse(
                    "Error updating attendance",
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }

    // ✅ 5. Delete Attendance
    @DeleteMapping("/delete/{attendanceId}")
    public ResponseEntity<?> deleteAttendance(@PathVariable Long attendanceId) {
        try {
            attendanceService.deleteAttendance(attendanceId);
            return GenerateResponces.generateResponse(
                    "Attendance deleted successfully",
                    HttpStatus.OK,
                    null
            );
        } catch (Exception e) {
            return GenerateResponces.generateResponse(
                    "Error deleting attendance",
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }

// calculate percentage data
    @GetMapping("/attendance-data/{studentId}")
    public ResponseEntity<?> getAttendanceData(
            @PathVariable Long studentId,
            @RequestParam int month,  // 1 = January, 12 = December
            @RequestParam int year
    ) {
        try {
            Map<String, Object> data = attendanceService.calculateAttendanceData(studentId, month, year);
            return GenerateResponces.generateResponse(
                    "Attendance percentage calculated successfully",
                    HttpStatus.OK,
                    data
            );
        } catch (Exception e) {
            return GenerateResponces.generateResponse(
                    "Error calculating percentage",
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }


    // ✅ Get Month-wise Attendance for a Student
    @GetMapping("/student/{studentId}/month")
    public ResponseEntity<?> getMonthlyAttendance(
            @PathVariable Long studentId,
            @RequestParam("month") int month,
            @RequestParam("year") int year) {

        try {
            Map<String, Object> data = attendanceService.getMonthlyAttendance(studentId, month, year);
            return GenerateResponces.generateResponse(
                    "Monthly attendance fetched successfully",
                    HttpStatus.OK,
                    data
            );
        } catch (Exception e) {
            return GenerateResponces.generateResponse(
                    "Error fetching monthly attendance",
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }


}
