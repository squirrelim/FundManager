package com.example.fundmanager.exception;

import com.example.fundmanager.entity.Position;
import com.example.fundmanager.entity.Security;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SecurityAlreadyInUseException extends IllegalArgumentException{
    private final Security security;

    public SecurityAlreadyInUseException(Security security) {
        super(security.toString() + " already exists.");
        this.security = security;
    }
}
