package com.jwt.implementation.controller;

import com.jwt.implementation.authService.AuthService;
import com.jwt.implementation.config.JwtGeneratorValidator;
import com.jwt.implementation.dto.UserDto;
import com.jwt.implementation.model.User;
import com.jwt.implementation.repository.UserRepository;
import com.jwt.implementation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private JwtGeneratorValidator jwtGenVal;


    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User userEntity) {
        try {

            User user = userRepository.findByEmail(userEntity.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            System.out.println("Login attempt for: " + userEntity.getEmail());

            if (!passwordEncoder.matches(userEntity.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
            }

            String token = jwtGenVal.generateToken(
                    user.getName(),
                    user.getEmail(),
                    String.valueOf(user.getId()),
                    String.valueOf(user.getRole()));

            Map<String, Object> response = new HashMap<>();
            response.put("message", "User logged in successfully.");
            response.put("token", token);
            response.put("role",user.getRole());
            response.put("userId",user.getId());
            return ResponseEntity.ok(response);


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Login failed: " + e.getMessage());
        }
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            // Encode password
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating user: " + e.getMessage());
        }
    }


    @GetMapping("/decode-token")    // 😊  use to  extract user information
    public ResponseEntity<?> decodeToken(@RequestHeader("Authorization") String authHeader) {
        try {
            // Extract token from "Bearer <token>"
            String token = authHeader.substring(7);

            String username = jwtGenVal.extractUsername(token);
            String email = jwtGenVal.extractEmail(token);
            String userId = jwtGenVal.extractUserId(token);
            String roll = jwtGenVal.extractRoll(token);

            Map<String, Object> decodedInfo = new HashMap<>();
            decodedInfo.put("username", username);
            decodedInfo.put("email", email);
            decodedInfo.put("userId", userId);
            decodedInfo.put("role", roll);

            return ResponseEntity.ok(decodedInfo);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid or expired token"));
        }
    }
}
