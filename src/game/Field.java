package game;

import javax.swing.JButton;
import java.awt.Dimension;

public class Field {
    static boolean isGameDone = false;
    boolean isBomb;
    boolean isDiscovered;
    boolean isFlag;
    int numberOfBombsAdjacent;
    JButton button;

    public Field(int buttonWidth, int buttonHeight) {
        button = new JButton();
        button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        isDiscovered = false;
        isFlag = false;
        isBomb = false; // TODO zainicjalizuj przy deklaracji
        numberOfBombsAdjacent = 0; // TODO zainicjalizuj przy deklaracji
    }

    int getNumberOfBombsAdjacent() { // TODO zmienić nazwe na getBombsAdjacentCount()
        return numberOfBombsAdjacent;
    }
}



