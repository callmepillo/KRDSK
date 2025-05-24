package PaooGame.Levels;

import PaooGame.Entity.Camera;
import PaooGame.Entity.Entity;
import PaooGame.Entity.Guard;
import PaooGame.Entity.GuardFactory;
import PaooGame.Tiles.Tile;

public class LevelOne extends Level{
    public LevelOne() {
        this.tileMap = new int[][][] {
                    //camera 0
                {
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 65},
                        {0, 61, 0, 0, 0, 0, 65, 0, 65, 65},
                        {0, 62, 0, 66, 65, 65, 65, 65, 65, 65}
                },

                //camera 1

                {
                        {0, 0, 65, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {65, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 63, 0, 0, 0, 0, 0, 0},
                        {65, 0, 65, 64, 0, 73, 74, 56, 65, 0}

                },
                //camera 2 Level 1
                {
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 65, 0, 0, 0, 0, 0, 0, 0},
                        {65, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 65, 0, 0, 0, 0, 0},
                        {0, 65, 0, 39, 65, 0, 0, 63, 0, 0},
                        {65, 65, 65, 40, 65, 0, 65, 62, 65, 0}


                },
                //camera 3 Level 1 (ultima)

                {
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 61, 0, 0, 0, 0, 0},
                        {0, 0, 65, 0, 62, 42, 43, 44, 65, 0}

                }

        };

        this.doors = new Door[][] {
                {new Door(0, 1, 10*Tile.TILE_WIDTH - 10, 2*Tile.TILE_HEIGHT, 10, 2*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT)},
                {new Door(1, 0, 0, 2*Tile.TILE_HEIGHT, 7*Tile.TILE_WIDTH - 10, 2*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT)}
        };

        this.entity = new Entity[][] {
                {},
                {guardFactory.createEntity(4*Tile.TILE_WIDTH, 4*Tile.TILE_HEIGHT - 1), cameraFactory.createEntity(9*Tile.TILE_WIDTH + (3*Tile.TILE_WIDTH)/4 - 1, 0)}
        };
        ((Camera) entity[1][1]).setAnim(true, 45, 100);
    }
}
