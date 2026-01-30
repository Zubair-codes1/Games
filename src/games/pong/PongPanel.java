package games.pong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PongPanel extends JPanel {

    // Window
    private static final int WIDTH = 800;
    private static final int HEIGHT = 500;

    // Paddle
    private static final int PADDLE_WIDTH = 10;
    private static final int PADDLE_HEIGHT = 80;
    private static final int PADDLE_SPEED = 6;

    // Ball
    private static final int BALL_SIZE = 15;
    private static final int BALL_SPEED = 6;

    // Left paddle
    private int leftY = HEIGHT / 2 - PADDLE_HEIGHT / 2;
    private int leftVY = 0;

    // Right paddle
    private int rightY = HEIGHT / 2 - PADDLE_HEIGHT / 2;
    private int rightVY = 0;

    // Ball
    private int ballX = WIDTH / 2 - BALL_SIZE / 2;
    private int ballY = HEIGHT / 2 - BALL_SIZE / 2;
    private double ballDX = BALL_SPEED;
    private double ballDY = BALL_SPEED;

    // Score
    private int leftScore = 0;
    private int rightScore = 0;

    // Winner
    private int WIN_SCORE = 5;
    private boolean gameOver = false;

    public PongPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);

        setupKeyBindings();
        startGameLoop();
    }

    // ---------------- INPUT ----------------

    private void setupKeyBindings() {
        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        // Left paddle (W / S)
        im.put(KeyStroke.getKeyStroke("pressed W"), "leftUp");
        im.put(KeyStroke.getKeyStroke("released W"), "leftStop");
        im.put(KeyStroke.getKeyStroke("pressed S"), "leftDown");
        im.put(KeyStroke.getKeyStroke("released S"), "leftStop");

        am.put("leftUp", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                leftVY = -PADDLE_SPEED;
            }
        });

        am.put("leftDown", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                leftVY = PADDLE_SPEED;
            }
        });
        am.put("leftStop", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                leftVY = 0;
            }
        });

        // Right paddle (UP / DOWN)
        im.put(KeyStroke.getKeyStroke("pressed UP"), "rightUp");
        im.put(KeyStroke.getKeyStroke("released UP"), "rightStop");
        im.put(KeyStroke.getKeyStroke("pressed DOWN"), "rightDown");
        im.put(KeyStroke.getKeyStroke("released DOWN"), "rightStop");

        am.put("rightUp", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                rightVY = -PADDLE_SPEED;
            }
        });
        am.put("rightDown", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                rightVY = PADDLE_SPEED;
            }
        });
        am.put("rightStop", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                rightVY = 0;
            }
        });

        // resetting the game
        im.put(KeyStroke.getKeyStroke("pressed R"), "reset");
        am.put("reset", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftScore = 0;
                rightScore = 0;
                gameOver = false;
                resetBall();
            }
        });

    }

    // ---------------- GAME LOOP ----------------

    private void startGameLoop() {
        new Timer(16, e -> {
            updatePaddles();
            updateBall();
            checkCollisions();
            repaint();
        }).start();
    }

    // ---------------- LOGIC ----------------

    private void updatePaddles() {
        leftY += leftVY;
        rightY += rightVY;

        leftY = clamp(leftY, 0, HEIGHT - PADDLE_HEIGHT);
        rightY = clamp(rightY, 0, HEIGHT - PADDLE_HEIGHT);
    }

    private void updateBall() {
        ballX += ballDX;
        ballY += ballDY;

        // Top / bottom bounce
        if (ballY <= 0 || ballY >= HEIGHT - BALL_SIZE) {
            ballDY *= -1;
        }
    }

    private void checkCollisions() {
        Rectangle ball = new Rectangle(ballX, ballY, BALL_SIZE, BALL_SIZE);
        Rectangle leftPaddle = new Rectangle(20, leftY, PADDLE_WIDTH, PADDLE_HEIGHT);
        Rectangle rightPaddle = new Rectangle(
                WIDTH - 30, rightY, PADDLE_WIDTH, PADDLE_HEIGHT
        );

        if (ball.intersects(leftPaddle)) {
            ballDX = Math.abs(ballDX) * 1.05;
        }

        if (ball.intersects(rightPaddle)) {
            ballDX = -Math.abs(ballDX) * 1.05;
        }

        if (!gameOver) {
            if (ballX < 0) {
                rightScore++;
                checkWin();
                resetBall();
            }

            if (ballX > WIDTH) {
                leftScore++;
                checkWin();
                resetBall();
            }
        }
    }

    private void checkWin() {
        if (leftScore >= WIN_SCORE || rightScore >= WIN_SCORE) {
            gameOver = true;
        }
    }

    private void resetBall() {
        ballX = WIDTH / 2 - BALL_SIZE / 2;
        ballY = HEIGHT / 2 - BALL_SIZE / 2;
        ballDX = BALL_SPEED * (Math.random() < 0.5 ? -1 : 1);
        ballDY = BALL_SPEED * (Math.random() < 0.5 ? -1 : 1);
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(value, max));
    }

    // ---------------- DRAWING ----------------

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);

        // Center line
        for (int y = 0; y < HEIGHT; y += 20) {
            g.fillRect(WIDTH / 2 - 2, y, 4, 10);
        }

        // Paddles
        g.fillRect(20, leftY, PADDLE_WIDTH, PADDLE_HEIGHT);
        g.fillRect(WIDTH - 30, rightY, PADDLE_WIDTH, PADDLE_HEIGHT);

        // Ball
        g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);

        // Score
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString(String.valueOf(leftScore), WIDTH / 2 - 60, 40);
        g.drawString(String.valueOf(rightScore), WIDTH / 2 + 40, 40);

        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 48));
            String winner = leftScore > rightScore ? "Left Wins!" : "Right Wins!";
            g.drawString(winner, WIDTH / 2 - 140, HEIGHT / 2);
        }

    }

    // ---------------- MAIN ----------------

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Pong");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.add(new PongPanel());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
