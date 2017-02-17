package game;

public class Counter {
    private int count = 0;

    public void inc() {
        count++;
    }

    public void dec() {
        count--;
    }

    int getCount() {
        return count;
    }
}
