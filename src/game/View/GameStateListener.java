package game.View;

public interface GameStateListener {
    void bombsCountChanged(int bombsCount);

    void gameFinished(boolean win);

    void displayMessage(String text);

    void clearMessage();
}
