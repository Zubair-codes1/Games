package games.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private int boardWidth;
    private int boardHeight;
    private int tileSize = 25;

    private Tile snakeHead;
    private Tile food;

    private Random random;
    Timer gameLoopTimer;

    private int velocityX;
    private int velocityY;

    private ArrayList<Tile> snakeBody;

    private boolean gameOver;

    public SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true); // listen to key presses

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<>();

        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;

        gameLoopTimer = new Timer(100, this);
        gameLoopTimer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // tiles
        /*
        for (int i = 0; i < boardHeight/tileSize; i++) {
            g.setColor(Color.DARK_GRAY);
            g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);  // vertical lines
            g.drawLine(0,  i*tileSize, boardWidth, i*tileSize); // horizontal lines
        }
        */

        // food
        g.setColor(Color.RED);
        g.fill3DRect(food.getX() * tileSize, food.getY() * tileSize, tileSize, tileSize, true);

        //snake head
        g.setColor(Color.GREEN);
        g.fill3DRect(snakeHead.getX() * tileSize, snakeHead.getY() * tileSize, tileSize, tileSize, true);

        // snake body
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            g.fill3DRect(snakePart.getX() * tileSize, snakePart.getY() * tileSize, tileSize, tileSize, true);
        }

        // Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver) {
            g.setColor(Color.RED);
            g.drawString("GAME OVER : " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }else {
            g.drawString("SCORE: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
    }

    private void placeFood() {
        int randomX = random.nextInt(boardWidth / tileSize);
        int randomY = random.nextInt(boardHeight / tileSize);
        int flag = 0;
        for  (int i = 0; i < snakeBody.size(); i++) {
            if (randomX == snakeBody.get(i).getX() && randomY == snakeBody.get(i).getY()) {
                flag = 1;
            }
        }

        if (flag == 0) {
            food.setX(randomX);
            food.setY(randomY);
        }else placeFood();
    }

    private boolean collision(Tile tile1, Tile tile2) {
        return tile1.getX() == tile2.getX() && tile1.getY() == tile2.getY();
    }

    private void move(){
        // eating food
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.getX(), food.getY()));
            placeFood();
        }

        // snake body
        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.setX(snakeHead.getX());
                snakePart.setY(snakeHead.getY());
            }
            else {
                Tile previousSnakePart = snakeBody.get(i-1);
                snakePart.setX(previousSnakePart.getX());
                snakePart.setY(previousSnakePart.getY());

            }
        }

        // snake head
        snakeHead.setX(snakeHead.getX() +  velocityX);
        snakeHead.setY(snakeHead.getY() + velocityY);

        // game over
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }

        if (
                snakeHead.getX() * tileSize < 0 || snakeHead.getX() * tileSize > boardWidth
                || snakeHead.getY() * tileSize < 0 || snakeHead.getY() * tileSize > boardHeight
        ) {
            gameOver = true;
        }
    }

    private void reset(){
        snakeHead.setX(5);
        snakeHead.setY(5);
        snakeBody.clear();
        gameOver = false;
        velocityX = 0;
        velocityY = 0;
        placeFood();
        gameLoopTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) {
            gameLoopTimer.stop();
        }
        move();
        repaint();

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if  (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1; // reversed
        }else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        }else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        }else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }else if (e.getKeyCode() == KeyEvent.VK_Q) {
            reset();
        }
    }

    // not needed
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
