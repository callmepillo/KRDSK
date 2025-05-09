package PaooGame.Levels;

import PaooGame.Graphics.Assets;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Door {
    private int origin = 0;
    private int destination = 0;
    private int x;
    private int y;
    private int destX;
    private int destY;

    public Door(int org, int dest, int x, int y, int dX, int dY) {
        this.origin = org;
        this.destination = dest;
        this.x = x;
        this.y = y;
        this.destX = dX;
        this.destY = dY;
    }

    public int getX() { return x; }
    public int getY() { return y; }

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

    public Rectangle getHitbox(int roomX, int roomY) {
        return new Rectangle(roomX + x, roomY + y, Tile.TILE_WIDTH/2, Tile.TILE_HEIGHT);
    }

    public void drawHitbox(Graphics2D g, int roomX, int roomY) {
        Stroke orgStroke = g.getStroke();
        Color orgColor = g.getColor();

        //drawing the hitbox for debugging purposes
        g.setColor(Color.RED);
        g.setStroke(new java.awt.BasicStroke(3));
        Rectangle hb = getHitbox(roomX, roomY);
        g.drawRect(hb.x, hb.y, hb.width, hb.height);

        g.setStroke(orgStroke);
        g.setColor(orgColor);
    }
}
