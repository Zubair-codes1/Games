package games.tetris.main;

import games.tetris.mino.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class PlayManager {

    private final int WIDTH = 360;
    private final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    // Mino
    Mino currentMino;
    private final int MINO_START_X;
    private final int MINO_START_Y;

    // next mino
    Mino nextMino;
    final int nextMinoStartX;
    final int nextMinoStartY;
    public static ArrayList<Block> staticBlocks = new ArrayList<>();

    // effect
    boolean effectCounterOn;
    int effectCounter;
    ArrayList<Integer> effectY = new ArrayList<>();

    boolean gameOver;

    public static int dropInterval = 60;

    // score and level
    int score;
    int level = 1;
    int lines;

    public PlayManager() {

        // Main play area frame
        left_x = (GamePanel.WIDTH - WIDTH) / 2;
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        MINO_START_X = left_x + (WIDTH / 2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        // next mino
        nextMinoStartX = right_x + 175;
        nextMinoStartY = top_y + 500;

        // current mino
        currentMino = pickRandomMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);

        nextMino = pickRandomMino();
        nextMino.setXY(nextMinoStartX, nextMinoStartY);

    }

    private Mino pickRandomMino() {
        Mino mino = null;
        int random = new Random().nextInt(7);

        switch(random) {
            case 0:
                mino = new Mino_L1();
                break;
            case 1:
                mino = new Mino_L2();
                break;
            case 2:
                mino = new Mino_Square();
                break;
            case 3:
                mino = new Mino_Bar();
                break;
            case 4:
                mino = new Mino_T();
                break;
            case 5:
                mino = new Mino_Z1();
                break;
            case 6:
                mino = new Mino_Z2();
                break;
        }
        return mino;
    }

    public void update() {
        if (currentMino.active == false) {
            staticBlocks.add(currentMino.blocks[0]);
            staticBlocks.add(currentMino.blocks[1]);
            staticBlocks.add(currentMino.blocks[2]);
            staticBlocks.add(currentMino.blocks[3]);

            if (currentMino.blocks[0].x == MINO_START_X && currentMino.blocks[0].y == MINO_START_Y) {
                gameOver = true;
            }

            currentMino.deactivating = false;

            currentMino = nextMino;
            currentMino.setXY(MINO_START_X, MINO_START_Y);
            nextMino = pickRandomMino();
            nextMino.setXY(nextMinoStartX, nextMinoStartY);

            checkDelete();
        }
        else {currentMino.update();}
    }

    private void checkDelete() {
        int x = left_x;
        int y = top_y;
        int blockCounter = 0;
        int lineCounter = 0;

        while (x < right_x && y < bottom_y) {

            for (int i = 0; i < staticBlocks.size(); i++) {
                if (staticBlocks.get(i).x == x && staticBlocks.get(i).y == y) {
                    blockCounter++;
                }
            }

            x += Block.SIZE;

            if (x == right_x) {

                if (blockCounter == 12) {

                    effectCounterOn = true;
                    effectY.add(y);

                    for (int i = staticBlocks.size() - 1; i > -1; i--) {
                        if (staticBlocks.get(i).y == y) {
                            staticBlocks.remove(i);
                        }
                    }

                    lineCounter++;
                    lines++;

                    // increasing drop speed
                    if (lines % 10 == 0 && dropInterval > 1) {

                        level++;
                        if (dropInterval > 10) {
                            dropInterval -= 10;
                        }else {
                            dropInterval -= 1;
                        }
                    }

                    // shifting down the other blocks
                    for (int i = 0; i < staticBlocks.size(); i++) {
                        if (staticBlocks.get(i).y < y) {
                            staticBlocks.get(i).y += Block.SIZE;
                        }
                    }
                }

                blockCounter = 0;
                x = left_x;
                y += Block.SIZE;
            }
        }

        if (lineCounter > 0) {
            int singleLineScore = 10 * level;
            score += singleLineScore * lineCounter;
        }
    }

    public void draw(Graphics2D g) {

        // drawing main play area
        g.setColor(Color.white);
        g.setStroke(new BasicStroke(4f));

        // changed because the stroke value is 4
        g.drawRect(left_x - 4, top_y - 4, WIDTH + 8, HEIGHT + 8);

        int x = right_x + 100;
        int y = bottom_y - 200;
        g.drawRect(x, y, 200, 200);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawString("NEXT", x+60, y+60);

        // score frame
        g.drawRect(x, top_y, 250, 300);
        x += 40;
        y = top_y + 90;
        g.drawString("Level " + level, x, y);
        y += 70;
        g.drawString("Score: " + score, x, y);
        y += 70;
        g.drawString("Score: " + score, x, y);


        if (currentMino != null) {
        currentMino.draw(g);
        }

        // drawing next mino
        nextMino.draw(g);

        for (int i = 0; i < staticBlocks.size(); i++) {
            staticBlocks.get(i).draw(g);
        }

        // draw effect
        if (effectCounterOn) {
            effectCounter++;

            g.setColor(Color.RED);
            for (int i = 0; i < effectY.size(); i++) {
                g.fillRect(left_x, effectY.get(i), WIDTH, Block.SIZE);
            }

            if (effectCounter == 10) {
                effectCounter = 0;
                effectY.clear();
            }
        }

        // draw pause or game over
        g.setColor(Color.yellow);
        g.setFont(g.getFont().deriveFont(50f));
        if (gameOver) {
            x = left_x + 25;
            y = top_y + 320;
            g.drawString("GAME OVER", x, y);
        }
        else if (KeyHandler.pausePressed) {
            x = left_x + 70;
            y = top_y + 320;
            g.drawString("PAUSED", x, y);
        }


        // game title
        x = 35;
        y = top_y + 320;
        g.setColor(Color.WHITE);
        g.setFont(new Font("Times New Roman", Font.BOLD, 60));
        g.drawString("Simple Tetris", x + 30, y);


    }
}
