package com.joker.command;

import com.joker.game.GameListener;

import java.io.PrintStream;

public class CLIGameListener implements GameListener {

    private final PrintStream printStream;

    public CLIGameListener(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void onPlayerMoved(String name, Integer from, Integer to) {
        printStream.print(name
                + " moves from "
                + (from == 0 ? "Start" : from)
                + " to " + to + ". ");
    }

    @Override
    public void onPlayerBounced(String name, Integer from, Integer to) {
        printStream.print(name + " bounces! " + name + " returns to " + to + ". ");
    }

    @Override
    public void onPlayerWin(String name) {
        printStream.print(name + " Wins!!");
    }
}
