package PaooGame.Levels;
import PaooGame.Database.DatabaseManager;
import PaooGame.Entity.Ambassador;
import PaooGame.Entity.Camera;
import PaooGame.Entity.Entity;
import PaooGame.Tiles.Tile;

public class LevelTwo extends Level{
    public LevelTwo() {
        this.tileMap = DatabaseManager.getLevel(2);
        this.levelId = 1;
      this.doors = new Door[][] {
                {new Door(0, 1, 10* Tile.TILE_WIDTH - 10, 2*Tile.TILE_HEIGHT, 10, 2*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT)},
                {new Door(1, 0, 0, 4*Tile.TILE_HEIGHT, 7*Tile.TILE_WIDTH - 10, 4*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT),
                        new Door(1, 2, 10*Tile.TILE_WIDTH - 10, 2*Tile.TILE_HEIGHT, 10, 2*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT)},
                {new Door(2, 1, 0, 4*Tile.TILE_HEIGHT, 7*Tile.TILE_WIDTH - 10, 4*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT),
                        new Door(2,3, 10*Tile.TILE_WIDTH-10, 3*Tile.TILE_HEIGHT, 10, 1*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT )},
                {new Door(3, 2, 0, 1* Tile.TILE_HEIGHT, 6*Tile.TILE_WIDTH-10, 5*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT),
                        new Door(3,4,10*Tile.TILE_WIDTH-10, 5*Tile.TILE_HEIGHT, 10, 4*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT)},
                {new Door(4, 3, 0, 4* Tile.TILE_HEIGHT, 6*Tile.TILE_WIDTH-10, 1*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT),
                        new Door(4, 5, 7*Tile.TILE_WIDTH, 6*Tile.TILE_HEIGHT-10, 1*Tile.TILE_WIDTH, 10, Tile.TILE_WIDTH, 10 )},
                {new Door(5, 4, 10*Tile.TILE_WIDTH-10, 5*Tile.TILE_HEIGHT, 10, 4*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT )}


                                //{new Door(2, 3, 10*Tile.TILE_WIDTH - 10, 4*Tile.TILE_HEIGHT, 10, 4*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT)},
                //{new Door(3, 2, 0, 4*Tile.TILE_HEIGHT, 7*Tile.TILE_WIDTH - 10, 4*Tile.TILE_HEIGHT, 10, Tile.TILE_HEIGHT)}
        };

        //so, the way we create an entity: we either a camera or guard factory to create an entity that is saved
        //in the entity array. the createEntity method takes only 2 arguments, the x position and y position
        //relative to the room they are positioned in (so everything is calculated from the left border of the room
        //and the ceiling
        this.entity = new Entity[][] {
                {}, //0
                {       guardFactory.createEntity(4 * Tile.TILE_WIDTH, 4 * Tile.TILE_HEIGHT - 1),
                        cameraFactory.createEntity(9 * Tile.TILE_WIDTH + (3 * Tile.TILE_WIDTH) / 4 - 1, 0),
                        cameraFactory.createEntity(0, 0)},

                {       guardFactory.createEntity(4 * Tile.TILE_WIDTH, 4 * Tile.TILE_HEIGHT - 1),
                        cameraFactory.createEntity(9 * Tile.TILE_WIDTH + (3 * Tile.TILE_WIDTH) / 4 - 1, 0),
                        cameraFactory.createEntity(0, 0)},

                {       //guardFactory.createEntity(4 * Tile.TILE_WIDTH, 4 * Tile.TILE_HEIGHT - 1),
                       cameraFactory.createEntity(9 * Tile.TILE_WIDTH + (3 * Tile.TILE_WIDTH) / 4 - 1, 0),
                        cameraFactory.createEntity(0, 0)
                        }, // 3
                {cameraFactory.createEntity(9 * Tile.TILE_WIDTH + (3 * Tile.TILE_WIDTH) / 4 - 1, 0),
                        cameraFactory.createEntity(0, 0)},
                {
                        new Ambassador(7 * Tile.TILE_WIDTH, 4 * Tile.TILE_HEIGHT - 1, false)
                }
        };


        //for the camera, because of the constructor and because we may want to make it move faster/with a different
        //angle we need to adjust these parameters manually using hte setAnim method which takes the director the camera
        //is pointing at, the angle of rotation and the time.
        //notice that we need to reference the camera directly from the entity vector and cast it to a camera object
        ((Camera) entity[1][1]).setAnim(true, 45, 100);
        ((Camera) entity[1][2]).setAnim(false, -45, 100);
        ((Camera) entity[2][1]).setAnim(true, 45, 100);
        ((Camera) entity[2][2]).setAnim(false, -45, 100);
        ((Camera) entity[3][0]).setAnim(true, 45, 100);
        ((Camera) entity[3][1]).setAnim(false, -45, 100);
        ((Camera) entity[4][0]).setAnim(true, 45, 100);
        ((Camera) entity[4][1]).setAnim(false, -45, 100);


    }
}
