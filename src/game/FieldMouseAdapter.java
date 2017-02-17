package game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.event.MouseEvent.BUTTON1;
import static java.awt.event.MouseEvent.BUTTON3;

public class FieldMouseAdapter extends MouseAdapter {
    private final Application application;
    private final Game game;
    private final int x, y;

    private final FirstClickHandler firstClickHandler;

    public FieldMouseAdapter(Application application, int x, int y) {
        this.application = application;
        this.game = application.getGame();
        this.x = x;
        this.y = y;
        this.firstClickHandler = application.getFirstClickHandler();
    }

    @Override
    public void mousePressed(MouseEvent event) {
        if (game.isFinished()) {
            application.setMessageBoxText("Start new game");
            return;
        }
        Button button = application.getButton(x, y);

        if (event.getButton() == BUTTON1) {
            leftButtonClick(button);
        } else if (event.getButton() == BUTTON3) {
            rightButtonClick(button);
        }
    }

    private void leftButtonClick(Button button) {
        if (game.isFinished()) {
            application.setMessageBoxText("Game is done");
            return;
        }
        firstClickHandler.repositionBombIfFirstClickedBomb(button);
        if (button.isDiscovered() || button.isFlag()) {
            application.setMessageBoxText("Field is already clicked or flagged");
            return;
        }
        if (button.isBomb()) {
            application.setMessageBoxText("Boom, you lose!");
            application.displayAllBombs();
            game.finish();
        } else {
            application.clearMessageBox();
            if (button.hasBombsNear()) {
                button.discoverButtonWithNearBombs();
            } else {
                game.floodFill(x, y);
            }
            if (game.winCondition()) {
                application.setGameAsWon();
            }
        }
    }

    private void rightButtonClick(Button button) {
        if (game.isFinished()) {
            application.setMessageBoxText("Game is done");
            return;
        }
        if (firstClickHandler.isFirstClick()) {
            firstClickHandler.setFirstClickAsUsed();
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
            application.setGameAsWon();
        }
    }
}