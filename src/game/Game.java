package game;

import static java.lang.Math.random;
import static java.lang.Math.round;

public class Game {
    private final int width, height;
    private final int bombsCount;
    private boolean finished = false;
    private final Field[][] fields;

    public Game(Size size, int bombsCount) {
        this.width = size.getWidth();
        this.height = size.getHeight();
        this.bombsCount = bombsCount;
        this.fields = createFields();
        populateBombs();
    }

    Size getSize() {
        return new Size(width, height);
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
                fillSingleBombCounterField(x + i, y + j, field);
            }
        }
    }

    private void fillSingleBombCounterField(int x, int y, Field field) {
        if (!boardContains(x, y)) return;
        if (fields[x][y].isBomb()) {
            int bombsCount = field.getNearBombsCount();
            field.setNearBombsCounter(bombsCount + 1);
        }
    }

    private boolean boardContains(int x, int y) {
        return (0 <= x && x < width) && (0 <= y && y < height);
    }

    Field getField(int x, int y) {
        return fields[x][y];
    }

    void floodFill(int x, int y) {
        if (!boardContains(x, y)) return;
        if (shouldFloodFillEnd(fields[x][y])) return;

        Field field = fields[x][y];
        field.discover();
        field.triggerFloodFill();
        recursiveFloodFill(x, y, field);
    }

    private void recursiveFloodFill(int x, int y, Field field) {
        if (field.getNearBombsCount() > 0) return;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                floodFill(x + i, y + j);
            }
        }
    }

    private boolean shouldFloodFillEnd(Field field) {
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

    public boolean isFinished() {
        return finished;
    }

    void finish() {
        this.finished = true;
    }

    boolean winCondition() {
        return flagsWinCondition() || bombsWinCondition();
    }

    private boolean flagsWinCondition() {
        return countFlagPoints() > 0 && countFlagPoints() == bombsCount - countDiscoveredFields();
    }

    private boolean bombsWinCondition() {
        return countDiscoveredFields() - bombsCount == 0;
    }
}
