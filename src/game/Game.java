package game;

import javax.swing.*;
import java.awt.*;

class Game {
    int getNumberOfBombs() {
        return numberOfBombs;
    }

    private final int numberOfBombs;

    int getHeight() {
        return height;
    }

    int getWidth() {
        return width;
    }

    private final int height;
    private final int width;
    boolean isGameDone = false;

    Field getField(int x, int y) {
        return fields[x][y];
    }

    private final Field[][] fields;

    Game(int width, int height, int numberOfBombs) {
        this.width = width;
        this.height = height;
        this.numberOfBombs = numberOfBombs;
        fields = initializeFields(width, height);
        setBombs();
        fillBombCounters();

    }

    private Field[][] initializeFields(int width, int height) {
        Field[][] fields = new Field[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                fields[i][j] = new Field();
            }
        }
        return fields;
    }

    private void setBombs() {
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
    }

    private void fillBombCounters() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                fillSingleBombCounter(i, j);
            }
        }

    }

    private void fillSingleBombCounter(int x, int y) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((0 <= x + i && x + i < height) && (0 <= y + j && y + j < width)) {
                    if (fields[x + i][y + j].isBomb) {
                        fields[x][y].incrementNumberOfBombsAdjacent();
                    }
                }
            }
        }
    }

    void floodFill(int x, int y, JButton[][] buttons) {
        if ((0 > x || x >= height) || (0 > y || y >= width)) {
            return;
        }
        if (fields[x][y].isFlag || fields[x][y].isBomb) {
            return;
        }
        if (fields[x][y].isDiscovered) {
            return;
        }
        if (fields[x][y].getNumberOfBombsAdjacent() > 0) {
            fields[x][y].isDiscovered = true;
            buttons[x][y].setText(Integer.toString(fields[x][y].getNumberOfBombsAdjacent()));
            buttons[x][y].setForeground(Application.getColorOfNumberOfBombsAdjacentToField(fields[x][y].getNumberOfBombsAdjacent()));
        } else {
            fields[x][y].isDiscovered = true;
            buttons[x][y].setBackground(Color.darkGray);
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    floodFill(x + i, y + j, buttons);
                }
            }
        }
    }

    int countPointsFromFlags() {
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

    int countFieldsDiscovered() {
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
