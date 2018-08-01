package com.joker;

import com.joker.command.CommandExecutor;
import com.joker.command.exception.CommandNotFoundException;
import com.joker.command.exception.GameStoppedException;
import com.joker.game.GooseGame;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello! This is The Goose Game!");

        GooseGame gooseGame = new GooseGame();
        CommandExecutor commandExecutor = new CommandExecutor(gooseGame);
        Scanner scanner = new Scanner(System.in);
        boolean continueGame = true;
        do {
            System.out.print("Type command: ");
            String command = scanner.nextLine();
            try {
                System.out.println(commandExecutor.executeGameCommand(command));
            } catch (CommandNotFoundException cnfe) {
                System.out.println(cnfe.getMessage());
            } catch (GameStoppedException gse) {
                continueGame = false;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (continueGame);

        System.out.println("The Goose Game terminated, Bye!");
    }
}
