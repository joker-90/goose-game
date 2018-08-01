package com.joker.game;

import com.joker.game.exception.PlayerAlreadyExistsException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
        String playerName = "Player name";

        gooseGame.addPlayer(playerName);

        assertThat(gooseGame.getPlayers(), hasEntry(playerName, 0));
    }

    @Test
    public void testAddExistingPlayerThrowsException() throws Exception {
        String playerName = "Player name";

        gooseGame.addPlayer(playerName);

        expectedException.expect(PlayerAlreadyExistsException.class);
        gooseGame.addPlayer(playerName);
    }
}