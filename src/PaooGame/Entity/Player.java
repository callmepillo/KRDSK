package PaooGame.Entity;

import PaooGame.GameWindow.CollisionChecker;
import PaooGame.Graphics.Assets;
import PaooGame.Tiles.Tile;


import java.awt.*;

public class Player extends Entity {
    private int gravity = 1;
    private boolean jump;
    private int airSpeed = 0;
    private int jumpSpeed = -20;
    private int fallSpeedAfterCollision = 1;
    private boolean inAir = false;

    public Player(int x, int y) {
        super(x, y, Tile.TILE_WIDTH - 10, Tile.TILE_HEIGHT/2);
        entitySprite = new Tile(Assets.player_idle, 6);
    }

    @Override
    public void Update(int roomX, int roomY, int[][] roomMap) {
        moving = false;

        if(jump)
            jump();

        if(!left && !right && !inAir)
            return;

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

        entitySprite = new Tile(Assets.player_idle, 6);

        //drawing the hitbox for debugging purposes
        g.setColor(Color.RED);
        g.setStroke(new java.awt.BasicStroke(3));
        g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

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
}

