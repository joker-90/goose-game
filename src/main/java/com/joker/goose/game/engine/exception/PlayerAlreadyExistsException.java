package com.joker.goose.game.engine.exception;

public class PlayerAlreadyExistsException extends Exception {
    public PlayerAlreadyExistsException(String name) {
        super("Player " + name + " already exist");
    }
}
