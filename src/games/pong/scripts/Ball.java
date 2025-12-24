package games.pong.scripts;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Ball {

    private int radius;
    private int xPos;
    private int yPos;
    private int xVel;
    private int yVel;

    private Color color;

    public Ball(int radius, int xPos, int yPos, int xVel, int yVel, Color color) {
        this.radius = radius;
        this.xPos = xPos;
        this.yPos = yPos;
        this.xVel = xVel;
        this.yVel = yVel;
        this.color = color;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(xPos, yPos, radius * 2, radius * 2);
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D.Double(xPos, yPos, radius * 2, radius * 2);
    }

    public int  getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public int getRadius() {
        return radius;
    }

    public void setXVel(int xVel) {
        this.xVel = xVel;
    }

    public void setYVel(int yVel) {
        this.yVel = yVel;
    }

}
