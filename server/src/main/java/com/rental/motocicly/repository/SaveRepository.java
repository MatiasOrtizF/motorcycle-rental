package com.rental.motocicly.repository;

import com.rental.motocicly.models.Motorcycle;
import com.rental.motocicly.models.Save;
import com.rental.motocicly.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaveRepository extends JpaRepository<Save, Long> {

    List<Save> findByUserId(Long userId);
    boolean existsByMotorcycleIdAndUserId(Long motorcycleId, Long userId);

    Save findByUserAndMotorcycle(User user, Motorcycle motorcycle);
}
