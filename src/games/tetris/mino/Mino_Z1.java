package games.tetris.mino;

import java.awt.*;

public class Mino_Z1 extends Mino {
    public Mino_Z1() {
        create(Color.red);
    }

    public void setXY(int x, int y) {
        blocks[0].x =  x;
        blocks[0].y = y;
        blocks[1].x = blocks[0].x;
        blocks[1].y = blocks[0].y - Block.SIZE;
        blocks[2].x = blocks[0].x - Block.SIZE;
        blocks[2].y = blocks[0].y;
        blocks[3].x = blocks[0].x - Block.SIZE;
        blocks[3].y = blocks[0].y + Block.SIZE;
    }
    public void getDirection1() {
        tempBlocks[0].x = blocks[0].x;
        tempBlocks[0].y = blocks[0].y;
        tempBlocks[1].x = blocks[0].x;
        tempBlocks[1].y = blocks[0].y - Block.SIZE;
        tempBlocks[2].x = blocks[0].x - Block.SIZE;
        tempBlocks[2].y = blocks[0].y;
        tempBlocks[3].x = blocks[0].x - Block.SIZE;
        tempBlocks[3].y = blocks[0].y + Block.SIZE;

        updateXY(1);
    }
    public void getDirection2() {
        tempBlocks[0].x = blocks[0].x;
        tempBlocks[0].y = blocks[0].y;
        tempBlocks[1].x = blocks[0].x + Block.SIZE;
        tempBlocks[1].y = blocks[0].y;
        tempBlocks[2].x = blocks[0].x;
        tempBlocks[2].y = blocks[0].y - Block.SIZE;
        tempBlocks[3].x = blocks[0].x - Block.SIZE;
        tempBlocks[3].y = blocks[0].y - Block.SIZE;

        updateXY(2);
    }
    public void getDirection3() {
        getDirection1();
    }
    public void getDirection4() {
        getDirection2();
    }
}
