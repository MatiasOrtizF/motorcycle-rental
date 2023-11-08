package com.rental.motocicly.services;

import com.rental.motocicly.exception.AlreadyExistsException;
import com.rental.motocicly.exception.ResourceNotFoundException;
import com.rental.motocicly.exception.UnauthorizedException;
import com.rental.motocicly.models.Motorcycle;
import com.rental.motocicly.models.Save;
import com.rental.motocicly.models.User;
import com.rental.motocicly.repository.MotorcycleRepository;
import com.rental.motocicly.repository.SaveRepository;
import com.rental.motocicly.repository.UserRepository;
import com.rental.motocicly.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaveService {

    private final SaveRepository saveRepository;
    private final AuthService authService;
    private final JWTUtil jwtUtil;
    private final MotorcycleRepository motorcycleRepository;
    private final UserRepository userRepository;

    @Autowired
    public SaveService(SaveRepository saveRepository, AuthService authService, JWTUtil jwtUtil,
                       MotorcycleRepository motorcycleRepository,
                       UserRepository userRepository) {
        this.saveRepository = saveRepository;
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.motorcycleRepository = motorcycleRepository;
        this.userRepository = userRepository;
    }

    public List<Save> getAllSaveMotorcycleByUserId(String token) {
        if(authService.validationToken(token)) {
            String userId = jwtUtil.getKey(token);
            return saveRepository.findByUserId(Long.valueOf(userId));
        } throw new UnauthorizedException("Unauthorized: invalid token");
    }

    public Save saveMotorcycle(String token, Long id) {
        if(authService.validationToken(token)) {
            String userId = jwtUtil.getKey(token);
            if(!saveRepository.existsByMotorcycleIdAndUserId(id, Long.valueOf(userId))) {
                Motorcycle motorcycle = motorcycleRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("The motorcycle with this id: " + id + " is incorrect"));
                User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()-> new ResourceNotFoundException("The user with this id: " + id + " is incorrect"));;

                Save save = new Save();
                save.setMotorcycle(motorcycle);
                save.setUser(user);

                return saveRepository.save(save);
            } throw new AlreadyExistsException("The user has already saved this motorcycle");
        } throw new UnauthorizedException("Unauthorized: invalid token");
    }
}
