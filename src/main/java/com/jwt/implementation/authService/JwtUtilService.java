package com.jwt.implementation.authService;

import com.jwt.implementation.config.JwtGeneratorValidator;
import com.jwt.implementation.model.User;
import com.jwt.implementation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtUtilService {

    @Autowired
    private JwtGeneratorValidator jwtGenVal;

    @Autowired
    private UserRepository userRepo;

    public User extractUserFromToken(String token) throws Exception {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new Exception("Invalid Token");
        }

        token = token.substring(7); // Remove "Bearer " prefix

        // Extract email from JWT
        String email = jwtGenVal.extractEmail(token);

        // Fetch user from database
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found");
        }

        return user; // Return the User object with all details
    }
}
