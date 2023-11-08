package com.rental.motocicly.repository;

import com.rental.motocicly.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.motorcycle.id = :motorcycleId")
    Float calculateRatingForMoto(@Param("motorcycleId")Long motorcycleId);

    boolean existsByMotorcycleIdAndUserId(Long motorcycleId, Long userId);
}
