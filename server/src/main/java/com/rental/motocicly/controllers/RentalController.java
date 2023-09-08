package com.rental.motocicly.controllers;

import com.rental.motocicly.exception.ResourceNotFoundException;
import com.rental.motocicly.models.Rental;
import com.rental.motocicly.models.User;
import com.rental.motocicly.repository.RentalRepository;
import com.rental.motocicly.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
public class RentalController {
    @Autowired
    private RentalRepository rentalRepository;

    @GetMapping("/rental")
    public List<Rental> getAllRental() {
        return rentalRepository.findAll();
    }

    @GetMapping("/rental/{userId}")
    public  List<Rental> getUserRental(@PathVariable Long userId) {
        List <Rental> list = rentalRepository.findByUserId(userId);
            return list;
    }

    @PostMapping("/rental")
    public Rental addRental(@RequestBody Rental rental) {
        return rentalRepository.save(rental);
    }
}
