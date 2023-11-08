package com.rental.motocicly.controllers;

import com.rental.motocicly.exception.AlreadyExistsException;
import com.rental.motocicly.exception.UnauthorizedException;
import com.rental.motocicly.services.SaveService;
import org.springframework.beans.factory.annotation.Autowired;
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
            return ResponseEntity.badRequest().body("Unauthorized: invalid token");
        }
    }

    @PostMapping
    public ResponseEntity<?> saveMotorcycle(@RequestParam Long motorcycleId, @RequestHeader(value = "Authorization") String token) {
        try {
            return ResponseEntity.ok(saveService.saveMotorcycle(token, motorcycleId));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.badRequest().body("The user has already saved this motorcycle");
        } catch (UnauthorizedException e) {
            return ResponseEntity.badRequest().body("Unauthorized: invalid token");
        }
    }
}
