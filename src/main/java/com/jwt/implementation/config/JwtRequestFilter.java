package com.jwt.implementation.config;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
public class JwtRequestFilter extends GenericFilterBean {

    private final JwtGeneratorValidator jwtGenVal;

    // Store email in thread-local storage (specific to the request)
    private static final ThreadLocal<String> emailHolder = new ThreadLocal<>();

    public JwtRequestFilter(JwtGeneratorValidator jwtGenVal) {
        this.jwtGenVal = jwtGenVal;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                String email = jwtGenVal.extractEmail(token);
                emailHolder.set(email); // Store email for this request
            } catch (Exception e) {
                emailHolder.remove(); // Clear in case of error
            }
        }
        chain.doFilter(request, response);
    }

    // Get the email for this request
    public static String getEmailFromRequest() {
        return emailHolder.get();
    }

    // Clear after request completes
    public static void clear() {
        emailHolder.remove();
    }
}

