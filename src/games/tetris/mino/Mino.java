package games.tetris.mino;

import games.tetris.main.KeyHandler;
import games.tetris.main.PlayManager;

import java.awt.*;

public class Mino {

    public Block[] blocks = new Block[4];
    public Block[] tempBlocks = new  Block[4];
    int autoDropCounter = 0;
    public int direction = 1;
    private boolean leftCollision, rightCollision, bottomCollision;
    public boolean active = true;
    public boolean deactivating;
    int deactivateCounter = 0;

    public void create(Color c) {
        blocks[0] = new Block(c);
        blocks[1] = new Block(c);
        blocks[2] = new Block(c);
        blocks[3] = new Block(c);
        tempBlocks[0] = new Block(c);
        tempBlocks[1] = new Block(c);
        tempBlocks[2] = new Block(c);
        tempBlocks[3] = new Block(c);
    }

    public void setXY(int x, int y) {

    }

    public void updateXY(int direction) {

        checkRotationCollision();

        if (leftCollision == false && rightCollision == false && bottomCollision == false) {
            this.direction = direction;
            blocks[0].x = tempBlocks[0].x;
            blocks[0].y = tempBlocks[0].y;
            blocks[1].x = tempBlocks[1].x;
            blocks[1].y = tempBlocks[1].y;
            blocks[2].x = tempBlocks[2].x;
            blocks[2].y = tempBlocks[2].y;
            blocks[3].x = tempBlocks[3].x;
            blocks[3].y = tempBlocks[3].y;
        }

    }

    private void deactivate() {
        deactivateCounter++;

        if (deactivateCounter == 45) {
            deactivateCounter = 0;
            checkMovementCollision();

            if (bottomCollision) {
                active = false;
            }
        }
    }

    public void getDirection1() {}
    public void getDirection2() {}
    public void getDirection3() {}
    public void getDirection4() {}

    public void checkMovementCollision() {
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        checkStaticBlockCollision();

        for (int index = 0; index < blocks.length; index++) {
            if (blocks[index].x == PlayManager.left_x) {
                leftCollision = true;
            }
            if (blocks[index].x + Block.SIZE == PlayManager.right_x) {
                rightCollision = true;
            }
            if (blocks[index].y + Block.SIZE == PlayManager.bottom_y) {
                bottomCollision = true;
            }
        }
    }

    public void checkRotationCollision() {
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        checkStaticBlockCollision();

        for (int index = 0; index < blocks.length; index++) {
            if (tempBlocks[index].x < PlayManager.left_x) {
                leftCollision = true;
            }
            if (tempBlocks[index].x + Block.SIZE > PlayManager.right_x) {
                rightCollision = true;
            }
            if (tempBlocks[index].y + Block.SIZE > PlayManager.bottom_y) {
                bottomCollision = true;
            }
        }
    }

    public void checkStaticBlockCollision() {
        for (int index = 0; index < PlayManager.staticBlocks.size(); index++) {
            int targetX = PlayManager.staticBlocks.get(index).x;
            int targetY = PlayManager.staticBlocks.get(index).y;

            // check down
            for (int y = 0; y < blocks.length; y++) {
                if (blocks[y].y + Block.SIZE == targetY && blocks[y].x == targetX) {
                    bottomCollision = true;
                }
            }

            // check left
            for (int y = 0; y < blocks.length; y++) {
                if (blocks[y].x - Block.SIZE == targetX && blocks[y].y == targetY) {
                    leftCollision = true;
                }
            }

            // check right
            for (int y = 0; y < blocks.length; y++) {
                if (blocks[y].x + Block.SIZE == targetX && blocks[y].y == targetY) {
                    rightCollision = true;
                }
            }
        }
    }

    public void update() {

        if (deactivating) {
            deactivate();
        }

        // controlling the mino
        if (KeyHandler.upPressed) {
            // for rotation

            switch (direction) {
                case 1:
                    getDirection2();
                    break;
                case 2:
                    getDirection3();
                    break;
                case 3:
                    getDirection4();
                    break;
                case 4:
                    getDirection1();
                    break;
            }

            KeyHandler.upPressed = false;
        }

        checkMovementCollision();

        if (KeyHandler.downPressed) {
            if (!bottomCollision) {
                blocks[0].y += Block.SIZE;
                blocks[1].y += Block.SIZE;
                blocks[2].y += Block.SIZE;
                blocks[3].y += Block.SIZE;

                autoDropCounter = 0;
            }

            KeyHandler.downPressed = false;
        }

        if (KeyHandler.leftPressed) {

            if (!leftCollision) {
                blocks[0].x -= Block.SIZE;
                blocks[1].x -= Block.SIZE;
                blocks[2].x -= Block.SIZE;
                blocks[3].x -= Block.SIZE;

                autoDropCounter = 0;
            }
            KeyHandler.leftPressed = false;
        }

        if (KeyHandler.rightPressed) {

            if (!rightCollision) {
                blocks[0].x += Block.SIZE;
                blocks[1].x += Block.SIZE;
                blocks[2].x += Block.SIZE;
                blocks[3].x += Block.SIZE;

                autoDropCounter = 0;
            }
            KeyHandler.rightPressed = false;
        }

        if (bottomCollision) { deactivating = true; }
        else {
            // moving the block down automatically
            autoDropCounter++;  // increasing the counter
            if (autoDropCounter == PlayManager.dropInterval) {
                blocks[0].y += Block.SIZE;
                blocks[1].y += Block.SIZE;
                blocks[2].y += Block.SIZE;
                blocks[3].y += Block.SIZE;
                autoDropCounter = 0;
            }
        }

    }

    public void draw(Graphics2D g2d) {

        int margin = 2;
        g2d.setColor(blocks[0].color);
        g2d.fillRect(
                blocks[0].x + margin,blocks[0].y + margin,
                Block.SIZE - (margin* 2), Block.SIZE -  (margin* 2)
        );
        g2d.fillRect(
                blocks[1].x + margin,blocks[1].y + margin,
                Block.SIZE - (margin* 2), Block.SIZE -  (margin* 2)
        );
        g2d.fillRect(
                blocks[2].x + margin,blocks[2].y + margin,
                Block.SIZE - (margin* 2), Block.SIZE -  (margin* 2)
        );
        g2d.fillRect(
                blocks[3].x + margin,blocks[3].y + margin,
                Block.SIZE - (margin* 2), Block.SIZE -  (margin* 2)
        );
    }
}
