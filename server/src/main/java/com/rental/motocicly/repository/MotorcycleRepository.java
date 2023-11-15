package com.rental.motocicly.repository;

import com.rental.motocicly.models.Motorcycle;
import com.rental.motocicly.models.Save;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long> {

    @Query("SELECT m FROM Motorcycle m WHERE LOWER(m.motorcycleName) LIKE LOWER(CONCAT('%', :word, '%'))")
    List<Motorcycle> findByMotorcycleName(String word);
}
