package PaooGame.Entity;

import PaooGame.Graphics.Assets;
import PaooGame.Graphics.Colors;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.Arrays;

public class Camera extends Entity {
    private Polygon cone = null;
    private boolean rotDiraction = true;
    private boolean direction = false;
    private int time = 0;
    private double degrees = 0;
    private int[] coneX = Polygons.cameraX.clone();
    private int[] coneY = Polygons.cameraY.clone();

    public Camera(int x, int y) {
        super(x, y, Tile.TILE_WIDTH/4, Tile.TILE_HEIGHT/4);
        this.speed = 0;
        this.coneWidth = 4*Tile.TILE_WIDTH;
    }

    public void setAnim(boolean direction, double degrees, int time) {
        this.direction = direction;
        this.degrees = degrees;
        this.time = time;
    }

    @Override
    public void Update(int roomX, int roomY, int[][] roomMap) {
        updatePolygon(roomX, roomY);

        if(moveBuffer != 0) {
            moveBuffer--;
        }
        else {
            moveBuffer = time;
            rotDiraction = !rotDiraction;
        }
    }

    @Override
    public void Draw(Graphics2D g, int roomX, int roomY) {
        super.Draw(g, roomX, roomY);

        Color orgColor = g.getColor();
        Stroke orgStroke = g.getStroke();

        g.setColor(Color.RED);
        g.setStroke(new java.awt.BasicStroke(3));
        g.fillRect(hitbox.x + roomX, hitbox.y + roomY, hitbox.width, hitbox.height);

        g.setColor(Colors.detection);
        g.fillPolygon(cone);

        g.setStroke(new BasicStroke(1));
        g.setColor(Colors.detectionOutline);
        g.drawPolygon(cone);

        g.setColor(orgColor);
        g.setStroke(orgStroke);
    }

    public void DrawPartial(Graphics g, int roomX, int roomY, int x, int y, int width, int sx, int sw) {
        BufferedImage cropped = new BufferedImage(getFullWidth(), 4*Tile.TILE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D c = (Graphics2D) cropped.getGraphics();
        if (!direction) {
            cone.translate(-(roomX + posX), -(roomY + posY));
            //entitySprite.Draw(c, 0, 0); //so its centered on the hitbox
            c.setColor(Color.RED);
            c.setStroke(new java.awt.BasicStroke(3));
            c.fillRect(0, 0, hitbox.width, hitbox.height);

            c.setColor(Colors.detection);
            c.fillPolygon(cone);

            c.setColor(Colors.detectionOutline);
            c.setStroke(new BasicStroke(1));
            c.drawPolygon(cone);

            cone.translate((roomX + posX), (roomY + posY));
        }
        else {
            cone.translate(-(roomX - coneWidth + posX), -(roomY + posY));
            //entitySprite.Draw(c, getFullWidth() - hitbox.width, 0); //so its centered on the hitbox

            c.setColor(Color.RED);
            c.setStroke(new java.awt.BasicStroke(3));
            c.fillRect(getFullWidth() - hitbox.width, 0, hitbox.width, hitbox.height);

            c.setColor(Colors.detection);
            c.fillPolygon(cone);

            c.setColor(Colors.detectionOutline);
            c.setStroke(new BasicStroke(1));
            c.drawPolygon(cone);

            cone.translate((roomX - coneWidth + posX), (roomY + posY));
        }
        cropped = cropped.getSubimage(sx, 0, sw, 4*Tile.TILE_HEIGHT);
        g.drawImage(cropped, x, y, width, 4*Tile.TILE_HEIGHT, null);
    }

    public void updatePolygon(int roomX, int roomY) {
        if(rotDiraction)
            Polygons.rotate(coneX, coneY, degrees/time);
        else
            Polygons.rotate(coneX, coneY, -degrees/time);

        if(cone == null) {
            int[] xpts = Polygons.cameraX;
            int[] ypts = Polygons.cameraY;

            for(int i = 0; i < xpts.length; ++i) {
                if(direction) {
                    coneX[i] = -xpts[i];
                    coneY[i] = ypts[i];
                }
                else {
                    coneX[i] = xpts[xpts.length - 1 - i];
                    coneY[i] = ypts[xpts.length - 1 - i];
                }
            }
            cone = new Polygon(coneX, coneY, 4);
        }
        else {
            for(int i = 0; i < coneX.length; ++i) {
                cone.xpoints[i] = coneX[i] + roomX + posX;
                cone.ypoints[i] = coneY[i] + roomY + posY;
            }
        }
    }

    public Polygon getDetectionCone() {
        return cone;
    }

    @Override
    public int getFullWidth() {
        return 4*Tile.TILE_WIDTH + hitbox.width;
    }

    @Override
    public boolean getDirection() {
        return !direction;
    }
}
