package com.joker.command;

import com.joker.command.exception.CommandNotFoundException;
import com.joker.command.exception.GameStoppedException;
import com.joker.game.GooseGame;
import com.joker.game.exception.PlayerAlreadyExistsException;
import com.joker.game.exception.PlayerNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class CommandExecutor {

    private final GooseGame gooseGame;

    public CommandExecutor(GooseGame gooseGame) {
        this.gooseGame = gooseGame;
    }

    public String executeGameCommand(String userString) throws Exception {
        Command command = Command.getCommandFromString(userString);
        List<String> userArguments = command.getCommandArguments(userString);
        switch (command) {
            case ADD_PLAYER:
                return handleAddPlayer(userArguments);
            case MOVE_PLAYER:
                return handleMovePlayer(userArguments);
            case EXIT:
                throw new GameStoppedException();
            default:
                throw new CommandNotFoundException(userString);
        }
    }

    private String handleMovePlayer(List<String> userArguments) throws PlayerNotFoundException {
        String playerName = userArguments.get(0);
        List<Integer> rolls = userArguments.subList(1, userArguments.size()).stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        Integer playerSpace = gooseGame.getPlayers().get(playerName);
        boolean bounced = gooseGame.movePlayer(playerName, rolls);
        Integer newPlayerSpace = gooseGame.getPlayers().get(playerName);

        String rollsString = rolls.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        return playerName + " rolls " + rollsString + ". " + playerName +
                " moves from " +
                (playerSpace == 0 ? "Start" : playerSpace) +
                " to " + (bounced ? +GooseGame.FINAL_SPACE + ". " + playerName + " bounces! " + playerName + " returns to " + newPlayerSpace : newPlayerSpace)
                + (gooseGame.getWinner().isPresent() ? ". " + gooseGame.getWinner().get() + " Wins!!" : "");
    }

    private String handleAddPlayer(List<String> userArguments) throws PlayerAlreadyExistsException {
        String playerName = userArguments.get(0);

        gooseGame.addPlayer(playerName);

        return gooseGame.getPlayers().keySet()
                .stream()
                .collect(Collectors.joining(", ", "players: ", ""));
    }
}
