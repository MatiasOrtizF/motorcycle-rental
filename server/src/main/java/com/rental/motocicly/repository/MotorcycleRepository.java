package com.rental.motocicly.repository;

import com.rental.motocicly.models.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long> {
}
