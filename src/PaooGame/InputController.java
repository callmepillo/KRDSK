package PaooGame;

//the game window which we want to control
import PaooGame.GameWindow.GameWindow;

//for constants regarding cli messages
import PaooGame.GameWindow.Options;
import PaooGame.Graphics.Messages;

//for TILE_HEIGHT and TILE_WIDTH constants
import PaooGame.Tiles.Tile;

//we use the MouseInputAdapter from Swing and MouseEvent from AWT to handle the mouse
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

//we use the KeyAdapter/KeyEvent from AWT
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//using Objects.equals for string comparison for null safety and good practice
import java.util.Objects;

public class InputController {
    private GameWindow Win;
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
        this.Win = Window;
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
            boolean optWASD = Options.getWASD();
            boolean optSPACE = Options.getSpace();

            if(optWASD) {
                switch (event.getKeyCode()) {
                    case KeyEvent.VK_W:
                        if(!optSPACE)
                            Win.GetPlayer().setJump(true);
                        break;
                    case KeyEvent.VK_A:
                        Win.GetPlayer().setLeft(true);
                        break;
                    case KeyEvent.VK_S:
                        Win.GetPlayer().setDown(true);
                        break;
                    case KeyEvent.VK_D:
                        Win.GetPlayer().setRight(true);
                        break;
                }
            }
            else {
                switch (event.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if(!optSPACE)
                            Win.GetPlayer().setJump(true);
                        break;
                    case KeyEvent.VK_LEFT:
                        Win.GetPlayer().setLeft(true);
                        break;
                    case KeyEvent.VK_DOWN:
                        Win.GetPlayer().setDown(true);
                        break;
                    case KeyEvent.VK_RIGHT:
                        Win.GetPlayer().setRight(true);
                        break;
                }
            }

            if(event.getKeyCode() == KeyEvent.VK_ESCAPE)
                Win.DisplayPauseMenu();

            if(optSPACE && (event.getKeyCode() == KeyEvent.VK_SPACE))
                Win.GetPlayer().setJump(true);


//            int keyCode = event.getKeyCode();
//            if (keyCode == KeyEvent.VK_LEFT) {
//                Win.GetPlayer().moveLeft();
//            }
//            if (keyCode == KeyEvent.VK_RIGHT) {
//                Win.GetPlayer().moveRight();
//            }
//            if (keyCode == KeyEvent.VK_SPACE) {
//                Win.GetPlayer().jump();
//            }
           // if (keyCode == KeyEvent.VK_ESCAPE) {
                // if(Win.GetPlayer().getY() >= 0)
             //   Win.DisplayPauseMenu();
            //}
//            if (keyCode == KeyEvent.VK_DOWN) {
//               // if(Win.GetPlayer().getY() <= (Win.GetWndHeight() - Tile.TILE_HEIGHT))
//                    Win.GetPlayer().moveDown();
//            }
//
           if (Character.isDigit(event.getKeyChar())) {
                int number = Integer.parseInt(Character.toString(event.getKeyChar()));
                if (Win.GetBar().isActive(number)) {
                    Win.removeRoom(number);
                    Win.GetBar().setActive(number, false);
                }
                else {
                    Win.addRoom(number);
                    Win.GetBar().setActive(number, true);
                }
           }
        }

        public void keyReleased(KeyEvent event)
        {
//            switch (event.getKeyCode()){
//                case KeyEvent.VK_UP:
//                case KeyEvent.VK_W:
//                    Win.GetPlayer().setUp(false);
//                    break;
//                case KeyEvent.VK_LEFT:
//                case KeyEvent.VK_A:
//                    Win.GetPlayer().setLeft(false);
//                    break;
//                case KeyEvent.VK_DOWN:
//                case KeyEvent.VK_S:
//                    Win.GetPlayer().setDown(false);
//                    break;
//                case KeyEvent.VK_RIGHT:
//                case KeyEvent.VK_D:
//                    Win.GetPlayer().setRight(false);
//                    break;
//                case KeyEvent.VK_SPACE:
//                    Win.GetPlayer().setJump(false);
//                    break;
//            }

            boolean optWASD = Options.getWASD();
            boolean optSPACE = Options.getSpace();

            if(optWASD) {
                switch (event.getKeyCode()) {
                    case KeyEvent.VK_W:
                        if(!optSPACE)
                            Win.GetPlayer().setJump(false);
                        break;
                    case KeyEvent.VK_A:
                        Win.GetPlayer().setLeft(false);
                        break;
                    case KeyEvent.VK_S:
                        Win.GetPlayer().setDown(false);
                        break;
                    case KeyEvent.VK_D:
                        Win.GetPlayer().setRight(false);
                        break;
                }
            }
            else {
                switch (event.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if(!optSPACE)
                            Win.GetPlayer().setJump(false);
                        break;
                    case KeyEvent.VK_LEFT:
                        Win.GetPlayer().setLeft(false);
                        break;
                    case KeyEvent.VK_DOWN:
                        Win.GetPlayer().setDown(false);
                        break;
                    case KeyEvent.VK_RIGHT:
                        Win.GetPlayer().setRight(false);
                        break;
                }
            }

            if(event.getKeyCode() == KeyEvent.VK_ESCAPE)
                Win.DisplayPauseMenu();

            if(optSPACE && (event.getKeyCode() == KeyEvent.VK_SPACE))
                Win.GetPlayer().setJump(false);
        }

