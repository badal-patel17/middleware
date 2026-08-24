package com.vfort.controller;

import com.vfort.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Login endpoint — React calls this first to get a JWT token.
 * Then passes token in Authorization: Bearer <token> header for all other calls.
 *
 * POST /api/auth/login
 * Body: { "username": "admin", "password": "admin" }
 * Returns: { "token": "eyJ..." }
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:85")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        // TODO: Replace with real user lookup from DB
        // For now hardcoded — wire to your users table later
        if ("admin".equals(username) && "admin".equals(password)) {
            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok(Map.of(
                "token", token,
                "username", username
            ));
        }

        return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
    }
}
