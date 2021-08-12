package com.example.fundmanager.exception;

import com.example.fundmanager.entity.Security;

public class IllegalUpdatedSecurityException extends IllegalArgumentException{
    private final Security security;

    public IllegalUpdatedSecurityException(Security security){
        super(security.toString() + " is an illegal Security for updating");
        this.security = security;
    }
}
