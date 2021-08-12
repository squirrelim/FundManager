package com.example.fundmanager.exception;

import com.example.fundmanager.entity.Position;
import org.apache.catalina.Manager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PositionAlreadyInUseException extends IllegalArgumentException{
    private final Position position;

    public PositionAlreadyInUseException(Position position) {
        super(position.toString() + " already exists.");
        this.position = position;
    }
}
