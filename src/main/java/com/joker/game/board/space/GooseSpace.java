package com.joker.game.board.space;

import java.util.function.Function;

public class GooseSpace extends DefaultSpace {

    public GooseSpace(String name, Integer index) {
        super(name, index);
    }

    @Override
    public Function<Integer, Integer> getSpaceRule() {
        return (Integer roll) -> getIndex() + roll;
    }
}
