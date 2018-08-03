package com.joker;

import com.joker.command.CLIGameListener;
import com.joker.command.CommandExecutor;
import com.joker.command.exception.CommandNotFoundException;
import com.joker.command.exception.GameStoppedException;
import com.joker.game.Die;
import com.joker.game.GooseGame;
import com.joker.game.board.Board;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello! This is The Goose Game!");

        GooseGame gooseGame = new GooseGame(new Board());
        gooseGame.addGameListener(new CLIGameListener(System.out));

        Die die = new Die(6);

        CommandExecutor commandExecutor = new CommandExecutor(gooseGame, die, System.out);

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
