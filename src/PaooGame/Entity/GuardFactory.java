package PaooGame.Entity;

public class GuardFactory extends EntityFactory{
    public Entity createEntity(int x, int y) {
        return new Guard(x, y);
    }
}