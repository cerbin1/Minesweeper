package game;

class Field {
    private boolean bomb = false;
    boolean isDiscovered = false;
    boolean isFlag = false;
    int numberOfBombsAdjacent = 0;

    private FloodFillListener listener;

    void addFloodFillListener(FloodFillListener listener) {
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

    boolean isBomb() {
        return bomb;
    }

    void setBomb(boolean bombStatus) {
        bomb = bombStatus;
    }
}
