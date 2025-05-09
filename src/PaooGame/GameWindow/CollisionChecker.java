package PaooGame.GameWindow;

import PaooGame.Levels.Door;
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
                    //solidObjects.add(new Rectangle(roomX + j*Tile.TILE_WIDTH,roomY + i*Tile.TILE_HEIGHT,  Tile.TILE_WIDTH, Tile.TILE_HEIGHT));
                    solidObjects.add(Tile.tiles[lvlMap[i][j]].getHitbox(roomX + j*Tile.TILE_WIDTH, roomY + i*Tile.TILE_HEIGHT));
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

    public static int GetEntityXPosNextToWall(Rectangle player, int roomX, int xSpeed) {
        int currentTile = (int) Math.round( (player.x - roomX) / (double) Tile.TILE_WIDTH);
        if( xSpeed > 0 ) {
            int tileXPos = currentTile * Tile.TILE_WIDTH + roomX;
            int xOffset = Tile.TILE_WIDTH - player.width;
            return tileXPos + xOffset;
        }else {
            return currentTile * Tile.TILE_WIDTH + roomX;
        }
    }

    public static int GetEntityYPosUnderRoofOrAboveFloor(Rectangle player, int roomX, int roomY, int airSpeed, int[][] lvlMap) {
        int currentTileY = (int) Math.floor( (player.y - roomY) / (double) Tile.TILE_HEIGHT);

        //Calculate current tile in order to implement hitbox
        int currentTileX = (int) Math.round( (player.x - roomX) / (double) Tile.TILE_WIDTH);
        int aboveOffset = 0;
        int underOffset = 0;
        int tileYPos = currentTileY * Tile.TILE_HEIGHT + roomY;
        Tile AboveTile = Tile.tiles[lvlMap[currentTileY][currentTileX]];

        Tile UnderTile = null;
        if(currentTileY + 1 < 6)
            UnderTile = Tile.tiles[lvlMap[currentTileY + 1][currentTileX]];

        if(AboveTile != null && AboveTile.IsSolid()) {
            aboveOffset = AboveTile.getHitbox(0, 0).height;
        }

        if(UnderTile != null && UnderTile.IsSolid()) {
            underOffset = UnderTile.getHitbox(0, 0).y;
        }

        if( airSpeed > 0 ) {
            int yOffset = Tile.TILE_HEIGHT - player.height;
            return tileYPos + yOffset + underOffset;
        }else {
            return currentTileY * Tile.TILE_HEIGHT + roomY + aboveOffset;
        }
    }

    public static boolean IsEntityOnFloor(Rectangle player, int roomX, int roomY, int[][] roomMap) {
        return !CanMoveHere(player, roomX, roomY, player.x, player.y + 1, roomMap);
    }

    public static int CheckDoor(Rectangle player, int roomX, int roomY, Door[] doors) {
        for(Door door: doors) {
            if (player.intersects(door.getHitbox(roomX, roomY)))
                return door.getDestinationRoom();
        }
        return doors[0].getOriginRoom();
    }
}
