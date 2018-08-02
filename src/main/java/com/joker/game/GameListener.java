package com.joker.game;

public interface GameListener {

    void onPlayerMoved(String name, Integer from, Integer to);

    void onPlayerBounced(String name, Integer from, Integer to);

    void onPlayerWin(String name);
}
