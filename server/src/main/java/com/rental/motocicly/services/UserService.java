package com.rental.motocicly.services;

import com.rental.motocicly.exception.UnauthorizedException;
import com.rental.motocicly.models.Rental;
import com.rental.motocicly.models.User;
import com.rental.motocicly.repository.RentalRepository;
import com.rental.motocicly.repository.UserRepository;
import com.rental.motocicly.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final JWTUtil jwtUtil;
    private final RentalRepository rentalRepository;

    @Autowired
    public UserService(UserRepository userRepository, AuthService authService, JWTUtil jwtUtil, RentalRepository rentalRepository) {
        this.userRepository = userRepository;
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.rentalRepository = rentalRepository;
    }

    public User addUser(User user) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, user.getPassword());
        user.setPassword(hash);

        return userRepository.save(user);
    }

    public List<Rental> getRentalsByUser(String token) {
        if(authService.validationToken(token)) {
            String userId = jwtUtil.getKey(token);
            return rentalRepository.findByUserId(Long.valueOf(userId));
        } throw new UnauthorizedException("Unauthorized: invalid token");
    }
}
