package com.joker.game;

import com.joker.game.exception.PlayerAlreadyExistsException;
import com.joker.game.exception.PlayerNotFoundException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

    public Integer movePlayer(String name, List<Integer> rolls) throws PlayerNotFoundException {
        Integer newSpace = players.computeIfPresent(name, (n, space) -> space + rolls.stream().mapToInt(Integer::intValue).sum());
        if (newSpace == null) {
            throw new PlayerNotFoundException(name);
        } else {
            return newSpace;
        }
    }

    public Map<String, Integer> getPlayers() {
        return Collections.unmodifiableMap(players);
    }
}
