package com.joker.game.board.space;

public class TheBridgeSpace implements Space {

    private final String name;
    private final Integer index;

    public TheBridgeSpace(String name, Integer index) {
        this.name = name;
        this.index = index;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer evaluateRule() {
        return 12;
    }

    public Integer getIndex() {
        return index;
    }
}
