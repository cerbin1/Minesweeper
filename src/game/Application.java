package game;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Application extends JPanel {
    final Color[] NEAR_BOMBS_COUNTER_COLORS = {
            Color.BLUE, Color.GREEN, Color.RED, Color.MAGENTA, Color.ORANGE, Color.LIGHT_GRAY, Color.YELLOW, Color.PINK
    };

    private final int height;
    private final int width;

    private JLabel textLabel;
    private Game game;
    private JButton[][] buttons;

    private Application(int height, int width, int numberOfBombs) {
        this.height = height;
        this.width = width;
        this.game = new Game(width, height, numberOfBombs);
        textLabel = new JLabel();
    }


    private void createAndShowBoard() {
        JFrame frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JPanel panel = new JPanel();
        JPanel outerPanel = new JPanel();
        panel.setLayout(new GridLayout(game.getHeight(), game.getWidth()));
        buttons = createJButtons(width, height);


        textLabel.setText("Zecznij klikac");
        outerPanel.setLayout(new BorderLayout());
        outerPanel.add(panel, BorderLayout.CENTER);
        outerPanel.add(textLabel, BorderLayout.PAGE_END);
        frame.add(outerPanel);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.pack();
    }

    private JButton[][] createJButtons(int width, int height) {
        JButton[][] buttons = new JButton[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                buttons[i][j] = createSingleJButton(i, j);
            }
        }
        return buttons;
    }

    private JButton createSingleJButton(int x, int y) {
        JButton button = new JButton();
        button.addMouseListener(new FieldMouseAdapter(this, x, y));
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setPreferredSize(new Dimension(50, 50));
        return button;
    }

    private void registerFloodFillListeners() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Field field = game.getField(i, j);
                JButton button = buttons[i][j];
                field.registerFloodFillListener
            }
        }
    }



    void displayAllBombs() {
        for (int i = 0; i < game.getWidth(); i++) {
            for (int j = 0; j < game.getHeight(); j++) {
                if (game.getField(i, j).isBomb) {
                    buttons[i][j].setText("x");
                    buttons[i][j].setForeground(Color.DARK_GRAY);
                    if (game.getField(i, j).isFlag) {
                        buttons[i][j].setBackground(Color.GREEN);
                    } else {
                        buttons[i][j].setBackground(Color.red);
                    }
                }
            }
        }
    }

    JButton getButton(int x, int y) {
        return buttons[x][y];
    }

    Game getGame() {
        return game;
    }

    void setMessageText(String text) {
        textLabel.setText(text);
    }

    void clearMessageBox() {
        textLabel.setText("");
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Application(10, 10, 10).createAndShowBoard();
            }
        });
    }

}
