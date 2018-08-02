package com.joker.command;

import com.joker.command.exception.CommandNotFoundException;
import com.joker.command.exception.GameStoppedException;
import com.joker.game.Die;
import com.joker.game.GooseGame;
import com.joker.game.exception.PlayerAlreadyExistsException;
import com.joker.game.exception.PlayerNotFoundException;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandExecutor {

    private final GooseGame gooseGame;
    private final Die die;

    private final PrintStream printStream;

    public CommandExecutor(GooseGame gooseGame, Die die, PrintStream printStream) {
        this.gooseGame = gooseGame;
        this.die = die;
        this.printStream = printStream;
    }

    public void executeGameCommand(String userString) throws Exception {
        Command command = Command.getCommandFromString(userString);
        List<String> userArguments = command.getCommandArguments(userString);
        switch (command) {
            case ADD_PLAYER:
                handleAddPlayer(userArguments);
                break;
            case MOVE_PLAYER:
                handleMovePlayer(userArguments);
                break;
            case EXIT:
                throw new GameStoppedException();
            default:
                throw new CommandNotFoundException(userString);
        }
    }

    private void handleMovePlayer(List<String> userArguments) throws PlayerNotFoundException {
        String playerName = userArguments.get(0);

        List<Integer> rolls = Arrays.asList(die.roll(), die.roll());

        gooseGame.movePlayer(playerName, rolls);

        String rollsString = rolls.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        printStream.print(playerName + " rolls " + rollsString + ". ");
    }

    private void handleAddPlayer(List<String> userArguments) throws PlayerAlreadyExistsException {
        String playerName = userArguments.get(0);

        gooseGame.addPlayer(playerName);

        printStream.println(gooseGame.getPlayers().keySet()
                .stream()
                .collect(Collectors.joining(", ", "players: ", "")));
    }
}
