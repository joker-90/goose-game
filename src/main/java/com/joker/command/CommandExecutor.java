package com.joker.command;

import com.joker.command.exception.CommandNotFoundException;
import com.joker.command.exception.GameStoppedException;
import com.joker.game.GooseGame;

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
                gooseGame.addPlayer(userArguments.get(0));
                return gooseGame.getPlayers().keySet()
                        .stream()
                        .collect(Collectors.joining(", ", "players: ", ""));
            case EXIT:
                throw new GameStoppedException();
            default:
                throw new CommandNotFoundException(userString);
        }
    }
}
