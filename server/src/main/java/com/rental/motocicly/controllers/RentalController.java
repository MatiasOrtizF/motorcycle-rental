package com.rental.motocicly.controllers;

import com.rental.motocicly.exception.ResourceNotFoundException;
import com.rental.motocicly.exception.UnauthorizedException;
import com.rental.motocicly.models.Rental;
import com.rental.motocicly.models.User;
import com.rental.motocicly.repository.RentalRepository;
import com.rental.motocicly.services.RentalService;
import com.rental.motocicly.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173/", "http://localhost:19006/", "192.168.0.9:8081"})
@RequestMapping("/api/rental")
@RestController
public class RentalController {

    private final RentalService rentalService;

    @Autowired
    RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<?> getAllRental(@RequestHeader(value = "Authorization") String token) {
        try {
            return ResponseEntity.ok(rentalService.getAllRental(token));
        } catch (UnauthorizedException e) {
            return ResponseEntity.badRequest().body("Unauthorized: invalid token");
        }
    }

    @PostMapping
    public ResponseEntity<?> addRental(@RequestHeader(value = "Authorization") String token, @RequestBody Rental rental) {
        try {
            return ResponseEntity.ok(rentalService.addRental(token, rental));
        } catch (UnauthorizedException e) {
            return ResponseEntity.badRequest().body("Unauthorized: invalid token");
        }
    }
}
