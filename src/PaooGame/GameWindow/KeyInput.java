package PaooGame.GameWindow;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


    public class KeyInput implements KeyListener {
        private Player player;

        public KeyInput(Player player){
            this.player=player;
        }

    @Override
    public void keyPressed(KeyEvent e){
        int key=e.getKeyCode();

        if(key==KeyEvent.VK_RIGHT){
            player.moveRight();
        }
        if(key==KeyEvent.VK_LEFT){
            player.moveLeft();

        }
        if(key== KeyEvent.VK_SPACE){
            player.jump();
        }
    }
    @Override
    public void keyReleased(KeyEvent e)
    {
        int key =e.getKeyCode();

        if(key==KeyEvent.VK_RIGHT || key== KeyEvent.VK_LEFT)
        {
            player.stopMoving();
        }
        if(key==KeyEvent.VK_SPACE){
            player.releaseJump();
        }
    }
    @Override
    public void keyTyped(KeyEvent e){}
}
