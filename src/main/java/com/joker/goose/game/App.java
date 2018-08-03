package com.joker.goose.game;

import com.joker.goose.game.engine.Dice;
import com.joker.goose.game.engine.GooseGame;
import com.joker.goose.game.engine.board.Board;
import com.joker.goose.game.gui.cli.CLIGameListener;
import com.joker.goose.game.gui.cli.CommandExecutor;
import com.joker.goose.game.gui.cli.exception.CommandNotFoundException;
import com.joker.goose.game.gui.cli.exception.GameStoppedException;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello! This is The Goose Game!");

        GooseGame gooseGame = new GooseGame(new Board());
        gooseGame.addGameListener(new CLIGameListener(System.out));

        Dice dice = new Dice(6);

        CommandExecutor commandExecutor = new CommandExecutor(gooseGame, dice, System.out);

        try (Scanner scanner = new Scanner(System.in)) {
            boolean continueGame = true;
            do {
                System.out.print("Type command: ");
                String command = scanner.nextLine();
                try {
                    commandExecutor.executeGameCommand(command);
                } catch (CommandNotFoundException cnfe) {
                    System.out.println(cnfe.getMessage());
                } catch (GameStoppedException gse) {
                    continueGame = false;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                System.out.println();

            } while (continueGame);
        }

        System.out.println("The Goose Game terminated, Bye!");
    }
}
