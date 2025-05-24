package PaooGame.Entity;

import PaooGame.GameWindow.CollisionChecker;
import PaooGame.Graphics.Assets;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class Entity {
    protected Tile entitySprite;
    protected int posX;
    protected int posY;
    protected Rectangle hitbox;
    protected boolean left, up, right, down;
    protected int speed = 4; //changed to int
    protected boolean moving = false;
    protected int moveBuffer = 0;
    protected Queue<Directions> movementQueue = new LinkedList<>();
    protected Queue<Integer> timingQueue = new LinkedList<>();

    protected Entity(int x, int y, int width, int height) {
        this.posX = x;
        this.posY = y;
        this.hitbox = new Rectangle(x, y, width, height);
    }

    public void updateHitboxPos() {
        hitbox.setLocation(posX, posY);
    }

    public boolean isLeft() {
        return left;
    }
    public void setLeft(boolean left) {
        this.left = left;
    }
    public boolean isRight() {
        return right;
    }
    public void setRight(boolean right) {
        this.right = right;
    }
    public boolean isUp() {
        return up;
    }
    public void setUp(boolean up) {
        this.up = up;
    }
    public boolean isDown() {
        return down;
    }
    public void setDown(boolean down) {
        this.down = down;
    }

    public void resetDirBooleans() {
        left=false;
        right=false;
        up=false;
        down=false;
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    public void setXY(int x, int y) {
        posX = x;
        posY = y;
    }

    public void Update(int roomX, int roomY, int[][] roomMap) {
        moving = false;

        if(moveBuffer != 0)
            moveBuffer--;
        else if(!movementQueue.isEmpty()) {
            resetDirBooleans();
            switch(movementQueue.remove()) {
                case LEFT:
                    left = true;
                    break;
                case RIGHT:
                    right = true;
                    break;
                case UP:
                    up = true;
                    break;
                case DOWN:
                    down = true;
                    break;
            }
            moveBuffer = timingQueue.remove();
        }

        if(!left && !right)
            return;

        int xSpeed = 0;

        if(left) {
            xSpeed -= speed;
        }
        if(right) {
            xSpeed += speed;
        }

        posX += xSpeed;
        updateHitboxPos();
    }

    public void Draw(Graphics2D g, int roomX, int roomY) {
        Stroke orgStroke = g.getStroke();
        Color orgColor = g.getColor();

        //entitySprite = new Tile(Assets.player_idle, 6);

        //drawing the hitbox for debugging purposes
        g.setColor(Color.RED);
        g.setStroke(new java.awt.BasicStroke(3));
        g.fillRect(hitbox.x + roomX, hitbox.y + roomY, hitbox.width, hitbox.height);
        //entitySprite.Draw(g, posX - 5, posY - Tile.TILE_HEIGHT/2); //so its centered on the hitbox

        g.setStroke(orgStroke);
        g.setColor(orgColor);
    }

    public Rectangle getRectangle() {
        //i wanted to make the hitbox a little smaller
        return hitbox;
    }

    public void addMove(Directions dir, int time) {
        movementQueue.add(dir);
        timingQueue.add(time);
    }
}
