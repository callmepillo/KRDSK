package PaooGame.Entity;

import PaooGame.Graphics.Assets;
import PaooGame.Graphics.Colors;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

//player tile ids:
//idle = 93-94
//move_right = 95-114
//move_left = 115-134

public class Guard extends Entity {
    private Polygon cone = null;
    protected Tile entitySprite2;
    protected Tile animIdleBottom;
    protected Tile[] animMoveRightBottom;
    protected Tile[] animMoveLeftBottom;

    public Guard(int x, int y) {
        super(x, y, Tile.TILE_WIDTH, 2*Tile.TILE_HEIGHT);
        this.speed = 1;
        animIdle = new Tile(Assets.guard_move_left_top[0], 93);
        animIdleBottom = new Tile(Assets.guard_move_left_bottom[0], 94);
        animMoveRight = new Tile[10];
        animMoveRightBottom = new Tile[10];
        animMoveLeft = new Tile[10];
        animMoveLeftBottom = new Tile[10];
        for(int i = 0; i < 10; ++i) {
            animMoveRight[i] = new Tile(Assets.guard_move_right_top[i], 95 + i);
            animMoveRightBottom[i] = new Tile(Assets.guard_move_right_bottom[i], 105 + i);
            animMoveLeft[i] = new Tile(Assets.guard_move_left_top[i], 115 + i);
            animMoveLeftBottom[i] = new Tile(Assets.guard_move_left_bottom[i], 125 + i);
        }
        entitySprite = animIdle;
        entitySprite2 = animIdleBottom;
        this.coneWidth = 3*Tile.TILE_WIDTH;
    }

    @Override
    public void Update(int roomX, int roomY, int[][] roomMap) {
        super.Update(roomX, roomY, roomMap);
        updateSprite();
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
        Color orgColor = g.getColor();
        Stroke orgStroke = g.getStroke();
        g.setColor(Colors.detection);
        g.fillPolygon(cone);
        g.setStroke(new BasicStroke(1));
        g.setColor(Colors.detectionOutline);
        g.drawPolygon(cone);

        super.Draw(g, roomX, roomY);
        entitySprite2.Draw(g, posX + roomX, posY + Tile.TILE_HEIGHT + roomY);

        g.setColor(orgColor);
        g.setStroke(orgStroke);
    }

    public void DrawPartial(Graphics2D g, int roomX, int roomY, int x, int y, int width, int sx, int sw) {
        BufferedImage cropped = new BufferedImage(getFullWidth(), 2*Tile.TILE_WIDTH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D c = (Graphics2D) cropped.getGraphics();
        if (right) {
            cone.translate(-(roomX + posX), -(roomY + posY));

            c.setColor(Colors.detection);
            c.fillPolygon(cone);

            c.setStroke(new BasicStroke(1));
            c.setColor(Colors.detectionOutline);
            c.drawPolygon(cone);

            entitySprite.Draw(c, 0, 0); //so its centered on the hitbox
            entitySprite2.Draw(c, 0, Tile.TILE_HEIGHT);

            cone.translate((roomX + posX), (roomY + posY));
        }
        else {
            cone.translate(-(roomX - coneWidth + hitbox.width/2 + posX), -(roomY + posY));

            c.setColor(Colors.detection);
            c.fillPolygon(cone);

            c.setStroke(new BasicStroke(1));
            c.setColor(Colors.detectionOutline);
            c.drawPolygon(cone);

            entitySprite.Draw(c, 3*Tile.TILE_WIDTH - hitbox.width/2, 0); //so its centered on the hitbox
            entitySprite2.Draw(c, 3*Tile.TILE_WIDTH - hitbox.width/2, Tile.TILE_HEIGHT);

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

    @Override
    public void updateSprite() {
        if(right) {
            animationCounter++;
            entitySprite = animMoveRight[animationCounter/5 % 10];
            entitySprite2 = animMoveRightBottom[animationCounter/5 % 10];
            return;
        }
        else if (left){
            animationCounter++;
            entitySprite = animMoveLeft[animationCounter/5 % 10];
            entitySprite2 = animMoveLeftBottom[animationCounter/5 % 10];
            return;
        }
        entitySprite = animIdle;
        entitySprite2 = animIdleBottom;
        animationCounter = 0;
    }

}
