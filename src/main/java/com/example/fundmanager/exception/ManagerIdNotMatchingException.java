package com.example.fundmanager.exception;

import com.example.fundmanager.entity.Fund;
import com.example.fundmanager.entity.FundManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ManagerIdNotMatchingException extends IllegalArgumentException{
    private final Long id;
    private final FundManager manager;

    public ManagerIdNotMatchingException(Long id, FundManager manager) {
        super("Manager id " + manager.getEmployeeId().toString() + " is different from id " + id.toString() + " in path");
        this.id = id;
        this.manager = manager;
    }
}
