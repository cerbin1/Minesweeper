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
        Field field = fields[x][y];
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (boardContains(x + i, y + j)) {
                    if (fields[x + i][y + j].isBomb()) {
                        int incrementedNearBombsCount = field.getNearBombsCount() + 1;
                        field.setNearBombsCounter(incrementedNearBombsCount);
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
        return bombsCount - forEachFields((field, count) -> {
            if (field.isFlag()) {
                count.inc();
            }
        });
    }

    int countDiscoveredFields() {
        int fieldsCount = forEachFields((field, count) -> {
            if (field.isDiscovered()) {
                count.inc();
            }
        });
        return height * width - fieldsCount;
    }

    int countFlagPoints() {
        return forEachFields((field, count) -> {
            if (field.isFlag()) {
                if (field.isBomb()) {
                    count.inc();
                } else {
                    count.dec();
                }
            }
        });
    }

    private int forEachFields(CountingFieldIterator iterator) {
        Counter counter = new Counter();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                iterator.iterate(fields[i][j], counter);
            }
        }
        return counter.getCount();
    }

    public boolean isGameDone() {
        return gameDone;
    }

    void setGameDone() {
        this.gameDone = true;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
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
