package com.rental.motocicly.services;

import com.rental.motocicly.exception.ResourceNotFoundException;
import com.rental.motocicly.exception.UnauthorizedException;
import com.rental.motocicly.models.Motorcycle;
import com.rental.motocicly.models.Rating;
import com.rental.motocicly.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
    private final AuthService authService;

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingService(AuthService authService, RatingRepository ratingRepository) {
        this.authService = authService;
        this.ratingRepository = ratingRepository;
    }

    public Rating addRating(Rating rating, String token) {
        if(authService.validationToken(token)) {
            return ratingRepository.save(rating);
        } throw new UnauthorizedException("Unauthorized: invalid token");
    }

    public Float getRating(Long motorcycleId, String token) {
        if(authService.validationToken(token)) {
            return ratingRepository.calculateRatingForMoto(motorcycleId);
        } throw new UnauthorizedException("Unauthorized: invalid token");
    }
}
