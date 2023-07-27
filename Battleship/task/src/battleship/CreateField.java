package battleship;

import java.util.Scanner;

public class CreateField {
    private final String EMPTY_FIELD_TILE = " ~";
    private final String SPACE_FIELD_TILE = " ";
    private final String SHIP_TILE = " O";
    private final String HIT_TILE = " X";
    private final String MISS_TILE = " M";
    private final int FIELD_COLUMN = 11;
    private final int FIELD_ROW = 11;
    private String[][] playerOneField;
    private String[][] playerTwoField;
    private String[][] playerOneFoggyField;
    private String[][] playerTwoFoggyField;
    UserActionValidation userActionValidation = new UserActionValidation();
    public String[][] getPlayerOneField() {
        return playerOneField;
    }
    public void setPlayerOneField(String[][] playerOneField) {
        this.playerOneField = playerOneField;
    }
    public String[][] getPlayerTwoField() {
        return playerTwoField;
    }
    public void setPlayerTwoField(String[][] playerTwoField) {
        this.playerTwoField = playerTwoField;
    }
    public String getSHIP_TILE() {
        return SHIP_TILE;
    }
    public void setPlayerTwoFoggyField(String[][] playerTwoFoggyField) {
        this.playerTwoFoggyField = playerTwoFoggyField;
    }
    public void setPlayerOneFoggyField(String[][] playerOneFoggyField) {
        this.playerOneFoggyField = playerOneFoggyField;
    }

