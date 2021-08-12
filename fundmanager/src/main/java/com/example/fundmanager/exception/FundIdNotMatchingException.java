package com.example.fundmanager.exception;

import com.example.fundmanager.entity.Fund;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FundIdNotMatchingException extends IllegalArgumentException{
    private final Long id;
    private final Fund fund;

    public FundIdNotMatchingException(Long id, Fund fund) {
        super("Fund id " + fund.getFundId().toString() + " is different from id " + id.toString() + " in path");
        this.id = id;
        this.fund = fund;
    }
}
