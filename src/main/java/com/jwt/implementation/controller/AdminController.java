package com.jwt.implementation.controller;

import com.jwt.implementation.authService.AuthService;
import com.jwt.implementation.config.JwtGeneratorValidator;
import com.jwt.implementation.model.Admin;
import com.jwt.implementation.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {
//    @Autowired
//    private AuthenticationManager authManager;

    @Autowired
    private JwtGeneratorValidator jwtGenVal;


    @Autowired
    private AuthService authService;
    @Autowired
    private AdminRepository adminRepository;

    @PostMapping("/login")
    public String generateJwtToken(@RequestBody Admin admin) throws Exception {
        try {
//            authManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(admin
//                            .getFullName(),admin.getPassword())
//            );
        } catch (Exception ex) {
            throw new Exception("Invalid username/password");
        }

        // Fetch the correct user ID from the database
        Admin user = adminRepository.findByEmail(admin.getEmail());
        if (user == null) {
            return "user not found";
        }
        String token=jwtGenVal.generateToken(user.getFullName(), user.getEmail(), String.valueOf(user.getAdminId()), String.valueOf(user.getTypeOfUser()));
        return "user loggend successfull "+token;
    }

//    @GetMapping("/decode-admin")
//    public ResponseEntity<Admin> getAdminDetails(@RequestHeader("Authorization") String token) {
//        try {
//            Admin findAdmin = jwtUtilService.extractAdminFromToken(token);
//            return ResponseEntity.ok(findAdmin);
//        } catch (Exception e) {
//            return ResponseEntity.status(400).body(null); // Return bad request response if token is invalid
//        }
//    }

    @GetMapping("/decode-token")
    public ResponseEntity<?> decodeToken(@RequestHeader("Authorization") String token) {
        try {
           String email= AuthService.getEmailFromToken(token);
           Admin admin=adminRepository.findByEmail(email);
            return ResponseEntity.ok(admin);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Invalid token");
        }
    }




}
