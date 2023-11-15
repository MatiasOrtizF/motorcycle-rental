package com.rental.motocicly.repository;

import com.rental.motocicly.models.Motorcycle;
import com.rental.motocicly.models.Rental;
import com.rental.motocicly.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    @Query("SELECT r FROM Rental r WHERE r.user.id = :userId")
    List<Rental> findByUserId(@Param("userId")Long userId);

    List<Rental> findByMotorcycle(Motorcycle motorcycle);
}
