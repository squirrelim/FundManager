package com.example.fundmanager.exception;

import com.example.fundmanager.entity.Position;

public class IllegalUpdatedPositionException extends IllegalArgumentException{
    private final Position position;

    public IllegalUpdatedPositionException(Position positon){
        super(positon.toString() + " is an illegal Position for updating");
        this.position = positon;
    }
}
