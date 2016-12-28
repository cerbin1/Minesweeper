package game;

import java.awt.*;

public class Game {
    void setBombs(Field[][] fields, int numberOfBombs) {
        for (int i = 0; i < numberOfBombs; i++) {
            while (true) {
                int x = (int) Math.round(Math.random() * 9);
                int y = (int) Math.round(Math.random() * 9);
                if (!fields[x][y].isBomb) {
                    fields[x][y].isBomb = true;
                    System.out.println(Integer.toString(x) + Integer.toString(y));
                    break;
                }
            }
        }
    }

    void displayAllBombs(Field[][] fields) {
        for (int k = 0; k < 10; k++) {
            for (int l = 0; l < 10; l++) {
                if (fields[k][l].isBomb) {
                    fields[k][l].button.setText("x");
                    fields[k][l].button.setForeground(Color.RED);
                    Field.isGameDone = true;
                }
            }
        }
    }
}
