package com.jwt.implementation.controller;

import com.jwt.implementation.authService.AuthService;
import com.jwt.implementation.dto.StudentDto;
import com.jwt.implementation.dto.StudentResponseDto;
import com.jwt.implementation.model.*;
import com.jwt.implementation.repository.ClassRoomRepository;
import com.jwt.implementation.repository.StudentProfileRepository;
import com.jwt.implementation.repository.UserRepository;
import com.jwt.implementation.responces.GenerateResponces;
import com.jwt.implementation.service.StudentProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/student")
public class StudentProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StudentProfileService studentProfileService;
    @Autowired
    private StudentProfileRepository studentProfileRepository;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @PostMapping("/create-student")
    public ResponseEntity<?> createStudent(@RequestHeader("Authorization") String token, @RequestBody StudentDto dto) {
        try {
            // Extract email from token
            String email = AuthService.getEmailFromToken(token);
            if (email == null || email.isEmpty()) {
                return GenerateResponces.generateResponse("❌ Invalid token", HttpStatus.UNAUTHORIZED, null);
            }

            // Check if user is a TEACHER
            User teacher = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("❌ Teacher not found with email: " + email));

            if (!teacher.getRole().equals(User.Role.TEACHER)) {
                return GenerateResponces.generateResponse(
                        "⛔ Only teachers can create students",
                        HttpStatus.FORBIDDEN,
                        null);
            }

          Optional<User> checkStud = userRepository.findByEmail(dto.getEmail());
           if(checkStud.isPresent()){
               return GenerateResponces.generateResponse(
                       "✅ Student '" + dto.getName() + " already present in the class",
                       HttpStatus.BAD_REQUEST,
                       null
               );
           }

            // Create new User for student
            User user = new User();
            user.setName(dto.getName());
            user.setPhone(dto.getPhone());
            user.setEmail(dto.getEmail());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setRole(User.Role.STUDENT);
            user.setSchool(teacher.getSchool());
            user.setAddress(dto.getAddress());
            user.setDateOfBirth(dto.getDateOfBirth());
            user = userRepository.save(user);

            // Get the ClassRoom
            ClassRoom classRoom = classRoomRepository.findById(dto.getClassRoomId())
                    .orElseThrow(() -> new RuntimeException("❌ Class room not found with ID: " + dto.getClassRoomId()));

            // Create Student Profile
            StudentProfile profile = new StudentProfile();
            profile.setUser(user);
            profile.setClassRoom(classRoom);
            profile.setRollNumber(dto.getRollNumber());

            StudentProfile student = studentProfileService.createStudent(profile);

            return GenerateResponces.generateResponse(
                    "✅ Student '" + user.getName() + "created successfully in class " + classRoom.getClassName() + "-" + classRoom.getSection(),
                    HttpStatus.CREATED,
                    student.getStudentProfileId()
            );

        } catch (Exception e) {
            e.printStackTrace();
            return GenerateResponces.generateResponse("❌ Error creating student: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }


    // get student id  by user id.
    // this api take userId and then find its corresponding StudentProfile
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getStudentIdByUserId(@PathVariable Long userId) {
        Optional<StudentProfile> studentProfile = studentProfileRepository.findByUserId(userId);
        if (studentProfile.isPresent()) {
            return ResponseEntity.ok(studentProfile.get().getStudentProfileId());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student profile not found for user ID " + userId);
        }
    }

    // Show all the Student profile data
    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getStudentProfileByUserId(@PathVariable Long userId) {
        try {
            StudentResponseDto student = studentProfileService.findStudentProfileByUserId(userId);
            return new ResponseEntity<>(student, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return GenerateResponces.generateResponse("❌ Error fetching student: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
