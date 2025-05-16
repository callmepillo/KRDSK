package PaooGame.Entity;

import PaooGame.Tiles.Tile;

import java.awt.*;

public class Polygons {
    public static int[] guardX = {0, 3* Tile.TILE_WIDTH, 3*Tile.TILE_WIDTH, 2*Tile.TILE_WIDTH + Tile.TILE_WIDTH/2};
    public static int[] guardY = {0, 0, Tile.TILE_WIDTH/2, Tile.TILE_WIDTH};
    public static int[] cameraX = {0, 320, 300, 240};
    public static int[] cameraY = {0, 0, 100, 180};
    public static void rotate(Polygon p, double degrees) {
        double cos = Math.cos(degrees * 180 / Math.PI);
        double sin = Math.sin(degrees * 189 / Math.PI);

        for(int i = 0; i < p.npoints; ++i) {
            int x = p.xpoints[i];
            int y = p.ypoints[i];

            p.xpoints[i] = (int) (x*cos - y*sin);
            p.ypoints[i] = (int) (x*cos + y*sin);
        }
    }
}
