package com.example.fundmanager.exception;

import com.example.fundmanager.entity.Fund;
import com.example.fundmanager.entity.FundManager;
import org.apache.catalina.Manager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ManagerAlreadyInUseException extends IllegalArgumentException{
    private final FundManager manager;

    public ManagerAlreadyInUseException(FundManager manager) {
        super(manager.toString() + " already exists.");
        this.manager = manager;
    }
}
