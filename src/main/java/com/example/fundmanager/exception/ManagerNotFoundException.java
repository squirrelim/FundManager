package com.example.fundmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ManagerNotFoundException extends IllegalArgumentException{
    private final Long managerId;

    public ManagerNotFoundException(Long id) {
        super("Manager with id" + id + " not found.");
        this.managerId = id;
    }
}
