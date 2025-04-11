package PaooGame.GameWindow;

import PaooGame.Graphics.Assets;
import PaooGame.Tiles.Tile;

import java.awt.*;

public class Player {
    private Tile playerSprite;
    private int posX;
    private int posY;
    private int velX;
    private int velY;
    private int speed = 10;

    public Player(int x, int y) {
        posX = x;
        posY = y;
        velX = 0;
        velY = 0;
    }

    public void Update() {
        posX += velX * speed;
        posY += velY * speed;

        if(velX > 0) {
            --velX;
        }
        else if(velX < 0) {
            ++velX;
        }

        if(velY > 0)
            --velY;
        else if(velY < 0)
            ++velY;
    }

    public void Draw(Graphics2D g) {
        Stroke orgStroke = g.getStroke();
        Color orgColor = g.getColor();

        playerSprite = new Tile(Assets.bonzai, 6);

        g.setColor(Color.CYAN);
        g.setStroke(new java.awt.BasicStroke(3));
        g.drawRect(posX, posY, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);

        playerSprite.Draw(g, posX, posY);

        g.setStroke(orgStroke);
        g.setColor(orgColor);
    }

    public void addX() {
        velX += 5;
    }

    public void subX() {
        velX -= 5;
    }

    public int getX() {
        return posX;
    }

    public void addY() {
        velY += 5;
    }

    public void subY() {
        velY -= 5;
    }

    public int getY() {
        return posY;
    }
}
