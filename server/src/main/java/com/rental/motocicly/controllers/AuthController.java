package com.rental.motocicly.controllers;

import com.rental.motocicly.models.User;
import com.rental.motocicly.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:5173/", "http://localhost:19006/", "192.168.0.9:8081"})
@RestController
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("api/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            return ResponseEntity.ok(authService.validationCredentials(user));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Email or password is incorrect");
        }
    }
}
