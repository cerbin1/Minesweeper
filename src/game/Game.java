package game;

import static java.lang.Math.random;
import static java.lang.Math.round;

public class Game {
    private final int width, height;
    private final int bombsCount;
    private boolean gameDone = false;
    private final Field[][] fields;

    public Game(Size size, int bombsCount) {
        this.width = size.getWidth();
        this.height = size.getHeight();
        this.bombsCount = bombsCount;
        fields = createFields();
        populateBombs();
    }

    private Field[][] createFields() {
        Field[][] fields = new Field[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                fields[i][j] = new Field();
            }
        }
        return fields;
    }

    private void populateBombs() {
        for (int i = 0; i < bombsCount; i++) {
            plantSingleBomb();
        }
    }

    void plantSingleBomb() {
        while (true) {
            int x = (int) round(random() * (width - 1));
            int y = (int) round(random() * (height - 1));
            if (!fields[x][y].isBomb()) {
                fields[x][y].setBomb(true);
                break;
            }
        }
    }

    void fillBombsCounters() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                fillSingleBombCounter(i, j);
            }
        }
    }

    private void fillSingleBombCounter(int x, int y) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (boardContains(x + i, y + j)) {
                    if (fields[x + i][y + j].isBomb()) {
                        fields[x][y].incrementNearBombsCount();
                    }
                }
            }
        }
    }

    private boolean boardContains(int x, int y) {
        return (0 <= x && x < width) && (0 <= y && y < height);
    }

    Field getField(int x, int y) {
        return fields[x][y];
    }

    void floodFill(int x, int y) {
        if (!boardContains(x, y)) {
            return;
        }
        Field field = fields[x][y];

        if (shouldFloodFillEndOn(field)) {
            return;
        }

        field.discover();
        field.triggerFloodFill();

        if (field.getNearBombsCount() == 0) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    floodFill(x + i, y + j);
                }
            }
        }
    }

    private boolean shouldFloodFillEndOn(Field field) {
        return field.isFlag() || field.isBomb() || field.isDiscovered();
    }

    int countUnflaggedBombs() {
        int[] points = {bombsCount};
        forEachFields(field -> {
            if (field.isFlag()) {
                points[0]--;
            }
        });
        return points[0];
    }

    int countDiscoveredFields() {
        int[] numberOfFields = {height * width};
        forEachFields(field -> {
            if (field.isDiscovered()) {
                numberOfFields[0]--;
            }
        });
        return numberOfFields[0];
    }

    int countFlagPoints() {
        int[] points = {0};
        forEachFields(field -> {
            if (field.isFlag()) {
                if (field.isBomb()) {
                    points[0]++;
                } else {
                    points[0]--;
                }
            }
        });
        return points[0];
    }

    private void forEachFields(FieldIterator iterator) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                iterator.iterate(fields[i][j]);
            }
        }
    }

    public boolean isGameDone() {
        return gameDone;
    }

    void setGameDone() {
        this.gameDone = true;
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }

    boolean winCondition() {
        return flagsWinCondition() || bombsWinCondition();
    }

    private boolean flagsWinCondition() {
        return countFlagPoints() == bombsCount - countDiscoveredFields();
    }

    private boolean bombsWinCondition() {
        return countDiscoveredFields() - bombsCount == 0;
    }
}
