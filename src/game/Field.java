package game;

public class Field {
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

    public boolean isBomb() {
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

    public boolean isFlag() {
        return flag;
    }

    void setFlag(boolean flag) {
        this.flag = flag;
    }

    int getNearBombsCount() {
        return nearBombsCounter;
    }

    void setNearBombsCounter(int number) {
        nearBombsCounter = number;
    }
}
