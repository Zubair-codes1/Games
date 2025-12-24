package games.pong.scripts;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Paddle {

    private int xPos;
    private int yPos;
    private int height;
    private int width;
    private int speed;
    private Color color;

    private Rectangle2D.Double paddleGui;

    public Paddle(int xPos, int yPos, int width, int height, int speed, Color color) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.color = color;

        paddleGui = new Rectangle2D.Double(xPos, yPos, width, height);
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(xPos, yPos, width, height);
    }

    public Rectangle2D.Double getPaddleGui() {
        return paddleGui;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public int getSpeed() {
        return speed;
    }

    public Rectangle getDimensions() {
        return new Rectangle(xPos, yPos, width, height);
    }

    public void moveUp(){
        yPos += speed;
    }

    public void moveDown(){
        yPos -= speed;
    }

}
