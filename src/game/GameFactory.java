package game;

class GameFactory {
    static Game create(Size size, int bombsCount) {
        if (bombsCount < 0) {
            throw new IllegalArgumentException("Negative number of bombs");
        }
        if (size.getWidth() < 4 || size.getHeight() < 4) {
            throw new IllegalArgumentException("Board size too small");
        }
        if (bombsCount >= size.getWidth() * size.getHeight()) {
            throw new IllegalArgumentException("Number of bombs exceeds board size");
        }
        return new Game(size, bombsCount);
    }
}
