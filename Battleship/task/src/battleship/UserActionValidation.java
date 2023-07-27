package battleship;

public class UserActionValidation {
    public void shipPlacementValidation(Ships shipData, Game game, CreateField fieldCreation, int player) {
        // Setting the user input as valid before going to check them
        game.setValiduserInput(true);

        String[][] field;
        if (player == 1) {
            field = fieldCreation.getPlayerOneField();
        } else {
            field = fieldCreation.getPlayerTwoField();
        }

        if (!inputCoordinatesFormatValidation(game) ||
                !inputCoordinatesRangeValidation(game) ||
                !shipLength(shipData, game) ||
                !diagonalCoordinatesValidation(game) ||
                !adjacentToShipValidation(game, fieldCreation, field)) {
            game.setValiduserInput(false);
        }
    }
    // TODO: GO OVER ALL THE VALIDATION PROCESSES
    public boolean userShootCoordinatesValidation(Game game) {
        // Setting the user input as valid before going to check them
        game.setValiduserInput(true);

        if (!userShootCoordinatesRangeValidation(game)) {
            game.setValiduserInput(false);
            return false;
        } else {
            return true;
        }
    }
    private boolean userShootCoordinatesRangeValidation(Game game) {
        // Checking if the shot is in valid range <= 10
        if (game.getShootRow() <= 10 && game.getShootCol() <= 10) {
            return true;
        } else {
            System.out.println();
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return false;
        }
    }
    private boolean inputCoordinatesFormatValidation(Game game) {
        for (String coordinate : game.getUserCoordinates()) {
            try {
                char firstLetter = coordinate.charAt(0);
                int col = Character.toUpperCase(firstLetter - 'A');

                int row = Integer.parseInt(coordinate.substring(1)) - 1;

                // If the coordinate is valid, continue with the next coordinate
            } catch (Exception e) {
                // If an exception occurs, handle the invalid coordinate and set the flag to false
                System.out.println();
                System.out.println("Error! Invalid coordinate format: " + coordinate);
                return false; // Exit the method with false if any coordinate is invalid
            }
        }

        return true;
    }
    private boolean inputCoordinatesRangeValidation(Game game) {
        for (String coordinate : game.getUserCoordinates()) {
            try {
                char firstLetter = coordinate.charAt(0);
                int col = Character.toUpperCase(firstLetter) - 'A';

                if (!(col >= 0 && col <= 10)) {
                    System.out.println();
                    System.out.println("Error! Invalid column coordinate: " + firstLetter);
                    return false; // Return false when an invalid coordinate is found
                }

                int row = Integer.parseInt(coordinate.substring(1)) - 1;

                if (!(row >= 0 && row <= 10)) {
                    System.out.println();
                    System.out.println("Error! Invalid row coordinate: " + coordinate.substring(1));
                    return false; // Return false when an invalid coordinate is found
                }

                // If the coordinate is valid, continue with the next coordinate
            } catch (Exception e) {
                // If an exception occurs, handle the invalid coordinate and set the flag to false
                System.out.println();
                System.out.println("Error! Invalid coordinate format: " + coordinate);
                return false; // Return false when an invalid coordinate format is found
            }
        }

        return true; // Return true if all coordinates are valid
    }
    private boolean shipLength(Ships shipData, Game game) {
        int shipLength = shipData.getCellSize();

        if (game.getUserRow1() == game.getUserRow2()) {
            // If both the row letters are the same, we check for columns
            int userInputShipLength = Math.abs(game.getUserCol2() - game.getUserCol1()) + 1;
            if (userInputShipLength == shipLength) {
                return true;
            } else {
                System.out.println();
                String message = "Error! Wrong length of the " + shipData.getShipName() + "! Try again:";
                System.out.println(message);
                return false;
            }
        } else {
            // Else we check for rows
            int userInputShipLength = game.getUserRow2() - game.getUserRow1() + 1;
            if (userInputShipLength == shipLength) {
                return true;
            } else {
                System.out.println();
                String message = "Error! Wrong length of the " + shipData.getShipName() + "! Try again:";
                System.out.println(message);
                return false;
            }
        }
    }
    private boolean diagonalCoordinatesValidation(Game game) {
        if (game.getUserRow1() == game.getUserRow2() || game.getUserCol1() == game.getUserCol2()) {
            return true;
        } else {
            System.out.println();
            String message = "Error! Wrong ship location! Try again:";
            System.out.println(message);
            return false;
        }
    }
    private boolean adjacentToShipValidation(Game game, CreateField fieldCreation, String[][] field) {
        int row1 = game.getUserRow1();
        int row2 = game.getUserRow2();
        int col1 = game.getUserCol1();
        int col2 = game.getUserCol2();

        // Iterate through the cells around the ship's location
        for (int row = row1 - 1; row <= row2 + 1; row++) {
            for (int col = col1 - 1; col <= col2 + 1; col++) {
                // Check if the current cell is within the bounds of the field
                if (row >= 0 && row < field.length && col >= 0 && col < field[row].length) {
                    // Check if the current cell contains a ship tile
                    if (field[row][col].equals(fieldCreation.getSHIP_TILE())) {
                        System.out.println();
                        String message = "Error! You placed it too close to another one. Try again:";
                        System.out.println(message);
                        System.out.println();
                        return false; // Ship is adjacent to another ship, return false
                    }
                }
            }
        }

        // Iterate through the cells around the ship's location
        //for (int row = row1 - 1; row <= row2 + 1; row++) {
        //    for (int col = col1 - 1; col <= col2 + 1; col++) {
        //        // Check if the current cell is within the bounds of the field
        //        if (row >= 0 && row < fieldCreation.getPlayerOneField().length && col >= 0 && col < fieldCreation.getPlayerOneField()[row].length) {
        //            // Check if the current cell contains a ship tile
        //            if (fieldCreation.getPlayerOneField()[row][col].equals(fieldCreation.getSHIP_TILE())) {
        //                System.out.println();
        //                String message = "Error! You placed it too close to another one. Try again:";
        //                System.out.println(message);
        //                System.out.println();
        //                return false; // Ship is adjacent to another ship, return false
        //            }
        //        }
        //    }
        //}

        return true; // No adjacent ships found, return true
    }
}
