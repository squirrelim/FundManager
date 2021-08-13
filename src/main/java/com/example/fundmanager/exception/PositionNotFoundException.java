package com.example.fundmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PositionNotFoundException extends IllegalArgumentException{
    private final Long positionId;

    public PositionNotFoundException(Long id) {
        super("Posion with id" + id + " not found.");
        this.positionId = id;
    }
}
