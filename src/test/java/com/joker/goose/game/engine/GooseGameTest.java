package com.joker.goose.game.engine;

import com.joker.goose.game.engine.board.Board;
import com.joker.goose.game.engine.exception.PlayerAlreadyExistsException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class GooseGameTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private GameListener gameListener;

    private GooseGame gooseGame;
    private Board board;

    @Before
    public void setUp() {
        board = new Board();

        gameListener = mock(GameListener.class);

        gooseGame = new GooseGame(board);
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
    public void testMovePlayerUpdatePlayerSpaceAndNotifyMoved() throws Exception {
        String playerName = "Pippo";
        gooseGame.addPlayer(playerName);

        gooseGame.movePlayer(playerName, Arrays.asList(2, 2));

        verify(gameListener).onPlayerMoved(playerName, board.getSpace(0), board.getSpace(4));
        assertThat(gooseGame.getPlayers().get(playerName), equalTo(4));
    }

    @Test
    public void testMovePlayerOnBridgeSpaceUpdatePlayerSpaceAndNotifyMoved() throws Exception {
        String playerName = "Pippo";
        gooseGame.addPlayer(playerName);

        gooseGame.movePlayer(playerName, Arrays.asList(4, 2));

        verify(gameListener).onPlayerMoved(playerName, board.getSpace(0), board.getSpace(6));
        verify(gameListener).onPlayerJump(playerName, board.getSpace(12));
        assertThat(gooseGame.getPlayers().get(playerName), equalTo(12));
    }

    @Test
    public void testMovePlayerWinUpdatePlayerSpaceAndNotifyWin() throws Exception {
        String playerName = "Pippo";
        gooseGame.addPlayer(playerName);

        gooseGame.movePlayer(playerName, Arrays.asList(60, 3));

        verify(gameListener).onPlayerMoved(playerName, board.getSpace(0), board.getSpace(63));
        verify(gameListener).onPlayerWin(playerName);
        assertThat(gooseGame.getPlayers().get(playerName), equalTo(63));
    }

    @Test
    public void testMovePlayerOverMaxSpaceBounceAndUpdatePlayerSpaceAndNotifyBounced() throws Exception {
        String playerName = "Pippo";
        gooseGame.addPlayer(playerName);

        gooseGame.movePlayer(playerName, Arrays.asList(60, 5));

        verify(gameListener).onPlayerMoved(playerName, board.getSpace(0), board.getSpace(63));
        verify(gameListener).onPlayerBounced(playerName, board.getSpace(61));
        verifyNoMoreInteractions(gameListener);
        assertThat(gooseGame.getPlayers().get(playerName), equalTo(61));
    }

    @Test
    public void testMovePlayerOnGooseSpaceUpdatePlayerSpaceAndNotifyMoved() throws Exception {
        String playerName = "Pippo";
        gooseGame.addPlayer(playerName);

        gooseGame.movePlayer(playerName, Arrays.asList(4, 1));

        verify(gameListener).onPlayerMoved(playerName, board.getSpace(0), board.getSpace(5));
        assertThat(gooseGame.getPlayers().get(playerName), equalTo(10));
    }

    @Test
    public void testMovePlayerOnGooseSpaceMoveTwiceUpdatePlayerSpaceAndNotifyMoved() throws Exception {
        String playerName = "Pippo";
        gooseGame.addPlayer(playerName);

        gooseGame.movePlayer(playerName, Arrays.asList(4, 6));
        gooseGame.movePlayer(playerName, Arrays.asList(2, 2));

        verify(gameListener).onPlayerMoved(playerName, board.getSpace(0), board.getSpace(10));
        verify(gameListener).onPlayerJump(playerName, board.getSpace(22));
        assertThat(gooseGame.getPlayers().get(playerName), equalTo(22));
    }

    @Test
    public void testMovePlayerOnAlreadyOccupiedSpaceUpdateTwoPlayer() throws Exception {
        String playerName1 = "Pippo";
        gooseGame.addPlayer(playerName1);
        gooseGame.movePlayer(playerName1, Collections.singletonList(15));

        String playerName2 = "Pluto";
        gooseGame.addPlayer(playerName2);
        gooseGame.movePlayer(playerName2, Collections.singletonList(17));

        gooseGame.movePlayer(playerName1, Arrays.asList(1, 1));

        verify(gameListener).onPlayerMoved(playerName1, board.getSpace(15), board.getSpace(17));
        verify(gameListener).onPlayerPrank(playerName2, board.getSpace(17), board.getSpace(15));
        assertThat(gooseGame.getPlayers().get(playerName1), equalTo(17));
        assertThat(gooseGame.getPlayers().get(playerName2), equalTo(15));
    }
}