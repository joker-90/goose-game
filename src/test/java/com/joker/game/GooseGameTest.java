package com.joker.game;

import com.joker.game.exception.PlayerAlreadyExistsException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class GooseGameTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private GameListener gameListener;

    private GooseGame gooseGame;

    @Before
    public void setUp() {
        gooseGame = new GooseGame();

        gameListener = mock(GameListener.class);

        gooseGame.addGameListener(gameListener);
    }

    @Test
    public void testAddPlayer() throws Exception {
        String playerName = "Pippo";

        gooseGame.addPlayer(playerName);

        assertThat(gooseGame.getPlayers(), hasEntry(playerName, 0));
        verifyZeroInteractions(gameListener);
    }

    @Test
    public void testAddExistingPlayerThrowsException() throws Exception {
        String playerName = "Pippo";

        gooseGame.addPlayer(playerName);

        expectedException.expect(PlayerAlreadyExistsException.class);
        gooseGame.addPlayer(playerName);
    }

    @Test
    public void testMovePlayerUpdatePlayerSpace() throws Exception {
        String playerName = "Pippo";
        gooseGame.addPlayer(playerName);

        gooseGame.movePlayer(playerName, Arrays.asList(4, 2));

        verify(gameListener).onPlayerMoved(playerName, 0, 6);
        assertThat(gooseGame.getPlayers().get(playerName), equalTo(6));
    }

    @Test
    public void testMovePlayerWinUpdatePlayerSpaceAndIsWinner() throws Exception {
        String playerName = "Pippo";
        gooseGame.addPlayer(playerName);

        gooseGame.movePlayer(playerName, Arrays.asList(60, 3));

        verify(gameListener).onPlayerMoved(playerName, 0, 63);
        verify(gameListener).onPlayerWin(playerName);
        assertThat(gooseGame.getPlayers().get(playerName), equalTo(63));
    }

    @Test
    public void testMovePlayerOverMaxSpaceBounceAndUpdatePlayerSpace() throws Exception {
        String playerName = "Pippo";
        gooseGame.addPlayer(playerName);

        gooseGame.movePlayer(playerName, Arrays.asList(60, 5));

        verify(gameListener).onPlayerMoved(playerName, 0, 63);
        verify(gameListener).onPlayerBounced(playerName, 0, 61);
        assertThat(gooseGame.getPlayers().get(playerName), equalTo(61));
    }

}