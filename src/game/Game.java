package game;

class Game {
    private final int width;
    private final int height;
    private final int bombsCount;
    final private Field[][] fields;

    private final boolean gameDone = false;

    Game(Size size, int bombsCount) {
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
            int x = (int) Math.round(Math.random() * (width - 1));
            int y = (int) Math.round(Math.random() * (height - 1));
            if (!fields[x][y].isBomb()) {
                fields[x][y].setBomb(true);
                System.out.println(x + ", " + y);
                break;
            }
        }
    }

    void fillBombsCounters() {
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
                        fields[x][y].setNearBombsCounter(fields[x][y].getNearBombsCount() + 1);
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

        if (shouldFloodFillEndOn(field)) {
            return;
        }

        field.setDiscovered(true);
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
        int points = bombsCount;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Field field = fields[i][j];
                if (field.isFlag()) {
                    points--;
                }
            }
        }
        return points;
    }

    private int countDiscoveredFields() {
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

    private int countFlagPoints() {
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

    boolean isGameDone() {
        return gameDone;
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }

    private int getBombsCount() {
        return bombsCount;
    }

    boolean winCondition() {
        return flagsWinCondition() || bombsWinCondition();
    }

    private boolean flagsWinCondition() {
        return countFlagPoints() == getBombsCount() - countDiscoveredFields();
    }

    private boolean bombsWinCondition() {
        return countDiscoveredFields() - getBombsCount() == 0;
    }
}
