package com.rental.motocicly.controllers;

import com.rental.motocicly.exception.UnauthorizedException;
import com.rental.motocicly.models.User;
import com.rental.motocicly.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:5173/", "http://localhost:19006/", "192.168.0.9:8081"})
@RequestMapping("/api/user")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping
    public ResponseEntity<?> getRentalsByUser(@RequestHeader(value = "Authorization") String token) {
        try {
            return ResponseEntity.ok(userService.getRentalsByUser(token));
        } catch (UnauthorizedException e) {
            return ResponseEntity.badRequest().body("Unauthorized: invalid token");
        }
    }
}
