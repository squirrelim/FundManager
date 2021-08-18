package com.example.fundmanager.exception;

import com.example.fundmanager.entity.Position;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalUpdatedPositionException extends IllegalArgumentException{
    private final Position position;

    public IllegalUpdatedPositionException(Position positon){
        super(positon.toString() + " is an illegal Position for updating");
        this.position = positon;
    }
}
