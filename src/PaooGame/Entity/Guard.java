package PaooGame.Entity;

import PaooGame.Graphics.Colors;
import PaooGame.Tiles.Tile;

import java.awt.*;

public class Guard extends Entity {
    private Polygon cone = null;
    public Guard(int x, int y, int walkDistance) {
        super(x, y, Tile.TILE_WIDTH - 20, 2*Tile.TILE_HEIGHT);
        this.speed = 1;
        this.cone = Polygons.guardPolygon(x, y);
        //entitySprite
    }

    @Override
    public void Update(int roomX, int roomY, int[][] roomMap) {
        super.Update(roomX, roomY, roomMap);
        updatePolygon(roomX, roomY);
        if(movementQueue.isEmpty()) {
            if(right)
                addMove(Directions.LEFT, 120);
            else
                addMove(Directions.RIGHT, 120);
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
        int[] x = new int[4];
        int[] y = new int[4];

        for(int i = 0; i < x.length; ++i) {
            if(right) {
                x[i] = Polygons.guardX[i] + roomX + posX + Tile.TILE_WIDTH/2;
                y[i] = Polygons.guardY[i] + roomY + posY + Tile.TILE_WIDTH/4;
            }
            else {
                x[i] = -Polygons.guardX[3-i] + roomX + posX + Tile.TILE_WIDTH/2;
                y[i] = Polygons.guardY[3-i] + roomY + posY + Tile.TILE_WIDTH/4;
            }
        }

        cone = new Polygon(x, y, 4);
    }

    public Polygon getDetectionCone() {
        return cone;
    }

}
