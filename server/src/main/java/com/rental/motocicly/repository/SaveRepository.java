package com.rental.motocicly.repository;

import com.rental.motocicly.models.Save;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaveRepository extends JpaRepository<Save, Long> {

    List<Save> findByUserId(Long userId);
    boolean existsByMotorcycleIdAndUserId(Long motorcycleId, Long userId);
}
