package com.joker.goose.game.engine;

import com.joker.goose.game.engine.board.Board;
import com.joker.goose.game.engine.board.space.Space;
import com.joker.goose.game.engine.exception.PlayerAlreadyExistsException;
import com.joker.goose.game.engine.exception.PlayerNotFoundException;

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

        Integer newSpaceIndex = actualSpaceIndex + rollsSum;

        Space actualSpace = board.getSpace(actualSpaceIndex);
        notifyAllOnPlayerMoved(playerName, actualSpace, board.getSpace(Math.min(newSpaceIndex, FINAL_SPACE)));

        newSpaceIndex = evaluateSpaceRule(playerName, rollsSum, newSpaceIndex);

        handleAnotherPlayerInSpace(playerName, actualSpaceIndex, newSpaceIndex);
    }

    private void handleAnotherPlayerInSpace(String actualPlayerName, Integer actualPlayerSpace, Integer spaceIndex) {
        players.entrySet().stream()
                .filter(e -> !e.getKey().equals(actualPlayerName))
                .filter(e -> e.getValue().equals(spaceIndex))
                .forEach(e -> {
                    e.setValue(actualPlayerSpace);
                    notifyAllOnPlayerPrank(e.getKey(), board.getSpace(spaceIndex), board.getSpace(actualPlayerSpace));
                });
    }

    private Integer evaluateSpaceRule(String playerName, Integer rollsSum, Integer newSpaceIndex) {

        if (newSpaceIndex > FINAL_SPACE) {
            newSpaceIndex = 2 * FINAL_SPACE - newSpaceIndex;
            notifyAllOnBouncedPlayer(playerName, board.getSpace(newSpaceIndex));
        } else if (newSpaceIndex == FINAL_SPACE) {
            notifyAllOnPlayerWin(playerName);
        }

        Integer evaluatedLandingSpaceRule = board.getSpace(newSpaceIndex).getSpaceRule().apply(rollsSum);
        if (!evaluatedLandingSpaceRule.equals(newSpaceIndex)) {
            notifyAllOnPlayerJump(playerName, board.getSpace(evaluatedLandingSpaceRule));
            return evaluateSpaceRule(playerName, rollsSum, evaluatedLandingSpaceRule);
        }

        players.put(playerName, evaluatedLandingSpaceRule);

        return evaluatedLandingSpaceRule;
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

    private void notifyAllOnPlayerPrank(String playerJokedName, Space from, Space to) {
        gameListeners.forEach(gameListener -> gameListener.onPlayerPrank(playerJokedName, from, to));
    }
}
