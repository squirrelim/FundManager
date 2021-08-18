package com.example.fundmanager.exception;

import com.example.fundmanager.entity.Fund;
import com.example.fundmanager.entity.Security;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalUpdatedFundException extends IllegalArgumentException{
    private final Fund fund;

    public IllegalUpdatedFundException(Fund fund){
        super(fund.toString() + " is an illegal Fund for updating");
        this.fund = fund;
    }
}
