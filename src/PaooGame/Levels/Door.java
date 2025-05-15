package PaooGame.Levels;

import PaooGame.Graphics.Assets;
import PaooGame.Graphics.Colors;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Door {
    private int origin = 0;
    private int destination = 0;
    private final int x;
    private final int y;
    private int destX;
    private int destY;
    private int width;
    private int height;

    public Door(int org, int dest, int x, int y, int destX, int destY) {
        this.origin = org;
        this.destination = dest;
        this.x = x;
        this.y = y;
        this.destX = destX;
        this.destY = destY;
        this.width = Tile.TILE_WIDTH / 2;
        this.height = Tile.TILE_HEIGHT;
    }

    public Door(int org, int dest, int x, int y, int destX, int destY, int width, int height) {
        this(org, dest, x, y, destX, destY);
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void Draw(Graphics g, int roomX, int roomY) {
        Tile.right_down_door.Draw(g, roomX + x, roomY + y);
    }

    public void Draw(Graphics g, int x, int y, int width, int height, int sx, int sy, int sw, int sh) {
        Tile.right_down_door.Draw(g, x, y, width, height, sx, sy, sw, sh);
    }

    public int getDestinationRoom() {
        return destination;
    }

    public int getDestX() {
        return destX;
    }

    public int getDestY() {
        return destY;
    }

    public int getOriginRoom() {
        return origin;
    }

    public int getWidth() {
        return width;
    }

    public Rectangle getHitbox(int roomX, int roomY) {
        return new Rectangle(roomX + x, roomY + y, width, height);
    }

    public void drawSimple(Graphics2D g, int roomX, int roomY) {
        Stroke orgStroke = g.getStroke();
        Color orgColor = g.getColor();

        //drawing the hitbox for debugging purposes
        g.setColor(Colors.yellow);
        g.setStroke(new java.awt.BasicStroke(3));
        //sRectangle hb = getHitbox(roomX, roomY);
        g.fillRect(roomX + x, roomY + y, width, height);

        g.setStroke(orgStroke);
        g.setColor(orgColor);
    }

    public void drawSimple(Graphics2D g, int x, int y, int width, int height, int sx, int sy, int sw, int sh) {
        Stroke orgStroke = g.getStroke();
        Color orgColor = g.getColor();

        //drawing the hitbox for debugging purposes
        g.setColor(Colors.yellow);
        g.setStroke(new java.awt.BasicStroke(3));
        //Rectangle hb = getHitbox(x, y);
        g.fillRect(x, y, width, height);

        g.setStroke(orgStroke);
        g.setColor(orgColor);
    }
}
