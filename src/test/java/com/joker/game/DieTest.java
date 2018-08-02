package com.joker.game;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class DieTest {

    private Die die;

    @Before
    public void setUp() {
        die = new Die(6);
    }

    @Test
    public void rollReturnIntegerBetweenOneAndSix() {
        Integer result = die.roll();

        assertThat(result, is(both(greaterThanOrEqualTo(1)).and(lessThanOrEqualTo(6))));
    }
}