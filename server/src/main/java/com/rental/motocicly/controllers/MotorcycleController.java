package com.rental.motocicly.controllers;

import com.rental.motocicly.exception.ResourceNotFoundException;
import com.rental.motocicly.models.Motorcycle;
import com.rental.motocicly.repository.MotorcycleRepository;
import com.rental.motocicly.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
public class MotorcycleController {
    @Autowired
    private MotorcycleRepository motorcycleRepository;

    @Autowired
    private JWTUtil jwtUtil;

    @GetMapping("/motorcycle")
    public List<Motorcycle> getAllMotorcycle() {
        return motorcycleRepository.findAll();
    }

    @GetMapping("/motorcycle/{id}")
    public ResponseEntity<?> getMotorcycle(@PathVariable Long id, @RequestHeader(value="Authorization") String token) {
            String userId = jwtUtil.getKey(token);
            if(userId!=null) {
            Motorcycle motorcycle = motorcycleRepository.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("La moto con ese ID no existe: + id "));
            return ResponseEntity.ok(motorcycle);
            }
            return null;
    }

    @PostMapping("/motorcycle")
    public Motorcycle addMotorcycle(@RequestBody Motorcycle motorcycle) {
        return motorcycleRepository.save(motorcycle);
    }
}