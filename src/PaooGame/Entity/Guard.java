package PaooGame.Entity;

import PaooGame.Graphics.Assets;
import PaooGame.Graphics.Colors;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Guard extends Entity {
    private Polygon cone = null;
    protected Tile entitySprite2;
    public Guard(int x, int y) {
        super(x, y, Tile.TILE_WIDTH, 2*Tile.TILE_HEIGHT);
        this.speed = 1;
        entitySprite = new Tile(Assets.round_tree_up, 3);
        entitySprite2 = new Tile(Assets.round_tree_down, 4);
        this.coneWidth = 3*Tile.TILE_WIDTH;
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

        entitySprite2.Draw(g, posX + roomX, posY + Tile.TILE_HEIGHT + roomY);
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

    public void DrawPartial(Graphics g, int roomX, int roomY, int x, int y, int width, int sx, int sw) {
        BufferedImage cropped = new BufferedImage(getFullWidth(), 2*Tile.TILE_WIDTH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D c = (Graphics2D) cropped.getGraphics();
        if (right) {
            cone.translate(-(roomX + posX), -(roomY + posY));

            entitySprite.Draw(c, 0, 0); //so its centered on the hitbox
            entitySprite2.Draw(c, 0, Tile.TILE_HEIGHT);

            c.setColor(Colors.detection);
            c.fillPolygon(cone);

            c.setStroke(new BasicStroke(1));
            c.setColor(Colors.detectionOutline);
            c.drawPolygon(cone);

            cone.translate((roomX + posX), (roomY + posY));
        }
        else {
            cone.translate(-(roomX - coneWidth + hitbox.width/2 + posX), -(roomY + posY));

            entitySprite.Draw(c, 3*Tile.TILE_WIDTH - hitbox.width/2, 0); //so its centered on the hitbox
            entitySprite2.Draw(c, 3*Tile.TILE_WIDTH - hitbox.width/2, Tile.TILE_HEIGHT);

            c.setColor(Colors.detection);
            c.fillPolygon(cone);

            c.setStroke(new BasicStroke(1));
            c.setColor(Colors.detectionOutline);
            c.drawPolygon(cone);

            cone.translate((roomX - coneWidth + hitbox.width/2 + posX), (roomY + posY));
        }
        cropped = cropped.getSubimage(sx, 0, sw, 2*Tile.TILE_HEIGHT);
        g.drawImage(cropped, x, y, width, 2*Tile.TILE_HEIGHT, null);
    }

    public void updatePolygon(int roomX, int roomY) {
        if(cone == null)
            cone = new Polygon(Polygons.guardX,Polygons.guardY,4);

        for(int i = 0; i < cone.npoints; ++i) {
            if(right) {
                cone.xpoints[i] = Polygons.guardX[i] + roomX + posX + hitbox.width/2;
                cone.ypoints[i] = Polygons.guardY[i] + roomY + posY + hitbox.height/4;
            }
            else {
                cone.xpoints[i] = -Polygons.guardX[3-i] + roomX + posX + hitbox.width/2;
                cone.ypoints[i] = Polygons.guardY[3-i] + roomY + posY + hitbox.height/4;
            }
        }
    }

    @Override
    public int getFullWidth() {
        return 3*Tile.TILE_WIDTH + hitbox.width/2;
    }

    public Polygon getDetectionCone() {
        return cone;
    }

}
