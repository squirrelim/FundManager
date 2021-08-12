package com.example.fundmanager.exception;

import com.example.fundmanager.entity.Fund;
import com.example.fundmanager.entity.FundManager;

public class IllegalUpdatedFundManagerException extends IllegalArgumentException{
    private final FundManager manager;

    public IllegalUpdatedFundManagerException(FundManager manager){
        super(manager.toString() + " is an illegal Fund Manager for updating");
        this.manager = manager;
    }
}
