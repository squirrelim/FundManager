package com.example.fundmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SecurityNotFoundException extends IllegalArgumentException{
    private final Long securityId;

    public SecurityNotFoundException(Long id) {
        super("Security with id " + id + " not found.");
        this.securityId = id;
    }
}
