package game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.event.MouseEvent.BUTTON1;
import static java.awt.event.MouseEvent.BUTTON3;

class FieldMouseAdapter extends MouseAdapter {
    private final Mediator mediator;
    private final Game game;
    private final int x, y;

    private final FirstClickHandler firstClickHandler;

    FieldMouseAdapter(Mediator mediator, int x, int y) {
        this.mediator = mediator;
        this.game = mediator.getGame();
        this.x = x;
        this.y = y;
        this.firstClickHandler = mediator.getFirstClickHandler();
    }

    @Override
    public void mousePressed(MouseEvent event) {
        if (game.isFinished()) {
            mediator.displayMessage("Start new game");
            return;
        }
        Button button = mediator.getButton(x, y);

        if (event.getButton() == BUTTON1) {
            leftButtonClick(button);
        } else if (event.getButton() == BUTTON3) {
            rightButtonClick(button);
        }
    }

    private void leftButtonClick(Button button) {
        if (game.isFinished()) {
            mediator.displayMessage("Game is done");
            return;
        }
        firstClickHandler.repositionFirstClickedBomb(button);
        if (button.isDiscovered() || button.isFlag()) {
            mediator.displayMessage("Field is already clicked or flagged");
            return;
        }
        if (button.isBomb()) {
            mediator.gameFinished(false);
        } else {
            mediator.clearMessage();
            if (button.hasNearBombs()) {
                button.discoverButtonWithNearBombs();
            } else {
                game.floodFill(x, y);
            }
            if (game.winCondition()) {
                mediator.gameFinished(true);
            }
        }
    }

    private void rightButtonClick(Button button) {
        if (game.isFinished()) {
            mediator.displayMessage("Game is done");
            return;
        }
        firstClickHandler.setFirstClicked();
        mediator.clearMessage();
        if (button.isDiscovered()) {
            mediator.displayMessage("Field is discovered!");
            return;
        }
        if (game.countUnflaggedBombs() < 1) {
            if (!button.isFlag()) {
                mediator.displayMessage("Can't place more flags!");
                return;
            }
        }
        button.toggleFlag();
        mediator.updateFlaggedBombsCount();
        if (game.winCondition()) {
            mediator.gameFinished(true);
        }
    }
}