package PaooGame.Entity;

import PaooGame.Tiles.Tile;

import java.awt.*;
import java.util.Arrays;

public class Polygons {
    public static int[] guardX = {0, 3* Tile.TILE_WIDTH, 3*Tile.TILE_WIDTH, 2*Tile.TILE_WIDTH + Tile.TILE_WIDTH/2};
    public static int[] guardY = {0, 0, Tile.TILE_WIDTH/2, Tile.TILE_WIDTH};
    public static int[] cameraX = {0, 320, 300, 240};
    public static int[] cameraY = {0, 0, 100, 180};
    public static void rotate(int[] polX, int[] polY, double degrees) {
        double radians = Math.toRadians(degrees);
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);

        for(int i = 0; i < polX.length; ++i) {
            int x = polX[i];
            int y = polY[i];

            polX[i] = (int) Math.round(x*cos - y*sin);
            polY[i] = (int) Math.round(x*sin + y*cos);
        }
    }
}
