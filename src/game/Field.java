package game;

class Field {
    private boolean bomb = false;
    private boolean discovered = false;
    private boolean flag = false;
    private int nearBombsCounter = 0;

    private FloodFillListener listener;

    void addFloodFillListener(FloodFillListener listener) {
        this.listener = listener;
    }

    void triggerFloodFill() {
        if (listener != null) {
            listener.floodFill();
        }
    }

    boolean isBomb() {
        return bomb;
    }

    void setBomb(boolean bombStatus) {
        bomb = bombStatus;
    }

    boolean isDiscovered() {
        return discovered;
    }

    void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }

    boolean isFlag() {
        return flag;
    }

    void setFlag(boolean flag) {
        this.flag = flag;
    }

    int getNearBombsCounter() {
        return nearBombsCounter;
    }

    void incrementNumberOfBombsAdjacent() {
        nearBombsCounter++;
    }
}
