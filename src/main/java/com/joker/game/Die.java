package com.joker.game;

import java.util.Random;

public class Die {

    private final Random random;
    private final int size;

    public Die(int size) {
        this.size = size;
        this.random = new Random();
    }

    public Integer roll() {
        return random.ints(1, size + 1)
                .limit(1)
                .findFirst()
                .getAsInt();
    }
}
