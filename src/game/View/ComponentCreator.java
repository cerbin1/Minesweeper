package game.View;

import game.Application;
import game.FieldMouseAdapter;
import game.Game;

import javax.swing.*;
import java.awt.*;

import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class ComponentCreator {
    private Application application;

    public ComponentCreator(Application application) {
        this.application = application;
    }

    public JButton createSingleJButton(Application application, int x, int y) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(50, 50));
        button.addMouseListener(new FieldMouseAdapter(application, x, y));
        button.setFont(new Font("Arial", 0, 30));
        button.setMargin(new Insets(0, 0, 0, 0));
        return button;
    }

    public JFrame createMainJFrame() {
        JFrame frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
        return frame;
    }

    public JLabel createDefaultTextLabel(String name, String defaultText) {
        JLabel label = new JLabel(name, SwingConstants.CENTER);
        label.setText(defaultText);
        label.setBorder(createEmptyBorder(15, 15, 15, 15));
        label.setFont(new Font("Arial", Font.BOLD, 20));
        return label;
    }

    public JPanel createJPanelWithJButtons() {
        JPanel panel = new JPanel();
        Game game = application.getGame();
        panel.setLayout(new GridLayout(game.getWidth(), game.getHeight()));
        for (int i = 0; i < game.getWidth(); i++) {
            for (int j = 0; j < game.getHeight(); j++) {
                panel.add(application.getButton(i, j).getJButton());
            }
        }
        return panel;
    }

    public JPanel createOuterPanel(JPanel innerPanel) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(application.getBombsLeftLabel(), BorderLayout.PAGE_START);
        panel.add(innerPanel, BorderLayout.CENTER);
        panel.add(application.getMessageBox(), BorderLayout.PAGE_END);
        return panel;
    }
}
