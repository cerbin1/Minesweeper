package game;

class GameFactory {
    static Game create(int width, int height, int numberOfBombs) {
        if (numberOfBombs < 0) {
            throw new RuntimeException("Negative number of bombs");
        }
        if (width < 4 || height < 4) {
            throw new RuntimeException("Board size too small");
        }
        if (numberOfBombs >= width * height) {
            throw new RuntimeException("Number of bombs exeeds board size");
        }
        return new Game(width, height, numberOfBombs);
    }
}
