package games.tetris.main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    private final int FPS = 60;
    Thread gameThread;
    PlayManager playManager;

    public GamePanel() {

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);

        // key listener
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);

        playManager = new PlayManager();


    }

    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();  // automatically calls the run method
    }

    public void update() {
        if (KeyHandler.pausePressed == false && playManager.gameOver == false) {
            playManager.update();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        playManager.draw(g2d);
    }

    @Override
    public void run() {

        // Game loop
        double drawInterval = (double) 1000000000 / FPS;
        double deltaTime = 0;
        long lastTime = System.nanoTime();
        long currentTime;  // current time

        while (gameThread != null) {
            currentTime = System.nanoTime();
            deltaTime += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (deltaTime >= 1) {
                update();
                repaint();  // calls the paint component method
                deltaTime--;
            }
        }

    }
}
