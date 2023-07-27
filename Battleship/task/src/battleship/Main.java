package battleship;

public class Main {

    public static void main(String[] args) {
        CreateField fieldCreation = new CreateField();
        UserActionValidation uav = new UserActionValidation();
        UserInterface ui = new UserInterface();
        Game game = new Game();

        // Asking player1 to place its ships on the field
        ui.player1();
        // Preparing the game
        game.prepareTheGame(fieldCreation, game, ui);

        // Game starts!
        //ui.startGame();
        // Starting the game
        //ui.shoot();

        while (!game.isGameWin()) {
            game.playGame(fieldCreation, ui, uav, game);
            game.swapTurns();
            game.passTheTurn(ui);
        }
    }
}
