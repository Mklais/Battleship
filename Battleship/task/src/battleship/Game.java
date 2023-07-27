package battleship;

import java.io.IOException;
import java.util.Scanner;

public class Game {
    private boolean validuserInput = false;
    private String[] userCoordinates;
    private int playerTurn = 1;
    private int userRow1 = 0;
    private int userCol1 = 0;
    private int userRow2 = 0;
    private int userCol2 = 0;
    private char shootRowAsChar = 'A';
    private int shootRow = 0;
    private int shootCol = 0;
    private boolean gameWin = false;
    public boolean isValiduserInput() {
        return validuserInput;
    }
    public void setValiduserInput(boolean validuserInput) {
        this.validuserInput = validuserInput;
    }
    public String[] getUserCoordinates() {
        return userCoordinates;
    }
    public void setUserCoordinates(String[] userCoordinates) {
        this.userCoordinates = userCoordinates;
    }
    public int getUserRow1() {
        return userRow1;
    }
    public void setUserRow1(int userRow1) {
        this.userRow1 = userRow1;
    }
    public int getUserCol1() {
        return userCol1;
    }
    public void setUserCol1(int userCol1) {
        this.userCol1 = userCol1;
    }
    public int getUserRow2() {
        return userRow2;
    }
    public void setUserRow2(int userRow2) {
        this.userRow2 = userRow2;
    }
    public int getUserCol2() {
        return userCol2;
    }
    public void setUserCol2(int userCol2) {
        this.userCol2 = userCol2;
    }
    public int getShootRow() {
        return shootRow;
    }
    public void setShootRow(int shootRow) {
        this.shootRow = shootRow;
    }
    public int getShootCol() {
        return shootCol;
    }
    public void setShootCol(int shootCol) {
        this.shootCol = shootCol;
    }
    public boolean isGameWin() {
        return gameWin;
    }
    public void setGameWin(boolean gameWin) {
        this.gameWin = gameWin;
    }
    public void setShootRowAsChar(char shootRowAsChar) {
        this.shootRowAsChar = shootRowAsChar;
    }
    public int getPlayerTurn() {
        return playerTurn;
    }
    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void prepareTheGame(CreateField fieldCreation, Game game, UserInterface ui) {
        // Preparing the game for 2 players
        int player = 1;
        while (player <= 2) {
            playerGamePreparation(fieldCreation, game, ui, player);

            // Pass on the turn
            player++;
            passTheTurn(ui);
            if (player == 1) {
                ui.player2();
            }
        }
    }
    private void playerGamePreparation(CreateField fieldCreation, Game game, UserInterface ui, int player) {
        // Creating an empty field
        String[][] field = fieldCreation.createfield(player);
        // Creating a foggy field
        String[][] foggyField = fieldCreation.createFogOfWar(player);

        // Setting the settings for player
        fieldCreation.setFieldsForPlayer(player, foggyField, field);

        // Printing an empty field
        fieldCreation.printField(player);

        // Asking user to input all its ship's
        fieldCreation.shipPlacementPrompt(game, fieldCreation, ui, player);

        // Setting the settings for player
        fieldCreation.setFieldsForPlayer(player, foggyField, field);
    }
    public void passTheTurn(UserInterface ui) {
        ui.passTheTurn();

        try {
            System.in.read();
                // Consume any characters until the "ENTER" key is pressed
            } catch (Exception e) {
        }
    }
    public void playGame(CreateField fieldCreation, UserInterface ui, UserActionValidation uav, Game game) {
        game.setValiduserInput(false);

        displayBattleGrounds(fieldCreation, ui);
        // Asking the user for a coordinate to shoot to
        while (!game.isValiduserInput()) {
            ui.playerxTurn(game);
            userShootCoordinates(ui);
            if (uav.userShootCoordinatesValidation(game)) {
                game.setValiduserInput(true);
                boolean shotResult = fieldCreation.shootShip(game);
                analyzeShot(game);
                getShipData(ui, shotResult);
            }
        }
    }
    public void swapTurns() {
        if (playerTurn == 1) {
            setPlayerTurn(2);
        } else {
            setPlayerTurn(1);
        }
    }
    private void displayBattleGrounds(CreateField fieldCreation, UserInterface ui) {
        if (playerTurn == 1) {
            fieldCreation.printFoggyField(2);
            ui.splitTheFields();
            fieldCreation.printField(1);
        } else if (playerTurn == 2) {
            fieldCreation.printFoggyField(1);
            ui.splitTheFields();
            fieldCreation.printField(2);
        }
    }
    public void decodeUserInputCords() {
        int row1 = (int) getUserCoordinates()[0].charAt(0) - 'A' + 1;
        int col1 = Integer.parseInt(getUserCoordinates()[0].substring(1));

        int row2 = (int) getUserCoordinates()[1].charAt(0) - 'A' + 1;
        int col2 = Integer.parseInt(getUserCoordinates()[1].substring(1));

        // Swap values if necessary so that row1 and col1 are smaller than or equal to row2 and col2
        if (row1 > row2) {
            int temp = row1;
            row1 = row2;
            row2 = temp;
        }

        if (col1 > col2) {
            int temp = col1;
            col1 = col2;
            col2 = temp;
        }

        setUserRow1(row1);
        setUserCol1(col1);
        setUserRow2(row2);
        setUserCol2(col2);
    }
    private void userShootCoordinates(UserInterface ui) {
        Scanner scanner = new Scanner(System.in);

        ui.headSpace();
        String userShootCoordinate = scanner.nextLine();

        // Setting the user's shooting coordinates
        setShootRowAsChar(userShootCoordinate.charAt(0));
        int row = userShootCoordinate.charAt(0) - 'A';
        setShootRow(row + 1);
        setShootCol(Integer.parseInt(userShootCoordinate.substring(1)));
    }
    private void getShipData(UserInterface ui, boolean shotResult) {

        boolean sunkShip = false;
        if (shotResult) {
            for (Ships ship : Ships.values()) {
                if (ship.getHitCount() == ship.getCellSize()) {
                    if (!ship.isSunk()) {
                        ship.setSunk(true);
                        sunkShip = true;
                    }
                }
            }
        }
        boolean isEnd = isEndGame();
        if (isEnd) {
            ui.endGame();
            setGameWin(true);
        } else if (sunkShip) {
            ui.sankShip();
        } else if (shotResult && !sunkShip) {
            ui.hitShip();
        } else if (!shotResult && !sunkShip) {
            ui.missShip();
        }
    }
    private boolean isEndGame() {
        int countSunkShips = 0;
        for (Ships ship : Ships.values()) {
            if (ship.isSunk()) {
                countSunkShips++;
            }
        }
        if (countSunkShips == 5) {
            return true;
        } else {
            return false;
        }
    }
    private void analyzeShot(Game game) {
        String targetCoordinate = String.valueOf(shootRowAsChar) + shootCol;
        for (Ships ship : Ships.values()) {
            if (ship.getPlayerOneShipCoordinates().contains(targetCoordinate)) {
                ship.setHitCount(ship.getHitCount() + 1);
            }
        }
    }
}
