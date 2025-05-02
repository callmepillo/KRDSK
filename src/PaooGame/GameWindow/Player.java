package PaooGame.GameWindow;

import PaooGame.Game;
import PaooGame.Graphics.Assets;
import PaooGame.Tiles.Tile;


import java.awt.*;

public class Player {
    private Tile playerSprite;
    private int posX;
    private int posY;
    private int velX;
    private int velY;
    private int speed = 3; //changed to int
    private boolean freezed = false;
    private boolean onGround = false;
    private int gravity = 1;
    private final int jumpStrength = -19;
    private boolean jumpPressed = false;
    private boolean moving = false;
    private boolean left, up, right, down;
    private boolean jump;
    private int airSpeed = 0;
    private int jumpSpeed = -20;
    private int fallSpeedAfterCollision = 1;
    private boolean inAir = false;


    public Player(int x, int y) {
        posX = x;
        posY = y;
        velX = 0;
        velY = 0;
        playerSprite=new Tile(Assets.player_idle, 6);
    }

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
            if(!CollisionChecker.IsEntityOnFloor(getRectangle(), roomX, roomY, roomMap)) {
                inAir = true;
            }
        }

        if(inAir) {
            if(CollisionChecker.CanMoveHere(getRectangle(), roomX, roomY, posX, posY + airSpeed, roomMap)) {
                posY += airSpeed;
                airSpeed += gravity;
                updateXpos(xSpeed, roomX, roomY, roomMap);
            } else {
                posY = CollisionChecker.GetEntityYPosUnderRoofOrAboveFloor(getRectangle(), roomY, airSpeed);
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


//        if(!onGround) {
//            velY += gravity;
//        }
//
    //     int newX = posX + velX * speed;
//        //miscarea pe orizontala
//        if (CollisionChecker.CanMoveHere(getRectangle(), roomX, roomY, newX, posY, roomMap) && !freezed) {
//            posX = newX;
//        }
//
  //         int newY = posY + velY * speed;
//
//        if(CollisionChecker.CanMoveHere(getRectangle(), roomX, roomY, posX, newY, roomMap)&&!freezed){
//
//            posY=newY;
//        }
//
//        if(velY>=0 && newY>=roomY+FauxWindow.height-Tile.TILE_HEIGHT)
//        {
//            posY=roomY+FauxWindow.height-Tile.TILE_HEIGHT;
//            velY=0;
//            onGround=true;
//        }
//        else{
//            if(velY>0){
//                onGround=false;
//            }
//        }
//        if (velX > 0) {
//            --velX;
//        } else if (velX < 0) {
//            ++velX;
//        }
    }

    private void updateXpos(int xSpeed, int roomX, int roomY, int[][] roomMap) {
        if (CollisionChecker.CanMoveHere(getRectangle(), roomX, roomY, posX + xSpeed, posY, roomMap)) {
            posX += xSpeed;
        } else {
            posX = CollisionChecker.GetEntityXPosNextToWall(getRectangle(), roomX, xSpeed);
        }
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    public void jump(){
        if(inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    public void releaseJump(){
        jumpPressed =false;
        if(velY<-5) {
            velY = -5;
        }
    }

    public void Draw(Graphics2D g) {
        Stroke orgStroke = g.getStroke();
        Color orgColor = g.getColor();

        playerSprite = new Tile(Assets.player_idle, 6);

        //drawing the hitbox for debugging purposes
        g.setColor(Color.RED);
        g.setStroke(new java.awt.BasicStroke(3));
        g.drawRect(getRectangle().x, getRectangle().y, getRectangle().width, getRectangle().height);

        playerSprite.Draw(g, posX - 5, posY); //so its centered on the hitbox

        g.setStroke(orgStroke);
        g.setColor(orgColor);
    }

    public void moveRight() {
        velX = 5;
    }

    public void moveLeft() {
        velX = -5;
    }

    public void stopMoving()
    {
        velX=0;
        //velY=0;
    }


    public void moveDown() {
        velY = 5;
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    public void stopVerticalMoving() {
        velY = 0;
    }


    /* public void jumpPressed() {
        velY -= 5;
    }
*/
    public void setXY(int x, int y) {
        posX = x;
        posY = y;
    }

    public Rectangle getRectangle() {
        //i wanted to make the hitbox a little smaller
        return new Rectangle(posX, posY, Tile.TILE_WIDTH - 10, Tile.TILE_HEIGHT);
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
    public boolean isJump() {
        return jump;
    }
    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void resetDirBooleans() {
        left=false;
        right=false;
        up=false;
        down=false;

    }

}

