package PaooGame.GameWindow;

import PaooGame.Graphics.Assets;
import PaooGame.Tiles.Tile;


import java.awt.*;

public class Player {
    private Tile playerSprite;
    private int posX;
    private int posY;
    private int velX;
    private int velY;
    private int speed = 1;
    private boolean freezed = false;
    private boolean onGround = false;
    private final int gravity=1;
    private final int jumpStrength=-19;
    private boolean jumpPressed =false;

    public Player(int x, int y) {
        posX = x;
        posY = y;
        velX = 0;
        velY = 0;
        playerSprite=new Tile(Assets.player_idle, 6);
    }

    public void Update(int roomX, int roomY, int[][] roomMap) {

        if(!onGround) {
            velY += gravity;
        }

        int newX = posX + velX * speed;
        //miscarea pe orizontala
        if (CollisionChecker.CanMoveHere(getRectangle(), roomX, roomY, newX, posY, roomMap) && !freezed) {
            posX = newX;
        }

        int newY = posY + velY * speed;

        if(CollisionChecker.CanMoveHere(getRectangle(), roomX, roomY, posX, newY, roomMap)&&!freezed){

            posY=newY;
        }

        if(velY>=0 && newY>=roomY+FauxWindow.height-Tile.TILE_HEIGHT)
        {
            posY=roomY+FauxWindow.height-Tile.TILE_HEIGHT;
            velY=0;
            onGround=true;
        }
        else{
            if(velY>0){
                onGround=false;
            }
        }
        if (velX > 0) {
            --velX;
        } else if (velX < 0) {
            ++velX;
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
}

