package com.joker.goose.game.gui.cli;

import com.joker.goose.game.engine.Dice;
import com.joker.goose.game.engine.GooseGame;
import com.joker.goose.game.gui.cli.exception.CommandNotFoundException;
import com.joker.goose.game.gui.cli.exception.GameStoppedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;

public class CommandExecutorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private GooseGame gooseGame;
    private Dice dice;
    private PrintStream printStream;

    private CommandExecutor commandExecutor;

    @Before
    public void setUp() {
        gooseGame = mock(GooseGame.class);
        dice = mock(Dice.class);
        printStream = mock(PrintStream.class);

        commandExecutor = new CommandExecutor(gooseGame, dice, printStream);
    }

    @Test
    public void executeNotFoundGameCommandThrowsException() throws Exception {
        expectedException.expect(CommandNotFoundException.class);
        commandExecutor.executeGameCommand("invalid command");
    }

    @Test
    public void executeAddPlayerGameCommandCallsGooseGameAddPlayer() throws Exception {
        when(gooseGame.getPlayers()).thenReturn(Collections.singletonMap("Pluto", 0));

        commandExecutor.executeGameCommand("add player Pluto");

        verify(gooseGame).addPlayer("Pluto");
        verify(printStream).print("players: Pluto");
    }

    @Test
    public void executeMovePlayerGameCommandCallsGooseGameMovePlayer() throws Exception {
        when(dice.roll()).thenReturn(4, 2);

        commandExecutor.executeGameCommand("move Pluto");

        verify(gooseGame).movePlayer("Pluto", Arrays.asList(4, 2));
        verify(printStream).print("Pluto rolls 4, 2. ");
    }

    @Test
    public void executeExitCommandThrowsException() throws Exception {
        expectedException.expect(GameStoppedException.class);
        commandExecutor.executeGameCommand("exit");
    }
}