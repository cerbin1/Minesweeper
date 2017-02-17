package game;

class Field {
    private boolean bomb = false;
    private boolean discovered = false;
    private boolean flag = false;
    private int nearBombsCounter = 0;

    private FloodFillListener listener;

    void setFloodFillListener(FloodFillListener listener) {
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

    void discover() {
        this.discovered = true;
    }

    boolean isFlag() {
        return flag;
    }

    void setFlag(boolean flag) {
        this.flag = flag;
    }

    int getNearBombsCount() {
        return nearBombsCounter;
    }

    void incrementNearBombsCount() {
        nearBombsCounter++;
    }
}
