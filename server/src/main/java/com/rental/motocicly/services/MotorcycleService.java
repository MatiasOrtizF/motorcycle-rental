package com.rental.motocicly.services;

import com.rental.motocicly.exception.RatingAlreadyExistsException;
import com.rental.motocicly.exception.ResourceNotFoundException;
import com.rental.motocicly.exception.UnauthorizedException;
import com.rental.motocicly.models.Motorcycle;
import com.rental.motocicly.models.Rating;
import com.rental.motocicly.models.User;
import com.rental.motocicly.repository.MotorcycleRepository;
import com.rental.motocicly.repository.RatingRepository;
import com.rental.motocicly.repository.UserRepository;
import com.rental.motocicly.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MotorcycleService {
    private final MotorcycleRepository motorcycleRepository;
    private final AuthService authService;
    private final RatingRepository ratingRepository;
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    @Autowired
    public MotorcycleService(MotorcycleRepository motorcycleRepository, AuthService authService, RatingRepository ratingRepository, JWTUtil jwtUtil, UserRepository userRepository) {
        this.motorcycleRepository = motorcycleRepository;
        this.authService = authService;
        this.ratingRepository = ratingRepository;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public List<Motorcycle> getAllMotorcycle(String token) {
        if(authService.validationToken(token)) {
            return motorcycleRepository.findAll();
        } throw new UnauthorizedException("Unauthorized: invalid token");
    }

    public Motorcycle getMotorcycle(Long id, String token) {
        if(authService.validationToken(token)) {
            return motorcycleRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("The motorcycle with this id: " + id + " is incorrect"));
        } throw new UnauthorizedException("Unauthorized: invalid token");
    }

    public Motorcycle addMotorcycle(Motorcycle motorcycle, String token) {
        if(authService.validationToken(token)) {
            return motorcycleRepository.save(motorcycle);
        } throw new UnauthorizedException("Unauthorized: invalid token");
    }

    public Motorcycle updateRatingMotorcycle(Long id, Integer newRating, String token) {
        if(authService.validationToken(token)) {
            String userId = jwtUtil.getKey(token);
            if(!ratingRepository.existsByMotorcycleIdAndUserId(id, Long.valueOf(userId))) {
                Motorcycle motorcycle = motorcycleRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("The motorcycle with this id: " + id + " is incorrect"));
                User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()-> new ResourceNotFoundException("The user with this id: " + id + " is incorrect"));;

                Rating rating = new Rating();
                rating.setRating(newRating);
                rating.setMotorcycle(motorcycle);
                rating.setUser(user);

                ratingRepository.save(rating);

                motorcycle.setRating(ratingRepository.calculateRatingForMoto(id));
                return motorcycleRepository.save(motorcycle);
            } throw new RatingAlreadyExistsException("The user has already rated this motorcycle.");
        } throw new UnauthorizedException("Unauthorized: invalid token");
    }
}