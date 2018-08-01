package com.joker.game;

import com.joker.game.exception.PlayerAlreadyExistsException;
import com.joker.game.exception.PlayerNotFoundException;

import java.util.*;

public class GooseGame {

    public static final int FINAL_SPACE = 63;
    private final Map<String, Integer> players;
    private String winner;

    public GooseGame() {
        this.players = new HashMap<>();
    }

    public void addPlayer(String name) throws PlayerAlreadyExistsException {
        if (players.containsKey(name)) {
            throw new PlayerAlreadyExistsException("Player " + name + "already exist");
        }
        players.put(name, 0);
    }

    public boolean movePlayer(String name, List<Integer> rolls) throws PlayerNotFoundException {
        Integer actualSpace = players.get(name);
        if (actualSpace == null) {
            throw new PlayerNotFoundException(name);
        }

        Integer rollsSum = rolls.stream()
                .mapToInt(Integer::intValue)
                .sum();

        boolean bounced = false;

        int newSpace = actualSpace + rollsSum;
        if (newSpace > FINAL_SPACE) {
            newSpace = 2 * FINAL_SPACE - newSpace;
            bounced = true;
        } else if (newSpace == FINAL_SPACE) {
            winner = name;
        }

        players.put(name, newSpace);

        return bounced;
    }

    public Optional<String> getWinner() {
        return Optional.ofNullable(winner);
    }

    public Map<String, Integer> getPlayers() {
        return Collections.unmodifiableMap(players);
    }
}
