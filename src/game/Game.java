package game;


import java.awt.*;

public class Game {

    boolean[][] bombs;

    public Game() {
        bombs = createBombsArray();
        fillBooleanBoardWithBombs(bombs, 10);
    }


    private boolean[][] createBombsArray() {
        boolean[][] array = new boolean[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                array[i][j] = false;
            }
        }
        return array;
    }

    private void fillBooleanBoardWithBombs(boolean[][] array, int numberOfBombs) {
        for (int i = 0; i < numberOfBombs; i++) {
            while (true) {
                int x = (int) Math.round(Math.random() * 9);
                int y = (int) Math.round(Math.random() * 9);
                if (!array[x][y]) {
                    array[x][y] = true;
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
