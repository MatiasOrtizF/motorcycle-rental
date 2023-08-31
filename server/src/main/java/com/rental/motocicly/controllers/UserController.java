package com.rental.motocicly.controllers;

import com.rental.motocicly.models.User;
import com.rental.motocicly.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/user")
    public User addUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    /*@PostMapping("/login")
    public User login(@RequestBody User user) {
        return userRepository.authUser(user);
    }*/
}
