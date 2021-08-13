package com.example.fundmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FundNotFoundException extends IllegalArgumentException{
    private final Long fundId;

    public FundNotFoundException(Long id) {
        super("Fund with id " + id + " not found.");
        this.fundId = id;
    }
}
