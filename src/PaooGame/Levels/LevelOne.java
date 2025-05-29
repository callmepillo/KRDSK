package PaooGame.Levels;

import PaooGame.Database.DatabaseManager;
import PaooGame.Entity.*;
import PaooGame.Tiles.Tile;

public class LevelOne extends Level{
    public LevelOne() {
//        this.tileMap = new int[][][] {
//                    //camera 0
//                {
//                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 65},
//                        {0, 61, 0, 0, 0, 0, 65, 0, 65, 65},
//                        {0, 62, 0, 66, 65, 65, 65, 65, 65, 65}
//                },
//
//                //camera 1
//
//                {
//                        {0, 0, 65, 0, 0, 0, 0, 0, 0, 0},
//                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                        {65, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                        {0, 0, 0, 63, 0, 0, 0, 0, 0, 0},
//                        {65, 0, 65, 64, 0, 73, 74, 56, 65, 0}
//
//                },
//                //camera 2 Level 1
//                {
//                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                        {0, 0, 65, 0, 0, 0, 0, 0, 0, 0},
//                        {65, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                        {0, 0, 0, 0, 65, 0, 0, 0, 0, 0},
//                        {0, 65, 0, 39, 65, 0, 0, 63, 0, 0},
//                        {65, 65, 65, 40, 65, 0, 65, 62, 65, 0}
//
//
//                },
//                //camera 3 Level 1 (ultima)
//
//                {
//                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                        {0, 0, 0, 0, 61, 0, 0, 0, 0, 0},
//                        {0, 0, 65, 0, 62, 42, 43, 44, 65, 0}
//
//                }
//
//        };
        this.tileMap = DatabaseManager.getLevel(1);
        this.levelId = 0;

        //here we create the doors using the Door constructor which uses the following parameters, in order:
        //org - the room where the door is placed
        //dest - the room where the door takes us to
        //x - the X position within the room where the door is placed (relative to the room left border)
        //  - notice we multiply Tile.TILE_WIDTH by 10, so we are placed outside the 10 tile length room,
        //  - but we subtract 10 (the general width of the door) so the door is placed right next to the wall
        //y - the Y position within the room where the door is placed (relative to the ceiling)
        //destX - the position of the player within the destination room when the player uses the door (relative to the left border)
        //destY - same as above, but for the y position (relative to the ceiling)
        //width - the width of the door (in general, should be 10)
        //height - the height of the door (in general, one tile height)
        this.doors = new Door[][] {
                {new Door(0, 1, 10*Tile.TILE_WIDTH - 10, 2*Tile.TILE_HEIGHT, 10, 2*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT)},
                {new Door(1, 0, 0, 2*Tile.TILE_HEIGHT, 7*Tile.TILE_WIDTH - 10, 2*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT),
                new Door(1, 2, 10*Tile.TILE_WIDTH - 10, 4*Tile.TILE_HEIGHT, 10, 4*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT)},
                {new Door(2, 1, 0, 4*Tile.TILE_HEIGHT, 7*Tile.TILE_WIDTH - 10, 4*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT),
               new Door(2,3, 10*Tile.TILE_WIDTH-10, 4*Tile.TILE_HEIGHT, 10, 4*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT )},
                {new Door(3, 2, 0, 2* Tile.TILE_HEIGHT, 7*Tile.TILE_WIDTH-10, 2*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT)},
                //{new Door(2, 3, 10*Tile.TILE_WIDTH - 10, 4*Tile.TILE_HEIGHT, 10, 4*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT)},
                //{new Door(3, 2, 0, 4*Tile.TILE_HEIGHT, 7*Tile.TILE_WIDTH - 10, 4*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT)}
        };

        //so, the way we create an entity: we either a camera or guard factory to create an entity that is saved
        //in the entity array. the createEntity method takes only 2 arguments, the x position and y position
        //relative to the room they are positioned in (so everything is calculated from the left border of the room
        //and the ceiling
        this.entity = new Entity[][] {
                {},
                {guardFactory.createEntity(4*Tile.TILE_WIDTH, 4*Tile.TILE_HEIGHT - 1), cameraFactory.createEntity(9*Tile.TILE_WIDTH + (3*Tile.TILE_WIDTH)/4 - 1, 0), cameraFactory.createEntity(0, 0)},
                {},
                {new Ambassador(3*Tile.TILE_WIDTH, 4*Tile.TILE_HEIGHT - 1, false)}
        };

        //for the camera, because of the constructor and because we may want to make it move faster/with a different
        //angle we need to adjust these parameters manually using hte setAnim method which takes the director the camera
        //is pointing at, the angle of rotation and the time.
        //notice that we need to reference the camera directly from the entity vector and cast it to a camera object
        ((Camera) entity[1][1]).setAnim(true, 45, 100);
        ((Camera) entity[1][2]).setAnim(false, -45, 100);
    }
}
