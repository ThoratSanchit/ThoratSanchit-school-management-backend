package com.jwt.implementation.authService;

import com.jwt.implementation.config.JwtGeneratorValidator;
import com.jwt.implementation.config.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JwtGeneratorValidator jwtGenVal;

    public static String getEmailFromToken(String Token) {
        return JwtRequestFilter.getEmailFromRequest(); // Get email from request filter
    }

}
