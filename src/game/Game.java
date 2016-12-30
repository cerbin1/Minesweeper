package game;

import java.awt.*;

public class Game {
    static int numberOfBombs = 10;
    static int height = 10;
    static int width = 10;
    static boolean isGameDone = false;

    void setBombs(Field[][] fields, int numberOfBombs) {
        for (int i = 0; i < numberOfBombs; i++) {
            while (true) {
                int x = (int) Math.round(Math.random() * 9);
                int y = (int) Math.round(Math.random() * 9);
                if (!fields[x][y].isBomb) {
                    fields[x][y].isBomb = true;
                    break;
                }
            }
        }
        countNumberOfBombsAdjacentToField(fields);
    }

    void displayAllBombs(Field[][] fields) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Field cell = fields[i][j];
                if (cell.isBomb) {
                    cell.button.setText("x");
                    cell.button.setForeground(Color.DARK_GRAY);
                    if (cell.isFlag) {
                        cell.button.setBackground(Color.GREEN);
                    } else {
                        cell.button.setBackground(Color.red);
                    }
                    //TODO add Game.isGameDone = true;
                }
            }
        }
    }

    void countNumberOfBombsAdjacentToField(Field[][] fields) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                isBombAdjacentToField(i, j, fields);
            }
        }

    }

    void isBombAdjacentToField(int x, int y, Field[][] fields) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((0 <= x + i && x + i < height) && (0 <= y + j && y + j < width)) {
                    if (fields[x + i][y + j].isBomb) {
                        fields[x][y].numberOfBombsAdjacent++;
                    }
                }
            }
        }
    }

    void floodFill(int x, int y, Field[][] field) {
        if ((0 > x || x >= height) || (0 > y || y >= width)) {
            return;
        }
        if (field[x][y].isFlag || field[x][y].isBomb) {
            return;
        }
        if (field[x][y].isDiscovered) {
            return;
        }
        if (field[x][y].numberOfBombsAdjacent > 0) {
            field[x][y].button.setText(Integer.toString(field[x][y].getNumberOfBombsAdjacent()));
            field[x][y].button.setForeground(getColorOfNumberOfBombsAdjacentToField(field[x][y].numberOfBombsAdjacent));
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

    int countPointsFromFlags(Field[][] fields) {
        int points = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (fields[i][j].isFlag) {
                    if (fields[i][j].isFlag) {
                        points++;
                    } else {
                        points--;
                    }
                }
            }
        }
        return points;
    }

    Color getColorOfNumberOfBombsAdjacentToField(int numberOfBombs) {
        Color[] colors = {Color.BLUE, Color.GREEN, Color.RED, Color.MAGENTA, Color.ORANGE, Color.LIGHT_GRAY, Color.YELLOW, Color.PINK};
        return colors[numberOfBombs - 1];
    }

    int countFieldsDiscovered(Field[][] fields) {
        int numberOfFields = height * width;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (fields[i][j].isDiscovered) {
                    numberOfFields--;
                }
            }
        }
        return numberOfFields;
    }

}
