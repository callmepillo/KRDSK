package PaooGame.Entity;

import PaooGame.GameWindow.CollisionChecker;
import PaooGame.Graphics.Assets;
import PaooGame.Tiles.Tile;


import java.awt.*;

//player tile ids:
//idle = 1
//jump = 2
//move_right = 3-6
//move_left = 7-10

public class Player extends Entity {
    private int gravity = 1;
    private boolean jump;
    private int airSpeed = 0;
    private int jumpSpeed = -20;
    private int fallSpeedAfterCollision = 1;
    private boolean inAir = false;
    protected Tile animJump;

    //Utilizam design pattern-ul singleton deoarece vom folosi doar un jucator
    private static Player instance;

    public static Player getInstance() {
        if (instance == null)
            instance = new Player(0, 0);
        return instance;
    }

    private Player(int x, int y) {
        super(x, y, Tile.TILE_WIDTH - 10, Tile.TILE_HEIGHT/2);
        animIdle = new Tile(Assets.player_idle, 1);
        animJump = new Tile(Assets.player_jump, 2);
        animMoveRight = new Tile[4];
        animMoveLeft = new Tile[4];
        for(int i = 0; i < 4; ++i) {
            animMoveRight[i] = new Tile(Assets.player_move_right[i], 3 + i);
            animMoveLeft[i] = new Tile(Assets.player_move_left[i], 7 + i);
        }
        entitySprite = animIdle;

    }

    @Override
    public void Update(int roomX, int roomY, int[][] roomMap) {
        moving = false;

        if(jump) {
            jump();
        }

        if(!left && !right && !inAir) {
            return;
        }

        int xSpeed = 0;

        if(left) {
            xSpeed -= speed;
        }
        if(right) {
            xSpeed += speed;
        }

        if(!inAir) {
            if(!CollisionChecker.IsEntityOnFloor(hitbox, roomX, roomY, roomMap)) {
                inAir = true;
            }
        }

        if(inAir) {
            if(CollisionChecker.CanMoveHere(hitbox, roomX, roomY, posX, posY + airSpeed, roomMap)) {
                posY += airSpeed;
                airSpeed += gravity;
                updateXpos(xSpeed, roomX, roomY, roomMap);
            } else {
                int tempY = CollisionChecker.GetEntityYPosUnderRoofOrAboveFloor(hitbox, roomX, roomY, airSpeed, roomMap);
                if(CollisionChecker.CanMoveHere(hitbox, roomX, roomY, posX, tempY, roomMap))
                    posY = tempY;
                if(airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXpos(xSpeed, roomX, roomY, roomMap);
            }
        } else {
            updateXpos(xSpeed, roomX, roomY, roomMap);
        }
        moving = true;
        updateHitboxPos();
    }

    private void updateXpos(int xSpeed, int roomX, int roomY, int[][] roomMap) {
        if (CollisionChecker.CanMoveHere(hitbox, roomX, roomY, posX + xSpeed, posY, roomMap)) {
            posX += xSpeed;
        } else {
            posX = CollisionChecker.GetEntityXPosNextToWall(hitbox, roomX, xSpeed);
        }
    }

    public void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    public void setInAir(boolean inAir) {
        this.inAir = inAir;
    }

    public void jump(){
        if(inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    public void Draw(Graphics2D g) {
        Stroke orgStroke = g.getStroke();
        Color orgColor = g.getColor();

        //drawing the hitbox for debugging purposes
        //g.setColor(Color.RED);
        //g.setStroke(new java.awt.BasicStroke(3));
        //g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

        updateSprite();
        entitySprite.Draw(g, posX - 5, posY - Tile.TILE_HEIGHT/2); //so its centered on the hitbox

        g.setStroke(orgStroke);
        g.setColor(orgColor);
    }

    public boolean isJump() {
        return jump;
    }
    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void reset(int x, int y) {
        this.posX = x;
        this.posY = y;
        this.airSpeed = 0;
        this.inAir = true; // Assuming player falls from air at spawn
        this.jump = false;
        this.moving = false;
        this.left = false;
        this.right = false;
        updateHitboxPos();
    }

    @Override
    public void updateSprite() {
        if(inAir) {
            entitySprite = animJump;
            return;
        }
        super.updateSprite();
    }
}