//            int keyCode=event.getKeyCode();
//           if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_DOWN) {
//                Win.GetPlayer().stopMoving();
//            }
//        /*    if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
//                Win.GetPlayer().stopMoving();
//            }
//            if (keyCode == KeyEvent.VK_DOWN) {
//                Win.GetPlayer().stopVerticalMoving();
//            }
//
//*/          //int key=event.getKeyCode();
//            if(keyCode == KeyEvent.VK_SPACE)
//            {
//                Win.GetPlayer().releaseJump();
//            }
//        }
    }

    //mouse coordinate handler
    public class MouseMotionControls extends MouseInputAdapter {
        public void mouseMoved(MouseEvent e) {
            Win.SetMouse(distortX[e.getY()][e.getX()], distortY[e.getY()][e.getX()]);
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
            Win.SetMouse(distortX[e.getY()][e.getX()], distortY[e.getY()][e.getX()]);
        }
    }

    public class MousePressedControls extends MouseInputAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            Win.SetMousePressed(true);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            Win.SetMousePressed(false);
        }
    }

    //keyboard input handler for the CliWindow (
    public class MenuControl extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent event) {
            char key = event.getKeyChar();
            int keyCode = event.getKeyCode();

            //we are first checking the backspace character, that should delete a character (only it there are any to delete)
            if(keyCode == KeyEvent.VK_BACK_SPACE && !Objects.equals(Win.GetCliWindow().getUserInput(), "")) {
                if(!Objects.equals(Win.GetCliWindow().getUserInput(), ">"))
                    Win.GetCliWindow().setUserInput(Win.GetCliWindow().getUserInput().substring(0,Win.GetCliWindow().getUserInput().length() - 1));
            }
            //the enter key works as an 'execute command'
            else if(keyCode == KeyEvent.VK_ENTER) {
                String prompt = Win.GetCliWindow().getUserInput();
                //String[] args = prompt.split(" ");
                Win.GetCliWindow().addHistory(); //we are adding the command to the history so we can display it like a terminal would
                Win.handleWindowCommand(prompt);
                //after we split the command, we are using the first argument to check the requested operation
                /*
                switch (args[0]) {
                    case "exit":
                        if(Win.IsInLevel())
                            Win.DisplayStartMenu(); //save/load?
                        else
                            Win.SetStop(true);
                        break;
                    case "clear":
                        Win.GetCliWindow().clearHistory();
                        break;
                    case "play":
                        if(args.length > 1)
                            try {
                                Win.StartLevel(Integer.parseInt(args[1]));
                            }
                            catch(NumberFormatException ex) {
                                System.out.println("whoops");
                            }
                        break;
                    case "db":
                        if(args.length > 2)
                            switch (args[1]) {
                                case "enter":
                                    try {
                                        Win.EnterRoom(Integer.parseInt(args[2]));
                                        Win.HidePauseMenu();
                                    }
                                    catch(NumberFormatException ex) {
                                        System.out.println("whoops");
                                    }
                            }
                        break;
                    case "help":
                        Win.GetCliWindow().addText(Messages.help);
                        break;
                    case "title":
                        Win.GetCliWindow().addText(Messages.title);
                        break;
                    case "paused":
                        Win.GetCliWindow().addText(Messages.paused);
                        break;
                    case "numpie":
                        Win.GetCliWindow().addText("Matematici Discrete - Sebi 2024");
                        break;
                    default:
                        Win.GetCliWindow().addText("Command \"" + prompt + "\" not found");
                        break;
                }
                */
            }
            //in order to use the same cliWindow for the pause menu -> WIP
            else if (keyCode == KeyEvent.VK_ESCAPE) {
                Win.HidePauseMenu();
            }
            //add the key to the command
            else if(Character.isLetter(key) || Character.isDigit(key) || key == ' '){
                Win.GetCliWindow().setUserInput(Win.GetCliWindow().getUserInput() + key);
            }
        }
    }

}
