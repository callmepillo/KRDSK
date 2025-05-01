package PaooGame.GameWindow;

import PaooGame.Levels.Level;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeoutException;

public class CollisionChecker {
    public static boolean CanMoveHere(Rectangle player, int roomX, int roomY, int newX, int newY, int[][] lvlMap) {
        Rectangle room = new Rectangle(roomX, roomY, Level.LEVEL_WIDTH * Tile.TILE_WIDTH, Level.LEVEL_HEIGHT * Tile.TILE_HEIGHT);
        Rectangle newPlayer = new Rectangle(newX, newY, player.width, player.height);
        if(!room.contains(newPlayer))
            return false;

        ArrayList<Rectangle> solidObjects = new ArrayList<>();
        for(int i = 0; i < lvlMap.length; ++i) {
            for(int j = 0; j < lvlMap[i].length; ++j) {
                if(lvlMap[i][j] != 0 && Tile.tiles[lvlMap[i][j]].IsSolid()) {
                    solidObjects.add(new Rectangle(roomX + j*Tile.TILE_WIDTH,roomY + i*Tile.TILE_HEIGHT,  Tile.TILE_WIDTH, Tile.TILE_HEIGHT));
                }
            }
        }

        for(Rectangle obj: solidObjects) {
            if(obj.intersects(newPlayer))
                return false;
        }

        return true;
    }

    public static int CheckCloseToBorder(Rectangle player, int levelOffset, int leftBorderPos, int rightBorderPos) {
        int playerRightBorder = player.x + player.width;
        int playerLeftBorder = player.x;
        int borderRight = rightBorderPos - Tile.TILE_WIDTH;
        int borderLeft = leftBorderPos + Tile.TILE_WIDTH;

        if(playerRightBorder > borderRight)
            levelOffset += playerRightBorder - borderRight;
        else if(playerLeftBorder < borderLeft)
            levelOffset -= borderLeft - playerLeftBorder;

        if(levelOffset > 2*Tile.TILE_WIDTH - 1)
            levelOffset = 2*Tile.TILE_WIDTH - 1;
        else if(levelOffset <= 0)
            levelOffset = 0;

        return levelOffset;
    }

    public static float GetEntityXPosNextToWall(Rectangle2D, Float hitbox, float xSpeed) {
       ///////am ramas aici
        int currentTile=(int)(hitbox.x/);
        if(xSpeed>0){
            //right
        }else {
            //left
        }
    }
}
