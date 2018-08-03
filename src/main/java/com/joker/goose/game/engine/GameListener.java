package com.joker.goose.game.engine;

import com.joker.goose.game.engine.board.space.Space;

public interface GameListener {

    void onPlayerMoved(String playerName, Space from, Space to);

    void onPlayerBounced(String playerName, Space to);

    void onPlayerWin(String playerName);

    void onPlayerJump(String playerName, Space to);

    void onPlayerPrank(String playerJokedName, Space from, Space to);
}
