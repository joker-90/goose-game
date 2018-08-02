package com.joker.game;

import com.joker.game.board.Board;
import com.joker.game.board.space.Space;
import com.joker.game.exception.PlayerAlreadyExistsException;
import com.joker.game.exception.PlayerNotFoundException;

import java.util.*;

public class GooseGame {

    public static final int FINAL_SPACE = 63;
    private final Map<String, Integer> players;
    private final List<GameListener> gameListeners;
    private final Board board;

    public GooseGame(Board board) {
        this.board = board;
        this.players = new HashMap<>();
        this.gameListeners = new ArrayList<>();
    }

    public void addPlayer(String name) throws PlayerAlreadyExistsException {
        if (players.containsKey(name)) {
            throw new PlayerAlreadyExistsException(name);
        }
        players.put(name, 0);
    }

    public void movePlayer(String playerName, List<Integer> rolls) throws PlayerNotFoundException {
        Integer actualSpaceIndex = players.get(playerName);
        if (actualSpaceIndex == null) {
            throw new PlayerNotFoundException(playerName);
        }

        Integer rollsSum = rolls.stream()
                .mapToInt(Integer::intValue)
                .sum();

        int newSpaceIndex = actualSpaceIndex + rollsSum;

        Space actualSpace = board.getSpace(actualSpaceIndex);
        notifyAllOnPlayerMoved(playerName, actualSpace, board.getSpace(Math.min(newSpaceIndex, FINAL_SPACE)));

        if (newSpaceIndex > FINAL_SPACE) {
            newSpaceIndex = 2 * FINAL_SPACE - newSpaceIndex;
            notifyAllOnBouncedPlayer(playerName, board.getSpace(newSpaceIndex));
        } else if (newSpaceIndex == FINAL_SPACE) {
            notifyAllOnPlayerWin(playerName);
        }

        Integer evaluatedRule = board.getSpace(newSpaceIndex).evaluateRule();
        if (!evaluatedRule.equals(newSpaceIndex)) {
            newSpaceIndex = evaluatedRule;
            notifyAllOnPlayerJump(playerName, board.getSpace(newSpaceIndex));
        }

        players.put(playerName, newSpaceIndex);
    }

    public void addGameListener(GameListener gameListener) {
        gameListeners.add(gameListener);
    }

    public Map<String, Integer> getPlayers() {
        return Collections.unmodifiableMap(players);
    }

    private void notifyAllOnPlayerMoved(String playerName, Space from, Space to) {
        gameListeners.forEach(gameListener -> gameListener.onPlayerMoved(playerName, from, to));
    }

    private void notifyAllOnBouncedPlayer(String playerName, Space to) {
        gameListeners.forEach(gameListener -> gameListener.onPlayerBounced(playerName, to));
    }

    private void notifyAllOnPlayerWin(String playerName) {
        gameListeners.forEach(gameListener -> gameListener.onPlayerWin(playerName));
    }

    private void notifyAllOnPlayerJump(String playerName, Space to) {
        gameListeners.forEach(gameListener -> gameListener.onPlayerJump(playerName, to));
    }
}
