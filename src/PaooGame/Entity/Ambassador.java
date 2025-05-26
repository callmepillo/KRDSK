package PaooGame.Entity;

import PaooGame.Graphics.Assets;
import PaooGame.Graphics.Colors;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

//player tile ids:
//idle = 135-136
//move_right = 137-156
//move_left = 157-176

public class Ambassador extends Entity{
    protected Tile entitySprite2;
    protected Tile animIdleBottom;
    protected Tile[] animMoveRightBottom;
    protected Tile[] animMoveLeftBottom;

    public Ambassador(int x, int y, boolean direction) {
        super(x, y, Tile.TILE_WIDTH, 2*Tile.TILE_HEIGHT);
        this.speed = 1;
        animIdle = new Tile(Assets.amb_idle_top, 135);
        animIdleBottom = new Tile(Assets.amb_idle_bottom, 136);
        animMoveRight = new Tile[10];
        animMoveRightBottom = new Tile[10];
        animMoveLeft = new Tile[10];
        animMoveLeftBottom = new Tile[10];
        for(int i = 0; i < 10; ++i) {
            animMoveRight[i] = new Tile(Assets.amb_salute_right_top[i], 137 + i);
            animMoveRightBottom[i] = new Tile(Assets.amb_salute_right_bottom[i], 147 + i);
            animMoveLeft[i] = new Tile(Assets.amb_salute_left_top[i], 157 + i);
            animMoveLeftBottom[i] = new Tile(Assets.amb_salute_left_bottom[i], 167 + i);
        }
        entitySprite = animIdle;
        entitySprite2 = animIdleBottom;
        if(direction) {
            right = true;
            left = false;
        }
        else {
            right = false;
            left = true;
        }
    }

    @Override
    public void Update(int roomX, int roomY, int[][] roomMap) {
        updateSprite();
    }

    @Override
    public void Draw(Graphics2D g, int roomX, int roomY) {
        super.Draw(g, roomX, roomY);
        entitySprite2.Draw(g, posX + roomX, posY + Tile.TILE_HEIGHT + roomY);
    }

    public void DrawPartial(Graphics2D g, int roomX, int roomY, int x, int y, int width, int sx, int sw) {
        this.Draw(g, roomX, roomY);
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
