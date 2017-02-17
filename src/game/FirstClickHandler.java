package game;

class FirstClickHandler {
    private Game game;
    private boolean firstClick = true;

    FirstClickHandler(Game game) {
        this.game = game;
    }

    void repositionBombIfFirstClickedBomb(Button button) {
        if (firstClick) {
            if (button.isBomb()) {
                game.plantSingleBomb();
                button.unmarkAsBomb();
            }
            game.fillBombsCounters();
            setFirstClickAsUsed();
        }
    }

    boolean isFirstClick() {
        return firstClick;
    }

    void setFirstClickAsUsed() {
        this.firstClick = false;
    }
}
