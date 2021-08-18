package com.example.fundmanager.exception;

import com.example.fundmanager.entity.Security;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalUpdatedSecurityException extends IllegalArgumentException{
    private final Security security;

    public IllegalUpdatedSecurityException(Security security){
        super(security.toString() + " is an illegal Security for updating");
        this.security = security;
    }
}
