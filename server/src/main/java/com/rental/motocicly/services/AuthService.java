package com.rental.motocicly.services;

import com.rental.motocicly.models.User;
import com.rental.motocicly.repository.UserRepository;
import com.rental.motocicly.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    @Autowired
    public AuthService(JWTUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil= jwtUtil;
        this.userRepository = userRepository;
    }

    public boolean validationToken (String token) {
        String userId = jwtUtil.getKey(token);
        return (userId != null);
    }

    public User validationEmail (String email) {
        return userRepository.findByEmail(email);
    }

    public String validationCredentials (User user) {
        User userLogged = validationEmail(user.getEmail());
        if(userLogged != null) {
            String passwordHashed = userLogged.getPassword();

            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            if(argon2.verify(passwordHashed, user.getPassword())) {
                String tokenJWT = jwtUtil.create(userLogged.getId().toString(), userLogged.getEmail());

                return tokenJWT;
            }
        }
        throw new RuntimeException("Email or password is incorrect");
    }
}
