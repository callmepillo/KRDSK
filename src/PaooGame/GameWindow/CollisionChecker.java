package PaooGame.GameWindow;

import PaooGame.Tiles.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeoutException;

public class CollisionChecker {
    public static boolean CanMoveHere(Rectangle player, int roomX, int roomY, int newX, int newY, int[][] lvlMap) {
        Rectangle room = new Rectangle(roomX, roomY, FauxWindow.width, FauxWindow.height);
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
}
