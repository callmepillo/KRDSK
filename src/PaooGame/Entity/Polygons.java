package PaooGame.Entity;

import PaooGame.Tiles.Tile;

import java.awt.*;

public class Polygons {
    public static Polygon guardPolygon(int posX, int posY) {
        int[] x = {0,3* Tile.TILE_WIDTH,3*Tile.TILE_WIDTH,2*Tile.TILE_WIDTH + Tile.TILE_WIDTH/2};
        int[] y = {0,0, Tile.TILE_WIDTH/2, Tile.TILE_WIDTH};
        return new Polygon(x,y,4);
    }
    public static int[] guardX = {0,3* Tile.TILE_WIDTH,3*Tile.TILE_WIDTH,2*Tile.TILE_WIDTH + Tile.TILE_WIDTH/2};
    public static int[] guardY = {0,0, Tile.TILE_WIDTH/2, Tile.TILE_WIDTH};
}
