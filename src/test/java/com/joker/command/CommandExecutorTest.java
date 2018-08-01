package com.joker.command;

import com.joker.command.exception.CommandNotFoundException;
import com.joker.command.exception.GameStoppedException;
import com.joker.game.GooseGame;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class CommandExecutorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private CommandExecutor commandExecutor;
    private GooseGame gooseGame;

    @Before
    public void setUp() {
        gooseGame = mock(GooseGame.class);

        commandExecutor = new CommandExecutor(gooseGame);
    }

    @Test
    public void executeNotFoundGameCommandThrowsException() throws Exception {
        expectedException.expect(CommandNotFoundException.class);
        commandExecutor.executeGameCommand("invalid command");
    }

    @Test
    public void executeAddPlayerGameCommandCallsGooseGameAddPlayer() throws Exception {
        when(gooseGame.getPlayers()).thenReturn(Collections.singletonMap("Pluto", 0));

        String result = commandExecutor.executeGameCommand("add player Pluto");

        verify(gooseGame).addPlayer("Pluto");
        assertThat(result, equalTo("players: Pluto"));
    }

    @Test
    public void executeMovePlayerGameCommandCallsGooseGameMovePlayer() throws Exception {
        when(gooseGame.getPlayers()).thenReturn(Collections.singletonMap("Pluto", 10));
        when(gooseGame.movePlayer("Pluto", Arrays.asList(4,2))).thenReturn(16);

        String result = commandExecutor.executeGameCommand("move Pluto 4, 2");

        verify(gooseGame).movePlayer("Pluto", Arrays.asList(4,2));
        assertThat(result, equalTo("Pluto rolls 4, 2. Pluto moves from 10 to 16"));
    }

    @Test
    public void executeMovePlayerInStartSpaceGameCommandCallsGooseGameMovePlayer() throws Exception {
        when(gooseGame.getPlayers()).thenReturn(Collections.singletonMap("Pluto", 0));
        when(gooseGame.movePlayer("Pluto", Arrays.asList(4,2))).thenReturn(16);

        String result = commandExecutor.executeGameCommand("move Pluto 4, 2");

        verify(gooseGame).movePlayer("Pluto", Arrays.asList(4,2));
        assertThat(result, equalTo("Pluto rolls 4, 2. Pluto moves from Start to 16"));
    }

    @Test
    public void executeExitCommandThrowsException() throws Exception {
        expectedException.expect(GameStoppedException.class);
        commandExecutor.executeGameCommand("exit");
    }
}