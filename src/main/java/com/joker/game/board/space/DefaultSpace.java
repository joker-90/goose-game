package com.joker.game.board.space;

public class DefaultSpace implements Space {

    private final String name;

    private final Integer index;

    public DefaultSpace(String name, Integer index) {
        this.name = name;
        this.index = index;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer evaluateRule() {
        return index;
    }

    public Integer getIndex() {
        return index;
    }

}
