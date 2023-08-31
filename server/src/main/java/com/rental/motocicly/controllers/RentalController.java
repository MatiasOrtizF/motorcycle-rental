package com.rental.motocicly.controllers;

import com.rental.motocicly.models.Rental;
import com.rental.motocicly.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/rental")
    public Rental addRental(@RequestBody Rental rental) {
        return rentalRepository.save(rental);
    }
}