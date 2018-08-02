package com.joker.game;

import com.joker.game.exception.PlayerAlreadyExistsException;
import com.joker.game.exception.PlayerNotFoundException;

import java.util.*;

public class GooseGame {

    private static final int FINAL_SPACE = 63;
    private final Map<String, Integer> players;
    private final List<GameListener> gameListeners;

    public GooseGame() {
        this.players = new HashMap<>();
        this.gameListeners = new ArrayList<>();
    }

    public void addPlayer(String name) throws PlayerAlreadyExistsException {
        if (players.containsKey(name)) {
            throw new PlayerAlreadyExistsException(name);
        }
        players.put(name, 0);
    }

    public void movePlayer(String name, List<Integer> rolls) throws PlayerNotFoundException {
        Integer actualSpace = players.get(name);
        if (actualSpace == null) {
            throw new PlayerNotFoundException(name);
        }

        Integer rollsSum = rolls.stream()
                .mapToInt(Integer::intValue)
                .sum();

        int newSpace = actualSpace + rollsSum;
        notifyAllOnPlayerMoved(name, actualSpace, Math.min(newSpace, FINAL_SPACE));
        if (newSpace > FINAL_SPACE) {
            newSpace = 2 * FINAL_SPACE - newSpace;
            notifyAllOnBouncedPlayer(name, actualSpace, newSpace);
        } else if (newSpace == FINAL_SPACE) {
            notifyAllOnPlayerWin(name);
        }

        players.put(name, newSpace);
    }

    public void addGameListener(GameListener gameListener) {
        gameListeners.add(gameListener);
    }

    private void notifyAllOnPlayerMoved(String name, Integer from, Integer to) {
        gameListeners.forEach(gameListener -> gameListener.onPlayerMoved(name, from, to));
    }

    private void notifyAllOnBouncedPlayer(String name, Integer from, Integer to) {
        gameListeners.forEach(gameListener -> gameListener.onPlayerBounced(name, from, to));
    }

    private void notifyAllOnPlayerWin(String name) {
        gameListeners.forEach(gameListener -> gameListener.onPlayerWin(name));
    }

    public Map<String, Integer> getPlayers() {
        return Collections.unmodifiableMap(players);
    }
}
