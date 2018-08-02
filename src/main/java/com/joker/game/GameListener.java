package com.joker.game;

import com.joker.game.board.space.Space;

public interface GameListener {

    void onPlayerMoved(String playerName, Space from, Space to);

    void onPlayerBounced(String playerName, Space to);

    void onPlayerWin(String playerName);

    void onPlayerJump(String playerName, Space to);
}
