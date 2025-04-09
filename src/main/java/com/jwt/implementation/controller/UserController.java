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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String loginUser(@RequestBody UserDto userDto) throws Exception {
        try {
        } catch (Exception ex) {
            throw new Exception("Invalid username/password");
        }

        User user = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user == null) {
            return "user not found";
        }
        String token=jwtGenVal.generateToken(
                user.getName(),
                user.getEmail(),
                String.valueOf(user.getId()),
                String.valueOf(user.getRole()));

        return "user loggend successfull "+token;
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
}
