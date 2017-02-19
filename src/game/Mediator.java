package game;

import game.view.GameFrame;
import game.sound.SoundRepository;

import javax.swing.*;
import java.awt.*;

public class Mediator implements GameStateListener {
    private final Size size;
    private final Game game;
    private final Button[][] buttons;
    private final GameFrame gameFrame;
    private final FirstClickHandler firstClickHandler;
    private final SoundRepository soundRepository;

    public Mediator(Size size, Game game, SoundRepository soundRepository) {
        this.size = size;
        this.game = game;
        this.firstClickHandler = new FirstClickHandler(game);
        this.soundRepository = soundRepository;
        this.buttons = createButtons();
        this.gameFrame = new GameFrame(this);
    }

    public void showFrame() {
        gameFrame.showFrame();
    }

    private Button[][] createButtons() {
        Button[][] buttons = new Button[size.getWidth()][size.getHeight()];
        for (int i = 0; i < size.getWidth(); i++) {
            for (int j = 0; j < size.getHeight(); j++) {
                buttons[i][j] = createButton(i, j);
            }
        }
        return buttons;
    }

    private Button createButton(int x, int y) {
        return new Button(game.getField(x, y), createSingleJButton(x, y));
    }

    private JButton createSingleJButton(int x, int y) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(50, 50));
        button.addMouseListener(new FieldMouseAdapter(this, x, y));
        button.setFont(new Font("Arial", 0, 30));
        button.setMargin(new Insets(0, 0, 0, 0));
        return button;
    }

    protected void displayAllBombs() {
        for (int i = 0; i < size.getWidth(); i++) {
            for (int j = 0; j < size.getHeight(); j++) {
                buttons[i][j].displayBomb();
            }
        }
    }

    Button getButton(int x, int y) {
        return buttons[x][y];
    }

    public Game getGame() {
        return game;
    }

    FirstClickHandler getFirstClickHandler() {
        return firstClickHandler;
    }

    public JButton getJButton(int x, int y) {
        return buttons[x][y].getJButton();
    }

    public int getWidth() {
        return size.getWidth();
    }

    public int getHeight() {
        return size.getHeight();
    }

    void updateFlaggedBombsCount() {
        int unflaggedBombs = game.countUnflaggedBombs();
        bombsCountChanged(unflaggedBombs);
    }

    @Override
    public void bombsCountChanged(int bombsCount) {
        gameFrame.bombsCountChanged(bombsCount);
    }

    @Override
    public void gameFinished(boolean win) {
        displayAllBombs();
        game.finish();
        gameFrame.gameFinished(win);
        if (!win) {
            soundRepository.playSound();
        }
    }

    @Override
    public void displayMessage(String text) {
        gameFrame.displayMessage(text);
    }

    @Override
    public void clearMessage() {
        gameFrame.clearMessage();
    }
}
