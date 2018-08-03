package com.joker.goose.game.engine.board.space;

import java.util.function.Function;

public interface Space {

    String getName();

    Function<Integer, Integer> getSpaceRule();
}
