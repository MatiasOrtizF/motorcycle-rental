package com.rental.motocicly.controllers;

import com.rental.motocicly.exception.ResourceNotFoundException;
import com.rental.motocicly.exception.UnauthorizedException;
import com.rental.motocicly.exception.UserMismatchException;
import com.rental.motocicly.models.Rental;
import com.rental.motocicly.models.User;
import com.rental.motocicly.repository.RentalRepository;
import com.rental.motocicly.services.RentalService;
import com.rental.motocicly.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173/", "http://localhost:19006/", "192.168.0.9:8081"})
@RequestMapping("/api/rental")
@RestController
public class RentalController {

    private final RentalService rentalService;
    private final RentalRepository rentalRepository;

    @Autowired
    RentalController(RentalService rentalService,
                     RentalRepository rentalRepository) {
        this.rentalService = rentalService;
        this.rentalRepository = rentalRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAllRental(@RequestHeader(value = "Authorization") String token) {
        try {
            return ResponseEntity.ok(rentalService.getAllRental(token));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: invalid token");
        }
    }

    @PostMapping
    public ResponseEntity<?> addRental(@RequestHeader(value = "Authorization") String token, @RequestBody Rental rental) {
        try {
            return ResponseEntity.ok(rentalService.addRental(token, rental));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: invalid token");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteRental(@PathVariable Long id, @RequestHeader(value = "Authorization") String token) {
        try {
            rentalService.deleteRental(id, token);
            return ResponseEntity.ok().build();
        } catch (UserMismatchException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Mismatch");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rental does not exist");
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: invalid token");
        }
    }

    @GetMapping("{motorcycleId}")
    public ResponseEntity<?> getAllRentalByMotorcycle(@PathVariable Long motorcycleId, @RequestHeader(value = "Authorization") String token) {
        try {
            return ResponseEntity.ok(rentalService.getAllRentalByMotorcycle(motorcycleId, token));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Motorcycle does not exist");
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: invalid token");
        }
    }
}
