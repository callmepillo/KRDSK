package PaooGame.Levels;

import PaooGame.Entity.Camera;
import PaooGame.Entity.Entity;
import PaooGame.Tiles.Tile;

public class LevelThree extends Level{
    public LevelThree() {
        this.tileMap = new int[][][] {
                //camera 0 Level 3
                {
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 65, 0, 0, 65, 0, 0},
                        {0, 0, 0, 65, 0, 0, 0, 0, 0, 0},
                        {0, 0, 63, 0, 0, 0, 65, 65, 0, 0},
                        {0, 65, 64, 0, 65, 66, 65, 65, 0, 0},
                },

                //camera 1 Level 3

                {
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 65, 65, 65, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 65, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 85, 87, 0, 65},
                        {0, 0, 65, 0, 61, 65, 86, 88, 0, 65},
                        {0, 0, 65, 0, 62, 65, 65, 65, 0, 0},

                },
                //camera 2 Level 3
                {
                        {0, 0, 0, 65, 0, 0, 0, 0, 0, 0},
                        {0, 0, 65, 0, 0, 0, 0, 0, 0, 0},
                        {0, 65, 0, 0, 0, 0, 65, 65, 0, 0},
                        {0, 0, 0, 61, 65, 65, 65, 65, 0, 0},
                        {0, 0, 0, 62, 0, 0, 0, 0, 0, 0},
                        {0, 0, 65, 65, 0, 0, 0, 0, 71, 72},


                },
                //camera 3 Level 3

                {
                        {0, 0, 0, 0, 65, 65, 65, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 65, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 65, 0, 0, 0, 0, 0, 0},
                        {54, 0, 65, 65, 54, 61, 0, 0, 0, 0},
                        {55, 65, 0, 65, 55, 62, 0, 65, 0, 0},

                },
                //camera 4 Level 3
                {
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 65, 0, 65, 0, 65, 65, 65, 0},
                        {65, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 65, 85, 87, 85, 87, 0, 0},
                        {0, 0, 65, 65, 86, 88, 86, 88, 0, 0},
                },
                //camera 5 - Level 3
                {
                        {0, 0, 0, 0, 0, 0, 0, 65, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 65, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 65, 65, 0, 0, 0, 0, 0},
                        {0, 0, 65, 0, 65, 65, 61, 65, 0, 0},
                        {0, 0, 65, 65, 65, 65, 62, 65, 65, 0},
                },
                //camera 6 - Level 3
                {
                        {0, 0, 0, 0, 65, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 65, 0, 65, 0, 0},
                        {0, 65, 0, 0, 0, 0, 0, 0, 0, 65},
                        {0, 65, 65, 65, 61, 0, 0, 0, 0, 0},
                        {0, 65, 65, 65, 62, 0, 73, 74, 0, 0},
                },
                //camera 7 - Level 3 - FINALA
                {
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 61, 61, 0, 0, 61, 61, 0, 0},
                        {0, 0, 62, 62, 73, 74, 62, 62, 0, 0},

                }


        };


        this.doors = new Door[][]{
//                {new Door(0, 1, 10* Tile.TILE_WIDTH - 10, 2*Tile.TILE_HEIGHT, 10, 2*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT)},
//                {new Door(1, 0, 0, 4*Tile.TILE_HEIGHT, 7*Tile.TILE_WIDTH - 10, 4*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT),
//                        new Door(1, 2, 10*Tile.TILE_WIDTH - 10, 2*Tile.TILE_HEIGHT, 10, 2*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT)},
//                {new Door(2, 1, 0, 4*Tile.TILE_HEIGHT, 7*Tile.TILE_WIDTH - 10, 4*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT),
//                        new Door(2,3, 10*Tile.TILE_WIDTH-10, 3*Tile.TILE_HEIGHT, 10, 1*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT )},
//                {new Door(3, 2, 0, 1* Tile.TILE_HEIGHT, 6*Tile.TILE_WIDTH-10, 5*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT),
//                        new Door(3,4,10*Tile.TILE_WIDTH-10, 5*Tile.TILE_HEIGHT, 10, 4*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT)},
//                {new Door(4, 3, 0, 4* Tile.TILE_HEIGHT, 6*Tile.TILE_WIDTH-10, 1*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT),
                {new Door(0, 1, 8 * Tile.TILE_WIDTH, 6 * Tile.TILE_HEIGHT - 10, 1 * Tile.TILE_WIDTH, 10, Tile.TILE_WIDTH, 10)},
                {new Door(1, 0, 9 * Tile.TILE_WIDTH - 10, 0, 10, 4 * Tile.TILE_HEIGHT, Tile.TILE_WIDTH, 10),
                        new Door(1, 2, 10 * Tile.TILE_WIDTH - 10, 2 * Tile.TILE_HEIGHT, 10, 2 * Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT)},
                {new Door(2, 1, 0, 4 * Tile.TILE_HEIGHT, 7 * Tile.TILE_WIDTH - 10, 4 * Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT),
                        new Door(2, 3, 10 * Tile.TILE_WIDTH - 10, 3 * Tile.TILE_HEIGHT, 10, 1 * Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT)},
                {new Door(3, 2, 0, 1 * Tile.TILE_HEIGHT, 6 * Tile.TILE_WIDTH - 10, 5 * Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT),
                        new Door(3, 4, 10 * Tile.TILE_WIDTH - 10, 5 * Tile.TILE_HEIGHT, 10, 4 * Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT)},
                {new Door(4, 3, 0, 4 * Tile.TILE_HEIGHT, 6 * Tile.TILE_WIDTH - 10, 1 * Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT)

                }
        };
        this.entity = new Entity[][]{
                {},
                {guardFactory.createEntity(4 * Tile.TILE_WIDTH, 4 * Tile.TILE_HEIGHT - 1), cameraFactory.createEntity(9 * Tile.TILE_WIDTH + (3 * Tile.TILE_WIDTH) / 4 - 1, 0), cameraFactory.createEntity(0, 0)}
        };

        //for the camera, because of the constructor and because we may want to make it move faster/with a different
        //angle we need to adjust these parameters manually using hte setAnim method which takes the director the camera
        //is pointing at, the angle of rotation and the time.
        //notice that we need to reference the camera directly from the entity vector and cast it to a camera object
        ((Camera) entity[1][1]).setAnim(true, 45, 100);
        ((Camera) entity[1][2]).setAnim(false, -45, 100);
        //{new Door(2, 3, 10*Tile.TILE_WIDTH - 10, 4*Tile.TILE_HEIGHT, 10, 4*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT)},
        //{new Door(3, 2, 0, 4*Tile.TILE_HEIGHT, 7*Tile.TILE_WIDTH - 10, 4*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT)}


    }
}
