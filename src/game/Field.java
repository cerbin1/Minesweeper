package game;

class Field {
    boolean isBomb = false;
    boolean isDiscovered = false;
    boolean isFlag = false;
    private int numberOfBombsAdjacent = 0;

    int getNumberOfBombsAdjacent() { // TODO zmienić nazwe na getBombsAdjacentCount()
        return numberOfBombsAdjacent;
    }

    void incrementNumberOfBombsAdjacent() {
        numberOfBombsAdjacent++;
    }
}
