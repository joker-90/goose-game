package com.joker.game;

import com.joker.game.exception.PlayerAlreadyExistsException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GooseGame {

    private final Map<String, Integer> players;

    public GooseGame() {
        this.players = new HashMap<>();
    }

    public void addPlayer(String name) throws PlayerAlreadyExistsException {
        if (players.containsKey(name)) {
            throw new PlayerAlreadyExistsException("Player " + name + "already exist");
        }
        players.put(name, 0);
    }

    public Map<String, Integer> getPlayers() {
        return Collections.unmodifiableMap(players);
    }
}
