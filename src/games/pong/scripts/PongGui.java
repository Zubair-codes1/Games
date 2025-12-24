package games.pong.scripts;

import javax.swing.*;
import java.awt.*;

public class PongGui {

    private JFrame frame;
    private GamePanel panel;
    private Paddle leftPaddle;
    private Paddle rightPaddle;

    public PongGui() {
        frame = new JFrame("Pong");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 600);

        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);

        frame.setVisible(true);
    }

    private void paintComponent(Graphics g) {
        panel.paintComponent(g);

        leftPaddle.draw(g);
        rightPaddle.draw(g);
    }

}
