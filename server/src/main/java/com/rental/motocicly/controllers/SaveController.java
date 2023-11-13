package com.rental.motocicly.controllers;

import com.rental.motocicly.exception.AlreadyExistsException;
import com.rental.motocicly.exception.ResourceNotFoundException;
import com.rental.motocicly.exception.UnauthorizedException;
import com.rental.motocicly.exception.UserMismatchException;
import com.rental.motocicly.services.SaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:5173/", "http://localhost:19006/", "192.168.0.9:8081"})
@RequestMapping("/api/save")
@RestController
public class SaveController {

    private final SaveService saveService;

    @Autowired
    public SaveController(SaveService saveService) {
        this.saveService = saveService;
    }

    @GetMapping
    public ResponseEntity<?> getAllSave(@RequestHeader(value = "Authorization") String token) {
        try {
            return ResponseEntity.ok(saveService.getAllSaveMotorcycleByUserId(token));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: invalid token");
        }
    }

    @PostMapping
    public ResponseEntity<?> saveMotorcycle(@RequestParam Long motorcycleId, @RequestHeader(value = "Authorization") String token) {
        try {
            return ResponseEntity.ok(saveService.saveMotorcycle(token, motorcycleId));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The user has already saved this motorcycle");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Motorcycle does not exist");
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: invalid token");
        }
    }

    @DeleteMapping
    public ResponseEntity<String> unsaveMotorcycle(@RequestParam Long motorcycleId, @RequestHeader(value = "Authorization") String token) {
            try {
                saveService.unsaveMotorcycle(motorcycleId, token);
                return ResponseEntity.ok().build();
            } catch (UserMismatchException e) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Mismatch");
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Motorcycle or User does not exist");
            } catch (UnauthorizedException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: invalid token");
            }
    }
}
