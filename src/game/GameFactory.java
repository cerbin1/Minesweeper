package game;

class GameFactory {
    static Game create(int width, int height, int bombsCount) {
        if (bombsCount < 0) {
            throw new IllegalArgumentException("Negative number of bombs");
        }
        if (width < 4 || height < 4) {
            throw new IllegalArgumentException("Board size too small");
        }
        if (bombsCount >= width * height) {
            throw new IllegalArgumentException("Number of bombs exceeds board size");
        }
        return new Game(width, height, bombsCount);
    }
}
