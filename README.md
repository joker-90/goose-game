# goose-game
The Goose Game Kata

## Compile game

JDK version 1.8 and maven 3 are required to build the game.
To compile and build the project follow this steps:

1. `git clone` the project in a directory.
2. `mvn clean package` on the project directory.

## Play game

To start game go to **target** directory under project directory and execute `java -jar goose-game-<POM_VERSION>.jar`

### Game commands

The commands are:

- `add player <PLAYER_NAME>` : add player to game.
- `move <PLAYER_NAME>` : rolls two dice and move player.
- `exit` : exit the game.

**Have Fun!!**  :smiley:
