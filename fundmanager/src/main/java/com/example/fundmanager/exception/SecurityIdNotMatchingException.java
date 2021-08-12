package com.example.fundmanager.exception;

import com.example.fundmanager.entity.Fund;
import com.example.fundmanager.entity.Security;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SecurityIdNotMatchingException extends IllegalArgumentException{
    private final Long id;
    private final Security security;

    public SecurityIdNotMatchingException(Long id, Security security) {
        super("Security id " + security.getSecurityId().toString() + " is different from id " + id.toString() + " in path");
        this.id = id;
        this.security = security;
    }
}
