package game;

class Game {
    final int width;
    final int height;
    final int numberOfBombs;
    final private Field[][] fields;

    private boolean gameDone = false;

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
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                fields[i][j] = new Field();
            }
        }
        return fields;
    }

    private void populateBombs() {
        for (int i = 0; i < numberOfBombs; i++) {
            plantSingleBomb();
        }
    }

    void plantSingleBomb() {
        while (true) {
            int x = (int) Math.round(Math.random() * (width - 1));
            int y = (int) Math.round(Math.random() * (height - 1));
            if (!fields[x][y].isBomb()) {
                fields[x][y].setBomb(true);
                break;
            }
        }
    }

    private void fillBombsCounters() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                fillSingleBombCounter(i, j, fields);
            }
        }
    }

    private void fillSingleBombCounter(int x, int y, Field[][] fields) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((0 <= x + i && x + i < width) && (0 <= y + j && y + j < height)) {
                    if (fields[x + i][y + j].isBomb()) {
                        fields[x][y].incrementNumberOfBombsAdjacent();
                    }
                }
            }
        }
    }

    Field getField(int x, int y) {
        return fields[x][y];
    }

    void floodFill(int x, int y) {
        if ((0 > x || x >= width) || (0 > y || y >= height)) {
            return;
        }
        Field field = fields[x][y];

        if (field.isFlag() || field.isBomb() || field.isDiscovered()) {
            return;
        }

        field.setDiscovered(true);
        field.triggerFloodFill();

        if (field.getNearBombsCounter() == 0) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    floodFill(x + i, y + j);
                }
            }
        }
    }

    int countFlagPoints() {
        int points = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Field field = fields[i][j];
                if (field.isFlag()) {
                    if (field.isBomb()) {
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
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (fields[i][j].isDiscovered()) {
                    numberOfFields--;
                }
            }
        }
        return numberOfFields;
    }

    boolean isGameDone() {
        return gameDone;
    }
}
