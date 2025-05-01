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
    private float speed = 5.0f;
    private boolean freezed = false;
    private boolean onGround = false;
    private float gravity=0.04f;
    private final int jumpStrength=-19;
    private boolean jumpPressed =false;
    private boolean moving =false;
    private boolean left, up, right, down;
    private int roomX;
    private int roomY;

    private boolean jump;
    private float airSpeed=0f;
    private float jumpSpeed=-2.25f;
    private float fallSpeedAfterCollision=0.5f;
    private boolean inAir=false;


    public Player(int x, int y) {
        posX = x;
        posY = y;
        velX = 0;
        velY = 0;
        playerSprite=new Tile(Assets.player_idle, 6);
    }

    public void Update(int roomX, int roomY, int[][] roomMap) {
//
        moving = false;
        if(!left && !right && !inAir)
            return;

        float xSpeed=0;
        if(left) {
            xSpeed-=speed;
        }
        if(right) {
            xSpeed+=speed;
        }

        if(inAir) {

        }else {
            updateXpos(xSpeed);
        }



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

    private void updateXpos(float xSpeed) {
        if (CollisionChecker.CanMoveHere(hitbox.x+xSpeed, hitbox.y) {
           posX += xSpeed;
       }else {
            posX=GetEntityXPosNextToWall(posX, xSpeed);
        }
    }

    public void jump(){
        if(onGround && !jumpPressed){
            velY=jumpStrength;
            onGround=false;
            jumpPressed =true;
        }
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
        g.drawRect(posX, posY, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);

        playerSprite.Draw(g, posX, posY);

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
        return new Rectangle(posX, posY, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
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

}

