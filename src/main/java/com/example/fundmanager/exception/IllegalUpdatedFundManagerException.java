package com.example.fundmanager.exception;

import com.example.fundmanager.entity.Fund;
import com.example.fundmanager.entity.FundManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalUpdatedFundManagerException extends IllegalArgumentException{
    private final FundManager manager;

    public IllegalUpdatedFundManagerException(FundManager manager){
        super(manager.toString() + " is an illegal Fund Manager for updating");
        this.manager = manager;
    }
}
