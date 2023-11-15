package com.rental.motocicly.services;

import com.rental.motocicly.exception.ResourceNotFoundException;
import com.rental.motocicly.exception.UnauthorizedException;
import com.rental.motocicly.exception.UserMismatchException;
import com.rental.motocicly.models.Motorcycle;
import com.rental.motocicly.models.Rental;
import com.rental.motocicly.models.User;
import com.rental.motocicly.repository.MotorcycleRepository;
import com.rental.motocicly.repository.RentalRepository;
import com.rental.motocicly.repository.UserRepository;
import com.rental.motocicly.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {

    private final AuthService authService;
    private final RentalRepository rentalRepository;
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;
    private final MotorcycleRepository motorcycleRepository;

    @Autowired
    public RentalService(AuthService authService, RentalRepository rentalRepository, JWTUtil jwtUtil, UserRepository userRepository,
                         MotorcycleRepository motorcycleRepository) {
        this.authService = authService;
        this.rentalRepository = rentalRepository;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.motorcycleRepository = motorcycleRepository;
    }

    public List<Rental> getAllRental(String token) {
        if(authService.validationToken(token)) {
            return rentalRepository.findAll();
        } throw new UnauthorizedException("Unauthorized: invalid token");
    }

    public Rental addRental(String token, Rental rental) {
        if(authService.validationToken(token)) {
            String userId = jwtUtil.getKey(token);
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()-> new ResourceNotFoundException("The user with this id: " + userId + " is incorrect"));
            rental.setUser(user);

            return rentalRepository.save(rental);
        } throw new UnauthorizedException("Unauthorized: invalid token");
    }

    public boolean deleteRental(Long id, String token) {
        if(authService.validationToken(token)) {
            String userId = jwtUtil.getKey(token);
            Rental rental = rentalRepository.findById(Long.valueOf(id)).orElseThrow(()-> new ResourceNotFoundException("The rental with this id: " + id + " is incorrect"));
            if(rental.getUser().getId().equals(Long.valueOf(userId))) {
                rentalRepository.delete(rental);

                return true;
            } throw new UserMismatchException("User mismatch");
        } throw new UnauthorizedException("Unauthorized: invalid token");
    }

    public List<Rental> getAllRentalByMotorcycle(Long motorcycleId, String token) {
        if(authService.validationToken(token)) {
            Motorcycle motorcycle = motorcycleRepository.findById(motorcycleId).orElseThrow(()-> new ResourceNotFoundException("The motorcycle with this id: " + motorcycleId + " is incorrect"));
            return rentalRepository.findByMotorcycle(motorcycle);
        } throw new UnauthorizedException("Unauthorized: invalid token");
    }
}
