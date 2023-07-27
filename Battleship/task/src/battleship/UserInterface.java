package battleship;

import java.sql.SQLOutput;

public class UserInterface {
    public void headSpace() {
        System.out.println();
    }
    public void startGame() {
        System.out.println();
        System.out.println("The game starts!");
    }

    public void shoot() {
        System.out.println();
        System.out.println("Take a shot!");
    }
    public void endGame() {
        System.out.println();
        System.out.println("You sank the last ship. You won. Congratulations!");
    }
    public void sankShip() {
        System.out.println();
        System.out.println("You sank a ship!");
    }
    public void hitShip() {
        System.out.println();
        System.out.println("You hit a ship!");
    }
    public void missShip() {
        System.out.println();
        System.out.println("You missed!");
    }
    public void player1() {
        System.out.println("Player 1, place your ships on the game field");
        System.out.println();
    }
    public void passTheTurn() {
        System.out.println();
        System.out.println("Press Enter and pass the move to another player");
    }
    public void player2() {
        System.out.println("Player 2, place your ships to the game field");
        System.out.println();
    }
    public void splitTheFields() {
        System.out.println("---------------------");
    }
    public void playerxTurn(Game game) {
        System.out.println();
        int playerNumber = game.getPlayerTurn();
        String message = "Player <replace>, it's your turn:";
        System.out.println(message.replace("<replace>", String.valueOf(playerNumber)));
    }
}
