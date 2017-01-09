package game;

class Game {
    final int width;
    final int height;
    final int numberOfBombs;
    final private Field[][] fields;

    boolean isGameDone = false;

    Game(int width, int height, int numberOfBombs) {
        this.width = width;
        this.height = height;
        this.numberOfBombs = numberOfBombs;
        this.fields = createFields(width, height);
        populateBombs();
        fillBombsCounters();
    }

    private Field[][] createFields(int width, int height) {
        Field[][] fields = new Field[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                fields[i][j] = new Field();
            }
        }
        return fields;
    }

    private void populateBombs() {
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

    private void fillBombsCounters() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                fillSingleBombCounter(i, j, fields);
            }
        }
    }

    private void fillSingleBombCounter(int x, int y, Field[][] fields) {
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

    Field getField(int x, int y) {
        return fields[x][y];
    }

    void floodFill(int x, int y) {
        if ((0 > x || x >= height) || (0 > y || y >= width)) {
            return;
        }
        Field field = fields[x][y];

        if (field.isFlag || field.isBomb || field.isDiscovered) {
            return;
        }

        field.isDiscovered = true;
        field.triggerFloodFill();

        if (field.numberOfBombsAdjacent == 0) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    floodFill(x + i, y + j);
                }
            }
        }
    }

    int countFlagPoints() {
        int points = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Field field = fields[i][j];
                if (field.isBomb) {
                    if (field.isFlag) {
                        points++;
                    } else {
                        points--;
                    }
                }
            }
        }
        return points;
    }

    int countDiscoveredFields() {
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
