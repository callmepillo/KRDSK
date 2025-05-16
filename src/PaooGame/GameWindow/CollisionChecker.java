package PaooGame.GameWindow;

import PaooGame.Entity.Guard;
import PaooGame.Levels.Door;
import PaooGame.Levels.Level;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeoutException;

public class CollisionChecker {
    public static boolean CanMoveHere(Rectangle entity, int roomX, int roomY, int newX, int newY, int[][] lvlMap) {
        Rectangle room = new Rectangle(roomX, roomY, Level.LEVEL_WIDTH * Tile.TILE_WIDTH, Level.LEVEL_HEIGHT * Tile.TILE_HEIGHT);
        Rectangle newEntity = new Rectangle(newX, newY, entity.width, entity.height);
        if(!room.contains(newEntity))
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
            if(obj.intersects(newEntity))
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
        if(airSpeed > 0) {
            //falling
            int tileUnderX = (int) Math.floor((player.x - roomX) / (double) Tile.TILE_WIDTH);
            int tileUnderY = (int) Math.floor((player.x + player.width - roomX) / (double) Tile.TILE_WIDTH);
            int tileUnderLevel = (int) Math.floor((player.y - roomY) / (double) Tile.TILE_HEIGHT);
            int tileYPos = tileUnderLevel * Tile.TILE_HEIGHT + roomY;
            int leftOffset = Tile.TILE_HEIGHT, rightOffset = Tile.TILE_HEIGHT;
            Tile leftTile = null, rightTile = null;
            boolean objectUnder = false;

            if(tileUnderLevel + 1 < 6 && tileUnderY < 10 && tileUnderX >= 0) {
                leftTile = Tile.tiles[lvlMap[tileUnderLevel + 1][tileUnderX]];
                rightTile = Tile.tiles[lvlMap[tileUnderLevel + 1][tileUnderY]];
            }

            if(leftTile != null && leftTile.IsSolid()) {
                leftOffset = leftTile.getHitbox(0, 0).y;
                objectUnder = true;
            }
            if(rightTile != null && rightTile.IsSolid()) {
                rightOffset = rightTile.getHitbox(0, 0).y;
                objectUnder = true;
            }

            int offset = 0;

            if(objectUnder)
                offset = Math.min(leftOffset, rightOffset);

            int yOffset = offset - player.height + Tile.TILE_HEIGHT;

            return tileYPos + yOffset;
        } else {
            //jumping
            int tileAboveX = (int) Math.floor((player.x - roomX) / (double) Tile.TILE_WIDTH);
            int tileAboveY = (int) Math.floor((player.x + player.width - roomX) / (double) Tile.TILE_WIDTH);
            int tileUnderLevel = (int) Math.floor((player.y - roomY) / (double) Tile.TILE_HEIGHT);
            int tileYPos = tileUnderLevel * Tile.TILE_HEIGHT + roomY;
            int leftOffset = Tile.TILE_HEIGHT, rightOffset = Tile.TILE_HEIGHT;
            Tile leftTile = null, rightTile = null;
            boolean objectAbove = false;

            if(0 < tileUnderLevel && tileUnderLevel < 6) {
                leftTile = Tile.tiles[lvlMap[tileUnderLevel][tileAboveX]];
                rightTile = Tile.tiles[lvlMap[tileUnderLevel][tileAboveY]];
            }

            if(leftTile != null && leftTile.IsSolid()) {
                leftOffset = leftTile.getHitbox(0, 0).height;
                objectAbove = true;
            }

            if(rightTile != null && rightTile.IsSolid()) {
                rightOffset = rightTile.getHitbox(0, 0).height;
                objectAbove = true;
            }

            int offset = 0;

            if(objectAbove)
                offset = Math.min(leftOffset, rightOffset);


            return tileUnderLevel * Tile.TILE_HEIGHT + roomY + offset;
        }
    }

    public static boolean IsEntityOnFloor(Rectangle player, int roomX, int roomY, int[][] roomMap) {
        return !CanMoveHere(player, roomX, roomY, player.x, player.y + 1, roomMap);
    }

    public static Door CheckDoor(Rectangle player, int roomX, int roomY, Door[] doors) {
        for(Door door: doors) {
            if (player.intersects(door.getHitbox(roomX, roomY)))
                return door;
        }
        return null;
    }

    public static void CheckPlayerDetected(Rectangle player, Guard[] guards) {
        //Area playerArea = new Area(player);
        for(Guard guard: guards) {
//            System.out.println(Arrays.toString(guard.getDetectionCone().xpoints) + " " + Arrays.toString(guard.getDetectionCone().ypoints));
//            System.out.println((player.x + player.width) + " " + player.y);
            Area guardArea = new Area(guard.getDetectionCone());
            if (guardArea.intersects(player))
                System.out.println("PLAYER DETECED");
        }
    }
}
