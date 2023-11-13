package com.rental.motocicly.controllers;

import com.rental.motocicly.exception.AlreadyExistsException;
import com.rental.motocicly.exception.UnauthorizedException;
import com.rental.motocicly.models.Motorcycle;
import com.rental.motocicly.services.MotorcycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:5173/", "http://localhost:19006/", "192.168.0.9:8081"})
@RequestMapping("/api/motorcycle")
@RestController
public class MotorcycleController {

    private final MotorcycleService motorcycleService;

    @Autowired
    public MotorcycleController(MotorcycleService motorcycleService) {
        this.motorcycleService = motorcycleService;
    }


    @GetMapping
    public ResponseEntity<?> getAllMotorcycle(@RequestHeader(value="Authorization") String token) {
        try {
            return ResponseEntity.ok(motorcycleService.getAllMotorcycle(token));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: invalid token");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getMotorcycle(@PathVariable Long id, @RequestHeader(value="Authorization") String token) {
        try {
            return ResponseEntity.ok(motorcycleService.getMotorcycle(id, token));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: invalid token");
        }
    }

    @PostMapping
    public ResponseEntity<?> addMotorcycle(@RequestBody Motorcycle motorcycle, @RequestHeader(value="Authorization") String token) {
        try {
            return ResponseEntity.ok(motorcycleService.addMotorcycle(motorcycle, token));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: invalid token");
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateRatingMotorcycle(@PathVariable Long id, @RequestParam Integer newRating, @RequestHeader(value = "Authorization") String token) {
        try {
            return ResponseEntity.ok(motorcycleService.updateRatingMotorcycle(id, newRating, token));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The user has already rated this motorcycle");
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: invalid token");
        }
    }
}