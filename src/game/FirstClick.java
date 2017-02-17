package game;

class FirstClick {
    private Game game;
    private boolean firstClick = true;

    FirstClick(Application application) {
        this.game = application.getGame();
    }

    void repositionBombIfFirstClickedBomb(Button button) {
        if (firstClick) {
            if (button.isBomb()) {
                game.plantSingleBomb();
                button.unmarkAsBomb();
            }
            game.fillBombsCounters();
            setFirstClick(false);
        }
    }

    boolean isFirstClick() {
        return firstClick;
    }

    void setFirstClick(boolean firstClick) {
        this.firstClick = firstClick;
    }
}
