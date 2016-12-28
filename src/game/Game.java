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
                   /// System.out.println(Integer.toString(x) + Integer.toString(y));
                   /// break; chuj Ci w dupe xd
                }
            }
        }
        countNumberOfBombsAdjacentToField(fields);
    }

    void displayAllBombs(Field[][] fields) {
        for (int k = 0; k < 10; k++) {
            for (int l = 0; l < 10; l++) {
                if (fields[k][l].isBomb) {
                    fields[k][l].button.setText("x");
                    fields[k][l].button.setForeground(Color.RED);
//                    Field.isGameDone = true;
                }
            }
        }
    }

    void countNumberOfBombsAdjacentToField(Field[][] fields) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; i++) {
                isBombAdjacentToField(i, j, fields);
            }
        }

    }

    void isBombAdjacentToField(int x, int y, Field[][] fields) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if((0 <= x + i && x + i < 10) && (0 <= y + j && y + j < 10)) {
                    if(fields[x + i][y + j].isBomb) {
                        fields[x][y].numberOfBombsAdjacent++;
                    }
                }
            }
        }
    }
}
