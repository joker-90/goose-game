package com.joker.command;

import com.joker.command.exception.CommandNotFoundException;
import com.joker.command.exception.GameStoppedException;
import com.joker.game.Die;
import com.joker.game.GooseGame;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class CommandExecutorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private CommandExecutor commandExecutor;
    private GooseGame gooseGame;
    private Die die;

    @Before
    public void setUp() {
        gooseGame = mock(GooseGame.class);
        die = mock(Die.class);

        commandExecutor = new CommandExecutor(gooseGame, die);
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
        when(gooseGame.getPlayers()).thenReturn(Collections.singletonMap("Pluto", 10), Collections.singletonMap("Pluto", 16));
        when(gooseGame.movePlayer("Pluto", Arrays.asList(4, 2))).thenReturn(false);
        when(die.roll()).thenReturn(4, 2);

        String result = commandExecutor.executeGameCommand("move Pluto");

        verify(gooseGame).movePlayer("Pluto", Arrays.asList(4, 2));
        assertThat(result, equalTo("Pluto rolls 4, 2. Pluto moves from 10 to 16"));
    }

    @Test
    public void executeMovePlayerInStartSpaceGameCommandCallsGooseGameMovePlayer() throws Exception {
        when(gooseGame.getPlayers()).thenReturn(Collections.singletonMap("Pluto", 0), Collections.singletonMap("Pluto", 16));
        when(gooseGame.movePlayer("Pluto", Arrays.asList(4, 2))).thenReturn(false);
        when(die.roll()).thenReturn(4, 2);

        String result = commandExecutor.executeGameCommand("move Pluto");

        verify(gooseGame).movePlayer("Pluto", Arrays.asList(4, 2));
        assertThat(result, equalTo("Pluto rolls 4, 2. Pluto moves from Start to 16"));
    }

    @Test
    public void executeMovePlayerWinCommandCallsGooseGameMovePlayer() throws Exception {
        when(gooseGame.getPlayers()).thenReturn(Collections.singletonMap("Pluto", 60), Collections.singletonMap("Pluto", 63));
        when(gooseGame.movePlayer("Pluto", Arrays.asList(1, 2))).thenReturn(false);
        when(gooseGame.getWinner()).thenReturn(Optional.of("Pluto"));
        when(die.roll()).thenReturn(1, 2);

        String result = commandExecutor.executeGameCommand("move Pluto");

        verify(gooseGame).movePlayer("Pluto", Arrays.asList(1, 2));
        assertThat(result, equalTo("Pluto rolls 1, 2. Pluto moves from 60 to 63. Pluto Wins!!"));
    }

    @Test
    public void executeMovePlayerBounceCommandCallsGooseGameMovePlayer() throws Exception {
        when(gooseGame.getPlayers()).thenReturn(Collections.singletonMap("Pluto", 60), Collections.singletonMap("Pluto", 61));
        when(gooseGame.movePlayer("Pluto", Arrays.asList(3, 2))).thenReturn(true);
        when(gooseGame.getWinner()).thenReturn(Optional.empty());
        when(die.roll()).thenReturn(3, 2);

        String result = commandExecutor.executeGameCommand("move Pluto");

        verify(gooseGame).movePlayer("Pluto", Arrays.asList(3, 2));
        assertThat(result, equalTo("Pluto rolls 3, 2. Pluto moves from 60 to 63. Pluto bounces! Pluto returns to 61"));
    }

    @Test
    public void executeExitCommandThrowsException() throws Exception {
        expectedException.expect(GameStoppedException.class);
        commandExecutor.executeGameCommand("exit");
    }
}