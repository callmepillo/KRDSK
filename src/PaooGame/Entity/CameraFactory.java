package PaooGame.Entity;

public class CameraFactory extends EntityFactory{
    public Entity createEntity(int x, int y) {
        return new Camera(x, y);
    }
}
