package PaooGame.Levels;

import PaooGame.Tiles.Tile;

import java.awt.*;

public class Level {
    public static final int LEVEL_WIDTH = 10; //Sizes for the level
    public static final int LEVEL_HEIGHT = 6; //Sizes for the level
    public Door[] doors;
    public int[][][] tileMap;
    //this is a tile map that contains the rooms, the rows and the columns of tiles
    //the coordinates correspond to [room][row][column]

    //Draw function for a level
    public void Draw(Graphics2D g, int leftBound, int rightBound, int upperBound, int room, int offset) {
        int origLeftBound = leftBound;
        int origRightBound = rightBound;
/*        leftBound += offset;
        rightBound += offset;*/
        for(int i = 0; i < LEVEL_HEIGHT; ++i) {
            for(int j = 0; j < LEVEL_WIDTH; ++j) {
                if(tileMap[room][i][j] != 0) {
                    int tileX = origLeftBound + j * Tile.TILE_WIDTH - offset;
                    int tileY = upperBound + i * Tile.TILE_HEIGHT;
                    if (tileX + Tile.TILE_WIDTH <= rightBound && tileX >= leftBound)
                        Tile.tiles[tileMap[room][i][j]].Draw(g, tileX,  tileY);
                    else if (tileX < rightBound && tileX + Tile.TILE_WIDTH > rightBound)
                        Tile.tiles[tileMap[room][i][j]].Draw(
                                g, tileX,  tileY,
                                rightBound - tileX, Tile.TILE_HEIGHT,
                                0, 0,
                                rightBound - tileX, Tile.TILE_HEIGHT
                        );
                    else if (tileX < leftBound && tileX + Tile.TILE_WIDTH > leftBound)
                        Tile.tiles[tileMap[room][i][j]].Draw(
                                g,
                                leftBound,  tileY,
                                tileX + Tile.TILE_WIDTH - leftBound, Tile.TILE_HEIGHT,
                                leftBound - tileX, 0,
                                tileX + Tile.TILE_WIDTH - leftBound, Tile.TILE_HEIGHT
                        );
                }
            }
        }

        if(doors != null) {
            for (Door door : doors) {
                if (door.getOriginRoom() == room) {
                    int doorX = leftBound + door.getX() - offset;
                    int doorY = upperBound + door.getY();
                    if (doorX + Tile.TILE_WIDTH <= rightBound && doorX >= leftBound)
                        door.Draw(g, leftBound - offset, upperBound);
                    else if (doorX < rightBound && doorX + Tile.TILE_WIDTH > rightBound)
                        door.Draw(
                                g, doorX, doorY,
                                rightBound - doorX, Tile.TILE_HEIGHT,
                                0, 0,
                                rightBound - doorX, Tile.TILE_HEIGHT
                        );
                    else if (doorX < leftBound && doorX + Tile.TILE_WIDTH > leftBound)
                        door.Draw(
                                g,
                                leftBound, doorY,
                                doorX + Tile.TILE_WIDTH - leftBound, Tile.TILE_HEIGHT,
                                leftBound - doorX, 0,
                                doorX + Tile.TILE_WIDTH - leftBound, Tile.TILE_HEIGHT
                        );
                }
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
    public int[][] GetRoomMap(int x) { return tileMap[x]; }
    public int[][][] getTileMap() { return tileMap; }
    public Door[] getDoors() { return doors; }
}
