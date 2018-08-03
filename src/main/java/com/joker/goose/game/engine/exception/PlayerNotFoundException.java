package com.joker.goose.game.engine.exception;

public class PlayerNotFoundException extends Exception {
    public PlayerNotFoundException(String name) {
        super("Player " + name + " not found");
    }
}