    public String[][] createfield(int player) {
        // Creating an empty field
        // Initialize the field array
        String[][] field = new String[FIELD_ROW][FIELD_COLUMN];
        int colNumber = 1;
        char rowChar = 'A';

        for (int row = 0; row < FIELD_ROW; row++) {
            for (int col = 0; col < FIELD_COLUMN; col++) {
                if (row == 0 && col == 0) {
                    field[row][col] = SPACE_FIELD_TILE;
                } else if (row == 0 && col >= 1) {
                    field[row][col] = SPACE_FIELD_TILE + colNumber;
                    colNumber++;
                } else if (row > 0 && col == 0) {
                    field[row][col] = String.valueOf(rowChar);
                    rowChar++;
                } else {
                    field[row][col] = EMPTY_FIELD_TILE;
                }
            }
        }
        return field;
    }
    public String[][] createFogOfWar(int player) {
        // Creating an empty field
        // Initialize the field array
        String[][] foggyField = new String[FIELD_ROW][FIELD_COLUMN];
        int colNumber = 1;
        char rowChar = 'A';

        for (int row = 0; row < FIELD_ROW; row++) {
            for (int col = 0; col < FIELD_COLUMN; col++) {
                if (row == 0 && col == 0) {
                    foggyField[row][col] = SPACE_FIELD_TILE;
                } else if (row == 0 && col >= 1) {
                    foggyField[row][col] = SPACE_FIELD_TILE + colNumber;
                    colNumber++;
                } else if (row > 0 && col == 0) {
                    foggyField[row][col] = String.valueOf(rowChar);
                    rowChar++;
                } else {
                    foggyField[row][col] = EMPTY_FIELD_TILE;
                }
            }
        }
        return foggyField;
    }
    public void printField(int player) {
        String[][] field;
        if (player == 1) {
            field = playerOneField;
        } else if (player == 2) {
            field = playerTwoField;
        } else {
            throw new IllegalArgumentException("Invalid player number. Expected 1 or 2.");
        }

        for (int row = 0; row < field.length; row++) {
            for (int col = 0; col < field[row].length; col++) {
                System.out.print(field[row][col]);
            }
            System.out.println();
        }
    }
    public void printFoggyField(int player) {
        String foggyField[][];
        if (player == 1) {
            foggyField = playerOneFoggyField;
        } else if (player == 2) {
            foggyField = playerTwoFoggyField;
        } else {
            throw new IllegalArgumentException("Invalid player number. Expected 1 or 2.");
        }

        System.out.println();

        for (int row = 0; row < foggyField.length; row++) {
            for (int col = 0; col < foggyField[row].length; col++) {
                System.out.print(foggyField[row][col]);
            }
            System.out.println();
        }
    }
    private void updateFoggyField(boolean shotResult, Game game) {
        if (shotResult) {
            if (game.getPlayerTurn() == 1) {
                playerTwoFoggyField[game.getShootRow()][game.getShootCol()] = HIT_TILE;
            } else if (game.getPlayerTurn() == 2) {
                playerOneFoggyField[game.getShootRow()][game.getShootCol()] = HIT_TILE;
            }
        } else {
            if (game.getPlayerTurn() == 1) {
                playerTwoFoggyField[game.getShootRow()][game.getShootCol()] = MISS_TILE;
            } else if (game.getPlayerTurn() == 2) {
                playerOneFoggyField[game.getShootRow()][game.getShootCol()] = MISS_TILE;
            }
        }
    }
    public void setFieldsForPlayer(int player, String[][] foggyField, String[][] field) {
        // Setting the player's settings
        if (player == 1) {
            setPlayerOneField(field);
            setPlayerOneFoggyField(foggyField);
        } else if (player == 2) {
            setPlayerTwoField(field);
            setPlayerTwoFoggyField(foggyField);
        }
    }
    public void shipPlacementPrompt(Game game, CreateField fieldCreation, UserInterface ui, int player) {
        // REMOVED <= 5 from fori, might need?
        for (int ship = 1; ship <= 5; ship++) {
            Ships shipData = shipToBePlaced(ship);
            String coordinatesPrompt = shipPlacementPrompt(shipData);

            // Printing the prompt for user to enter its ship's coordinates
            ui.headSpace();
            System.out.println(coordinatesPrompt);
            ui.headSpace();

            game.setValiduserInput(false);

            while (!game.isValiduserInput()) {
                String[] coordinates = UserCoordinatesInput();
                game.setUserCoordinates(coordinates);
                game.decodeUserInputCords();

                userActionValidation.shipPlacementValidation(shipData, game, fieldCreation, player);

                // If the user's input is valid, we place the ship on the field
                if (game.isValiduserInput()) {
                    ui.headSpace();

                    // Mapping the ships coordinates && placing it on the map
                    placeAndMapShip(game, shipData, coordinates, player);
                    // Mapping the ships coordinates
                    printField(player);
                }
            }
        }
    }
    private void placeAndMapShip(Game game, Ships shipData, String[] coordinates, int player) {
        // Placing the ship on the field
        for (int row = game.getUserRow1(); row <= game.getUserRow2(); row++) {
            for (int col = game.getUserCol1(); col <= game.getUserCol2(); col++) {
                if (player == 1) {
                    playerOneField[row][col] = SHIP_TILE;
                } else if (player == 2) {
                    playerTwoField[row][col] = SHIP_TILE;
                }
            }
        }

        // Mapping the ship's coordinates to the enum
        char row1 = coordinates[0].charAt(0);
        int col1 = Integer.parseInt(coordinates[0].substring(1));

        char row2 = coordinates[coordinates.length - 1].charAt(0);
        int col2 = Integer.parseInt(coordinates[coordinates.length - 1].substring(1));

        if (row1 == row2) {

            // Horizontal placement
            int maxCol = Math.max(col1, col2);
            int minCol = Math.min(col1, col2);
            char coordinateChar = row1;

            // Add the start coordinate
            if (player == 1) {
                shipData.setPlayerOneShipCoordinates(String.valueOf(coordinateChar) + minCol);
            } else if (player == 2) {
                shipData.setPlayerTwoShipCoordinates(String.valueOf(coordinateChar) + minCol);
            }

            for (int colAddition = minCol + 1; colAddition <= maxCol; colAddition++) {
                String coordinate = String.valueOf(coordinateChar) + colAddition;
                setShipCoordinates(coordinate, shipData, player);
            }
        } else if (col1 == col2) {
            // Vertical placement

            // Add the start coordinate
            if (player == 1) {
                shipData.setPlayerOneShipCoordinates(coordinates[0]);
            } else if (player == 2) {
                shipData.setPlayerTwoShipCoordinates(coordinates[0]);
            }


            int maxRow = Math.max(row1 - 'A', row2 - 'A');
            int minRow = Math.min(row1 - 'A', row2 - 'A');
            int coordinateCol = col1;
            for (int rowAddition = minRow + 1; rowAddition < maxRow; rowAddition++) { // Use < instead of <=
                char coordinateChar = (char) ('A' + rowAddition);
                String coordinate = String.valueOf(coordinateChar) + coordinateCol;
                setShipCoordinates(coordinate, shipData, player);
            }
            // Add the last coordinate (maxRow, col2) separately
            String coordinate = String.valueOf(row2) + coordinateCol;
            setShipCoordinates(coordinate, shipData, player);
        }
    }
    private void setShipCoordinates(String coordinate, Ships shipData, int player) {
        if (player == 1) {
            shipData.setPlayerOneShipCoordinates(coordinate);
        } else if (player == 2) {
            shipData.setPlayerTwoShipCoordinates(coordinate);
        }
    }
    public boolean shootShip(Game game) {
        boolean shotResult = false;
        String[][] field;
        if (game.getPlayerTurn() == 1) {
            field = playerTwoField;
        } else if (game.getPlayerTurn() == 2) {
            field = playerOneField;
        } else {
            throw new IllegalArgumentException("Invalid player turn. Expected 1 or 2.");
        }

        if (field[game.getShootRow()][game.getShootCol()].equals(SHIP_TILE)) {
            field[game.getShootRow()][game.getShootCol()] = HIT_TILE;
            shotResult = true;
            updateFoggyField(shotResult, game);
            return true;
        } else if (field[game.getShootRow()][game.getShootCol()].equals(HIT_TILE)) {
            shotResult = true;
            return true;
        } else if (field[game.getShootRow()][game.getShootCol()].equals(MISS_TILE)) {
            shotResult = false;
            return true;
        } else {
            field[game.getShootRow()][game.getShootCol()] = MISS_TILE;
            shotResult = false;
            updateFoggyField(shotResult, game);
            return false;
        }
    }
    private Ships shipToBePlaced(int ship) {
        // This method is to find out the number of the ship and only then to collect and display data of it
        for (Ships placement : Ships.values()) {
            if (placement.getShipNumber() == ship) {
                return placement;
            }
        }
        return null;
    }
    private String shipPlacementPrompt(Ships shipData) {
        // Collecting data of the ship based on the value of the fori loop
        // Printing out the prompt for user to input the coordinates of the ship
        String shipName = shipData.getShipName();
        int cellCount = shipData.getCellSize();

        String message = "Enter the coordinates of the " + shipName + " (" + cellCount + " cells):";
        return message;
    }
    private String[] UserCoordinatesInput() {
        Scanner scanner = new Scanner(System.in);
        String coordinateInput = scanner.nextLine();
        return coordinateInput.toUpperCase().split(" ");
    }
}
