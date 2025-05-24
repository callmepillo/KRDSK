package PaooGame.Input;

//the game window which we want to control
import PaooGame.GameWindow.GameWindow;
import PaooGame.InputFacade;

//we use the MouseInputAdapter from Swing and MouseEvent from AWT to handle the mouse
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;

//we use the KeyAdapter/KeyEvent from AWT
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputController {
    private InputFacade fac;
    private int[][] distortX; //we use the disotrsion maps in order to correctly
    private int[][] distortY; //handle mouse events
    public PlayerControl pControl;
    public MouseMotionControls mMControl;
    public MousePressedControls mPControl;
    public MenuControl kControl;

    /*this implementation makes an InputController specialized (it only modifies a certain
    GameWindow, not all of them; if we would have multiple GameWindows we would need multiple
    InputControllers */
    public InputController(GameWindow Window, int[][] distortX, int[][] distortY) {
        this.fac = new InputFacade(Window);
        this.distortX = distortX;
        this.distortY = distortY;
        pControl = new PlayerControl();
        mMControl = new MouseMotionControls();
        mPControl = new MousePressedControls();
        kControl = new MenuControl();
    }


    //player control (with arrow keys) and primitive boundry check
    public class PlayerControl extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent event) {
            boolean optWASD = fac.optGetWasd();
            boolean optSPACE = fac.optGetSpace();

            if(optWASD) {
                switch (event.getKeyCode()) {
                    case KeyEvent.VK_W:
                        if(!optSPACE)
                            fac.playerJump(true);
                        break;
                    case KeyEvent.VK_A:
                        fac.playerLeft(true);
                        break;
                    case KeyEvent.VK_S:
                        fac.playerDown(true);
                        break;
                    case KeyEvent.VK_D:
                        fac.playerRight(true);
                        break;
                }
            }
            else {
                switch (event.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if(!optSPACE)
                            fac.playerJump(true);
                        break;
                    case KeyEvent.VK_LEFT:
                        fac.playerLeft(true);
                        break;
                    case KeyEvent.VK_DOWN:
                        fac.playerDown(true);
                        break;
                    case KeyEvent.VK_RIGHT:
                        fac.playerRight(true);
                        break;
                }
            }

            if(event.getKeyCode() == KeyEvent.VK_ESCAPE)
                fac.winDisplayPauseMenu();

            if(optSPACE && (event.getKeyCode() == KeyEvent.VK_SPACE))
                fac.playerJump(true);
           if (Character.isDigit(event.getKeyChar())) {
                int number = Integer.parseInt(Character.toString(event.getKeyChar()));
                fac.barToggleRoom(number);
           }
        }

        public void keyReleased(KeyEvent event)
        {
            boolean optWASD = fac.optGetWasd();
            boolean optSPACE = fac.optGetSpace();

            if(optWASD) {
                switch (event.getKeyCode()) {
                    case KeyEvent.VK_W:
                        if(!optSPACE)
                            fac.playerJump(false);
                        break;
                    case KeyEvent.VK_A:
                        fac.playerLeft(false);
                        break;
                    case KeyEvent.VK_S:
                        fac.playerDown(false);
                        break;
                    case KeyEvent.VK_D:
                        fac.playerRight(false);
                        break;
                }
            }
            else {
                switch (event.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if(!optSPACE)
                            fac.playerJump(false);
                        break;
                    case KeyEvent.VK_LEFT:
                        fac.playerLeft(false);
                        break;
                    case KeyEvent.VK_DOWN:
                        fac.playerDown(false);
                        break;
                    case KeyEvent.VK_RIGHT:
                        fac.playerRight(false);
                        break;
                }
            }

            if(optSPACE && (event.getKeyCode() == KeyEvent.VK_SPACE))
                fac.playerJump(false);
        }

    }

    //mouse coordinate handler
    public class MouseMotionControls extends MouseInputAdapter {
        public void mouseMoved(MouseEvent e) {
            fac.winSetMouse(distortX[e.getY()][e.getX()], distortY[e.getY()][e.getX()]);
            /*We see that we are giving the distorted coordinates as arguments to the
            * SetMouse function in order to correctly the map the real screen coordinates
            * to the distored screen ones. This is necessary because when we are drawing
            * objects to the BufferedImage in Game.java, we use the normal dimensions, so
            * an objects pixels correspond to the real ones, but after that we distort the
            * whole image, so the objects pixels wont corespond to the real X,Y space. So,
            * if we dont pass the distortX and distortY, we have objects in a distorted space
            * and a mouse in a real space.*/
        }
        public void mouseDragged(MouseEvent e) {
            fac.winSetMouse(distortX[e.getY()][e.getX()], distortY[e.getY()][e.getX()]);
        }
    }

    public class MousePressedControls extends MouseInputAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            fac.winSetMousePressed(true);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            fac.winSetMousePressed(false);
        }
    }

    //keyboard input handler for the CliWindow
    public class MenuControl extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent event) {
            char key = event.getKeyChar();
            int keyCode = event.getKeyCode();

            //we are first checking the backspace character, that should delete a character (only it there are any to delete)
            if(keyCode == KeyEvent.VK_BACK_SPACE)
                fac.cliBackspace();

            //the enter key works as an 'execute command'
            else if(keyCode == KeyEvent.VK_ENTER) {
                //we are adding the command to the history so we can display it like a terminal would
                fac.cliExecute();
            }
            //in order to use the same cliWindow for the pause menu -> WIP
            else if (keyCode == KeyEvent.VK_ESCAPE) {
                fac.winHidePauseMenu();
            }
            //add the key to the command
            else if(Character.isLetter(key) || Character.isDigit(key) || key == ' '){
                fac.cliAddKey(key);
            }
        }
    }

}
