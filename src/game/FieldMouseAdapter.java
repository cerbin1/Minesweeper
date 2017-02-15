package game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class FieldMouseAdapter extends MouseAdapter {
    private final Application application;
    private final Game game;
    private final int x;
    private final int y;

    FieldMouseAdapter(Application application, int x, int y) {
        this.application = application;
        game = application.getGame();
        this.x = x;
        this.y = y;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (game.isGameDone()) {
            application.setMessageBoxText("Start new game");
            return;
        }
        Button button = application.getButton(x, y);

        if (e.getButton() == MouseEvent.BUTTON1) {
            leftButtonClick(button);
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            rightButtonClick(button);
        }
    }

    private void leftButtonClick(Button button) {
        application.repositionFirstClickedBomb(button);
        if (button.isDiscovered() || button.isFlag()) {
            application.setMessageBoxText("Field is already clicked or flagged");
            return;
        }
        if (button.isBomb()) {
            application.setMessageBoxText("Boom, you lose!");
            application.displayAllBombs();
            game.setGameDone();
        } else {
            application.clearMessageBox();
            if (button.hasNearBombs()) {
                button.discover();
            } else {
                game.floodFill(x, y);
            }
            if (game.winCondition()) {
                application.displayAllBombs();
                application.setMessageBoxText("You win!");
                game.setGameDone();
            }
        }
    }

    private void rightButtonClick(Button button) {
        if (application.isFirstClick()) {
            application.setFirstClick(false);
            game.fillBombsCounters();
        }
        application.clearMessageBox();
        if (button.isDiscovered()) {
            application.setMessageBoxText("Field is discovered!");
            return;
        }
        if (game.countUnflaggedBombs() < 1) {
            if (!button.isFlag()) {
                application.setMessageBoxText("Can't place more flags!");
                return;
            }
        }
        button.toggleFlag();
        application.updateFlaggedBombsCount();
        if (game.winCondition()) {
            application.setMessageBoxText("Wygrales!");
            game.setGameDone();
        }
    }
}