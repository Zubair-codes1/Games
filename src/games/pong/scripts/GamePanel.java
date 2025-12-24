package games.pong.scripts;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class GamePanel extends JPanel {

    private Paddle leftPaddle;
    private Paddle rightPaddle;
    private Ball ball;
    private Line2D line;

    public GamePanel() {
        setBackground(Color.BLACK);

        line = new Line2D.Double();
        line.setLine(400, 0, 400, 600);

        leftPaddle = new Paddle(0, 250, 15, 100, 5, Color.WHITE);
        rightPaddle = new Paddle(785, 250, 15, 100, 5, Color.WHITE);

        ball = new Ball(10, 390, 295, 10, 10, Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        leftPaddle.draw(g);
        rightPaddle.draw(g);
        ball.draw(g);
        g.setColor(Color.DARK_GRAY);
        int x = getWidth() / 2;
        g.drawLine(x, 0, x, getHeight());
    }

}
