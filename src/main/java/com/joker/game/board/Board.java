package com.joker.game.board;

import com.joker.game.GooseGame;
import com.joker.game.board.space.DefaultSpace;
import com.joker.game.board.space.GooseSpace;
import com.joker.game.board.space.Space;
import com.joker.game.board.space.TheBridgeSpace;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {

    private final List<Space> spaces;

    public Board() {
        spaces = IntStream.rangeClosed(0, GooseGame.FINAL_SPACE)
                .mapToObj(i -> {
                    switch (i) {
                        case 0:
                            return new DefaultSpace("Start", i);
                        case 6:
                            return new TheBridgeSpace("The Bridge", i);
                        case 5:
                        case 9:
                        case 14:
                        case 18:
                        case 23:
                        case 27:
                            return new GooseSpace("The Goose", i);
                        default:
                            return new DefaultSpace(Integer.toString(i), i);
                    }
                })
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    public Space getSpace(Integer index) {
        return spaces.get(index);
    }
}
