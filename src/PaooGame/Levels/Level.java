package PaooGame.Levels;

import PaooGame.Entity.*;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.util.ArrayList;

public class Level {
    public static final int LEVEL_WIDTH = 10; //Sizes for the level
    public static final int LEVEL_HEIGHT = 6; //Sizes for the level
    public Door[][] doors;
//    public Guard[][] guards;
//    public Camera[][] cameras;
    public Entity[][] entity;
    public int[][][] tileMap;
    static CameraFactory cameraFactory = new CameraFactory();
    static GuardFactory guardFactory = new GuardFactory();
    //this is a tile map that contains the rooms, the rows and the columns of tiles
    //the coordinates correspond to [room][row][column]

    //Draw function for a level
    public void Draw(Graphics2D g, int leftBound, int rightBound, int upperBound, int room, int offset) {
        int origLeftBound = leftBound;
        int origRightBound = rightBound;
/*        leftBound += offset;
        rightBound += offset;*/
        for (int i = 0; i < LEVEL_HEIGHT; ++i) {
            for (int j = 0; j < LEVEL_WIDTH; ++j) {
                if (tileMap[room][i][j] != 0) {
                    int tileX = origLeftBound + j * Tile.TILE_WIDTH - offset;
                    int tileY = upperBound + i * Tile.TILE_HEIGHT;
                    if (tileX + Tile.TILE_WIDTH <= rightBound && tileX >= leftBound)
                        Tile.tiles[tileMap[room][i][j]].Draw(g, tileX, tileY);
                    else if (tileX < rightBound && tileX + Tile.TILE_WIDTH > rightBound)
                        Tile.tiles[tileMap[room][i][j]].Draw(
                                g, tileX, tileY,
                                rightBound - tileX, Tile.TILE_HEIGHT,
                                0, 0,
                                rightBound - tileX, Tile.TILE_HEIGHT
                        );
                    else if (tileX < leftBound && tileX + Tile.TILE_WIDTH > leftBound)
                        Tile.tiles[tileMap[room][i][j]].Draw(
                                g,
                                leftBound, tileY,
                                tileX + Tile.TILE_WIDTH - leftBound, Tile.TILE_HEIGHT,
                                leftBound - tileX, 0,
                                tileX + Tile.TILE_WIDTH - leftBound, Tile.TILE_HEIGHT
                        );
                }
            }
        }

        if (doors != null && room < doors.length) {
            for (Door door : doors[room]) {
                int doorX = leftBound + door.getX() - offset;
                int doorY = upperBound + door.getY();
                int width = door.getWidth();
                if (doorX + width <= rightBound && doorX >= leftBound)
                    door.drawSimple(g, leftBound - offset, upperBound);
                else if (doorX < rightBound && doorX + width > rightBound)
                    door.drawSimple(
                            g, doorX, doorY,
                            rightBound - doorX, Tile.TILE_HEIGHT,
                            0, 0,
                            rightBound - doorX, Tile.TILE_HEIGHT
                    );
                else if (doorX < leftBound && doorX + width > leftBound)
                    door.drawSimple(
                            g,
                            leftBound, doorY,
                            doorX + width - leftBound, Tile.TILE_HEIGHT,
                            leftBound - doorX, 0,
                            doorX + width - leftBound, Tile.TILE_HEIGHT
                    );
            }
        }

//        if (guards != null && room < guards.length) {
//            for (Guard guard : guards[room]) {
//                int guardX = leftBound + guard.getX() - offset;
//                if(guardX + guard.getRectangle().width <= rightBound && guardX >= leftBound)
//                    guard.Draw(g, leftBound - offset, upperBound);
//            }
//        }

//        if (cameras != null && room < cameras.length) {
//            for (Camera cam: cameras[room]) {
//                int camX = leftBound + cam.getX() - offset;
//                if(camX + cam.getRectangle().width <= rightBound && camX >= leftBound)
//                    cam.Draw(g, leftBound - offset, upperBound);
//            }
//        }

        if (entity != null && room < entity.length) {
            for (Entity ent: entity[room]) {
                int camX = leftBound + ent.getX() - offset;
                if(camX + ent.getRectangle().width <= rightBound && camX >= leftBound)
                    ent.Draw(g, leftBound - offset, upperBound);
            }
        }

        /*The draw functions iterates over each tile of a room and draws them if it is visible
         * In this case, the function only searches if the end tile has space in the window
         * (by checking if leftBound + (j+1)* Tile.TILE_WIDHT is smaller than rightBound
         * This will need to be modified when we implement the camera and the player traversal rules
         * For this reason, there is an overloaded function of Tile::Draw that also takes a width
         * and height argument to display only slices of a tile*/
    }

    public int GetNumberOfRooms() {
        return tileMap.length;
    }

    public int[][] GetRoomMap(int x) {
        return tileMap[x];
    }

    public int[][][] getTileMap() {
        return tileMap;
    }

    public Door[] getRoomDoors(int room) {
        if(doors != null && room < doors.length)
            return doors[room];
        else
            return null;
    }

    public Guard[] getRoomGuards(int room) {
//        if(guards != null && room < guards.length)
//            return guards[room];
//        else
//            return null;
        if(entity != null && room < entity.length) {
            ArrayList<Guard> g = new ArrayList<>();
            for (Entity ent : entity[room])
                if (ent instanceof Guard)
                    g.add((Guard) ent);
            return g.toArray(Guard[]::new);
        }
        else
            return null;
    }

    public Camera[] getRoomCameras(int room) {
//        if(cameras != null && room < cameras.length)
//            return cameras[room];
//        else
//            return null;
        if(entity != null && room < entity.length) {
            ArrayList<Camera> g = new ArrayList<>();
            for (Entity ent : entity[room])
                if (ent instanceof Camera)
                    g.add((Camera) ent);
            return g.toArray(Camera[]::new);
        }
        else
            return null;
    }
}
