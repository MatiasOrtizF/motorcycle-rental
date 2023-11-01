package com.rental.motocicly.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RatingAlreadyExistsException extends RuntimeException{
    private static final Long serialVersionUID = 1L;

    public RatingAlreadyExistsException(String message) {
        super(message);
    }
}
