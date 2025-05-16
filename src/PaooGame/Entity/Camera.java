package PaooGame.Entity;

import PaooGame.Graphics.Colors;
import PaooGame.Tiles.Tile;

import java.awt.*;

public class Camera extends Entity {
    private Polygon cone = null;
    public Camera(int x, int y, boolean direction, double radians) {
        super(x, y, Tile.TILE_WIDTH/4, Tile.TILE_HEIGHT/4);
        this.speed = 0;
        //entitySprite
    }

    @Override
    public void Update(int roomX, int roomY, int[][] roomMap) {
        updatePolygon(roomX, roomY);
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
        if(cone == null)
            cone = new Polygon(Polygons.cameraX,Polygons.cameraY,4);

        for(int i = 0; i < cone.npoints; ++i) {
            if(right) {
                cone.xpoints[i] = Polygons.guardX[i] + roomX + posX + Tile.TILE_WIDTH/2;
                cone.ypoints[i] = Polygons.guardY[i] + roomY + posY + Tile.TILE_WIDTH/4;
            }
            else {
                cone.xpoints[i] = -Polygons.guardX[3-i] + roomX + posX + Tile.TILE_WIDTH/2;
                cone.ypoints[i] = Polygons.guardY[3-i] + roomY + posY + Tile.TILE_WIDTH/4;
            }
        }
    }

    public Polygon getDetectionCone() {
        return cone;
    }

}
