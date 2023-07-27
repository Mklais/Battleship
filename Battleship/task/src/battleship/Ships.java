package battleship;

import java.util.ArrayList;
import java.util.List;

public enum Ships {
    AIRCRAFT_CARRIER ("Aircraft Carrier",1, 5, 0, false, new ArrayList<>(), new ArrayList<>()),
    BATTLESHIP("Battleship",2, 4, 0, false, new ArrayList<>(), new ArrayList<>()),
    SUBMARINE ("Submarine", 3,3, 0, false, new ArrayList<>(), new ArrayList<>()),
    CRUISER ("Cruiser", 4, 3, 0, false, new ArrayList<>(), new ArrayList<>()),
    DESTROYER ("Destroyer", 5, 2, 0, false, new ArrayList<>(), new ArrayList<>());
    private String shipName;
    private int shipNumber;
    private int cellSize;
    private int hitCount;
    private boolean sunk;
    private List<String> playerOneShipCoordinates;
    private List<String> playerTwoShipCoordinates;
    Ships(String shipName, int shipNumber, int cellSize, int hitCount, boolean sunk, List<String> playerOneShipCoordinates, List<String> playerTwoShipCoordinates) {
        this.shipName = shipName;
        this.shipNumber = shipNumber;
        this.cellSize = cellSize;
        this.hitCount = hitCount;
        this.sunk = sunk;
        this.playerOneShipCoordinates = playerOneShipCoordinates;
        this.playerTwoShipCoordinates = playerTwoShipCoordinates;
    }

    public String getShipName() {
        return shipName;
    }
    public int getShipNumber() {
        return shipNumber;
    }
    public int getCellSize() {
        return cellSize;
    }
    public int getHitCount() {
        return hitCount;
    }
    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }
    public boolean isSunk() {
        return sunk;
    }
    public void setSunk(boolean sunk) {
        this.sunk = sunk;
    }
    public List<String> getPlayerOneShipCoordinates() {
        return playerOneShipCoordinates;
    }
    public void setPlayerOneShipCoordinates(String data) {
        playerOneShipCoordinates.add(data);
    }
    public List<String> getPlayerTwoShipCoordinates() {
        return playerTwoShipCoordinates;
    }
    public void setPlayerTwoShipCoordinates(String data) {
        playerTwoShipCoordinates.add(data);
    }
}
