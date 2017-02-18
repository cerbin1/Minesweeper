package game;

class FirstClickHandler {
    private Game game;
    private boolean firstClick = true;

    FirstClickHandler(Game game) {
        this.game = game;
    }

    void repositionFirstClickedBomb(Button button) {
        if (firstClick) {
            if (button.isBomb()) {
                game.plantSingleBomb();
                button.unMarkAsBomb();
            }
            firstClick = false;
            game.fillBombsCounters();
        }
    }

    void setFirstClicked() {
        if (firstClick) {
            firstClick = false;
            game.fillBombsCounters();
        }
    }
}
