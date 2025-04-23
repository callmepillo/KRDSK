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
    private int speed = 1;
    private boolean freezed = false;

    public Player(int x, int y) {
        posX = x;
        posY = y;
        velX = 0;
        velY = 0;
    }

    public void Update(int roomX, int roomY, int[][] roomMap) {
        int newX = posX + velX * speed;
        int newY = posY + velY * speed;
        if(CollisionChecker.CanMoveHere(getRectangle(), roomX, roomY, newX, newY, roomMap) && !freezed) {
            posX = newX;
            posY = newY;
        }

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

        playerSprite = new Tile(Assets.player_idle, 6);

        //drawing the hitbox for debugging purposes
        g.setColor(Color.RED);
        g.setStroke(new java.awt.BasicStroke(3));
        g.drawRect(posX, posY, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);

        playerSprite.Draw(g, posX, posY);

        g.setStroke(orgStroke);
        g.setColor(orgColor);
    }

    public void moveRight() {
        velX += 5;
    }

    public void moveLeft() {
        velX -= 5;
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    public int getNewX() {
        return posX + velX * speed;
    }

    public int getNewY() {
        return posY + velY * speed;
    }

    public void moveDown() {
        velY += 5;
    }

    public void jump() {
        velY -= 5;
    }

    public void setXY(int x, int y) {
        posX = x;
        posY = y;
    }

    public Rectangle getRectangle() {
        return new Rectangle(posX, posY, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
    }

    public void freeze() { freezed = true; }
    public void unfreeze() { freezed = false; }

    //public void moveLeft() {}
    //public void moveRight() {}
    //public void jump() {}
}

//Jucatorul trebuie sa:
//  mearga stanga/dreapta (viteza/acceleratie/whatev)
//  sara (viteza/acceleratie/gravitate)
//  gravitatie!!!