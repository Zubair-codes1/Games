package games.tetris.mino;

import java.awt.*;

// represents one block
public class Block extends Rectangle {
    public int x, y;
    public static final int SIZE = 30;
    public Color color;

    public Block(Color color) {
        this.color = color;
    }

    public void draw(Graphics2D g2d) {
        int margin = 2;
        g2d.setColor(color);
        g2d.fillRect(x + margin, y + margin, SIZE-(margin * 2), SIZE - (margin * 2));
    }
}
