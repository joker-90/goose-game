package com.joker.goose.game.engine.board.space;

import java.util.function.Function;

public class TheGooseSpace extends DefaultSpace {

    public TheGooseSpace(String name, Integer index) {
        super(name, index);
    }

    @Override
    public Function<Integer, Integer> getSpaceRule() {
        return (Integer roll) -> getIndex() + roll;
    }
}
