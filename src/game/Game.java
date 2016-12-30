package game;

import java.awt.*;

public class Game {

    int numberOfBombs;

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
            for (int j = 0; j < 10; j++) {
                isBombAdjacentToField(i, j, fields);
            }
        }

    }

    void isBombAdjacentToField(int x, int y, Field[][] fields) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((0 <= x + i && x + i < 10) && (0 <= y + j && y + j < 10)) {
                    if (fields[x + i][y + j].isBomb) {
                        fields[x][y].numberOfBombsAdjacent++;
                    }
                }
            }
        }
    }

    void floodFill(int x, int y, Field[][] field) {
        System.out.println(Integer.toString(x) + Integer.toString(y));
        if ((0 > x || x >= 10) || (0 > y || y >= 10)) {
            System.out.println("Przekroczona plansza");
            return;
        }
        if (field[x][y].isFlag || field[x][y].isBomb) {
            System.out.println("Pole nie jest puste");
            return;
        }
        if (field[x][y].isDiscovered) {
            System.out.println("Pole juz odkryte");
            return;
        }
        if (field[x][y].numberOfBombsAdjacent > 0) {
            field[x][y].button.setText(Integer.toString(field[x][y].getNumberOfBombsAdjacent()));
            field[x][y].isDiscovered = true;
        } else {
            field[x][y].isDiscovered = true;
            field[x][y].button.setBackground(Color.darkGray);
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    floodFill(x + i, y + j, field);
                }
            }
        }
    }

    void checkIfPlayerWinByFlags(Field[][] fields) {
        int points = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(fields[i][j].isFlag && fields[i][j].isFlag) {
                    points++;
                }
            }
        }
    }

}
