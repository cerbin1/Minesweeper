package game;

class Field {
    boolean isBomb = false;
    boolean isDiscovered = false;
    boolean isFlag = false;
    int numberOfBombsAdjacent = 0;

    private FloodFillListener listener;

    void registerFloodFillListener(FloodFillListener listener) {
        this.listener = listener;
    }

    void triggerFloodFill() {
        if (listener != null) {
            listener.floodFill();
        }
    }

    int getAdjacentBombsCount() {
        return numberOfBombsAdjacent;
    }

    @Override
    public String toString() {
        return "Field{" +
                "isBomb=" + isBomb +
                ", isDiscovered=" + isDiscovered +
                ", isFlag=" + isFlag +
                ", numberOfBombsAdjacent=" + numberOfBombsAdjacent +
                '}';
    }
}
