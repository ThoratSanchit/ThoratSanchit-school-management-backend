package com.jwt.implementation.authService;

import com.jwt.implementation.config.JwtGeneratorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtUtilService {

    @Autowired
    private JwtGeneratorValidator jwtGenVal;

    public String getEmailFromToken(String token) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                throw new IllegalArgumentException("Invalid token format");
            }

            // Remove "Bearer " prefix
            token = token.substring(7);

            // Extract email
            return jwtGenVal.extractEmail(token);
        } catch (Exception e) {
            return null; // Return null if token is invalid
        }
    }

}
