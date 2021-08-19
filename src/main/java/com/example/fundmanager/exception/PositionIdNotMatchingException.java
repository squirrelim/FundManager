package com.example.fundmanager.exception;

import com.example.fundmanager.entity.Fund;
import com.example.fundmanager.entity.Position;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PositionIdNotMatchingException extends IllegalArgumentException{
    private final Long id;
    private final Position position;

    public PositionIdNotMatchingException(Long id, Position position) {
        //super("Position id " + position.getPositionId().toString() + " is different from id " + id.toString() + " in path");
        this.id = id;
        this.position = position;
    }
}
