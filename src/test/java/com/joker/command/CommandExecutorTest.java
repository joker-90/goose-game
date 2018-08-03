package com.joker.command;

import com.joker.command.exception.CommandNotFoundException;
import com.joker.command.exception.GameStoppedException;
import com.joker.game.Die;
import com.joker.game.GooseGame;
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
    private Die die;
    private PrintStream printStream;

    private CommandExecutor commandExecutor;

    @Before
    public void setUp() {
        gooseGame = mock(GooseGame.class);
        die = mock(Die.class);
        printStream = mock(PrintStream.class);

        commandExecutor = new CommandExecutor(gooseGame, die, printStream);
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
        when(die.roll()).thenReturn(4, 2);

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