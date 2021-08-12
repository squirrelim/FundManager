package com.example.fundmanager.exception;

import com.example.fundmanager.entity.Fund;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FundAlreadyInUseException extends IllegalArgumentException{
    private final Fund fund;

    public FundAlreadyInUseException(Fund fund) {
        super(fund.toString() + " already exists.");
        this.fund = fund;
    }
}
