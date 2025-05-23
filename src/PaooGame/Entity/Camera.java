package PaooGame.Entity;

import PaooGame.Graphics.Colors;
import PaooGame.Tiles.Tile;

import java.awt.*;
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

    public Camera(int x, int y, boolean direction, double degrees, int time) {
        super(x, y, Tile.TILE_WIDTH/4, Tile.TILE_HEIGHT/4);
        this.speed = 0;
        this.degrees = degrees;
        this.time = time;
        this.direction = direction;
        //entitySprite
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
        g.setColor(Colors.detection);
        g.fillPolygon(cone);
        g.setStroke(new BasicStroke(1));
        g.setColor(Colors.detectionOutline);
        g.drawPolygon(cone);
        g.setColor(orgColor);
        g.setStroke(orgStroke);
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
                    xpts[i] = xpts[i] + roomX + posX;
                    ypts[i] = ypts[i] + roomY + posY;
                }
                else {
                    xpts[i] = xpts[xpts.length - 1 - i] + roomX + posX;
                    ypts[i] = ypts[xpts.length - 1 - i] + roomY + posY;
                }
            }
            cone = new Polygon(xpts, ypts, 4);
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
}
