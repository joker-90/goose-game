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

public class GooseGameTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private GooseGame gooseGame;

    @Before
    public void setUp() {
        gooseGame = new GooseGame();
    }

    @Test
    public void testAddPlayer() throws Exception {
        String playerName = "Pippo";

        gooseGame.addPlayer(playerName);

        assertThat(gooseGame.getPlayers(), hasEntry(playerName, 0));
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

        boolean result = gooseGame.movePlayer(playerName, Arrays.asList(4, 2));

        assertThat(result, equalTo(false));
        assertThat(gooseGame.getPlayers().get(playerName), equalTo(6));
        assertThat(gooseGame.getWinner().isPresent(), equalTo(false));
    }

    @Test
    public void testMovePlayerWinUpdatePlayerSpaceAndIsWinner() throws Exception {
        String playerName = "Pippo";
        gooseGame.addPlayer(playerName);

        boolean result = gooseGame.movePlayer(playerName, Arrays.asList(60, 3));

        assertThat(result, equalTo(false));
        assertThat(gooseGame.getPlayers().get(playerName), equalTo(63));
        assertThat(gooseGame.getWinner().isPresent(), equalTo(true));
        assertThat(gooseGame.getWinner().get(), equalTo(playerName));
    }

    @Test
    public void testMovePlayerOverMaxSpaceBounceAndUpdatePlayerSpace() throws Exception {
        String playerName = "Pippo";
        gooseGame.addPlayer(playerName);

        boolean result = gooseGame.movePlayer(playerName, Arrays.asList(60, 5));

        assertThat(result, equalTo(true));
        assertThat(gooseGame.getPlayers().get(playerName), equalTo(61));
        assertThat(gooseGame.getWinner().isPresent(), equalTo(false));
    }

}