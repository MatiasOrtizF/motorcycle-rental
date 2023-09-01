package com.rental.motocicly.controllers;

import com.rental.motocicly.models.User;
import com.rental.motocicly.repository.UserRepository;
import com.rental.motocicly.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtil jwtUtil;

    @GetMapping("/user")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/user")
    public User addUser(@RequestBody User user) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, user.getPassword());
        user.setPassword(hash);

        return userRepository.save(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        List<User> list =  userRepository.findByEmail(user.getEmail());

        if(!list.isEmpty()) {
            User userLogged = list.get(0);
            String passwordHashed = userLogged.getPassword();

            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            if(argon2.verify(passwordHashed, user.getPassword())) {
                String tokenJwt = jwtUtil.create(userLogged.getId().toString(), userLogged.getEmail());
                return tokenJwt;
            }
        }
        return "FAIL";
    }
}
