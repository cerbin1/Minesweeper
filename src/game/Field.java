package game;

import javax.swing.JButton;
import java.awt.Dimension;

public class Field {
    static boolean isGameDone = false;
    boolean isBomb;
    JButton button;

    public Field(int buttonWidth, int buttonHeight) {
        button = new JButton();
        button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        isBomb = false;

    }
}



