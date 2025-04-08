package PaooGame;

import PaooGame.GameWindow.GameWindow;
import PaooGame.Graphics.Messages;
import PaooGame.Tiles.Tile;

import javax.swing.event.MouseInputAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class InputController {
    private GameWindow Win;
    private int[][] distortX;
    private int[][] distortY;
    public PlayerControl pControl;
    public MouseControls mControl;
    public MenuControl   kControl;

    public InputController(GameWindow Window, int[][] distortX, int[][] distortY) {
        this.Win = Window;
        this.distortX = distortX;
        this.distortY = distortY;
        pControl = new PlayerControl();
        mControl = new MouseControls();
        kControl = new MenuControl();
    }

    public class PlayerControl extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent event) {
            int keyCode = event.getKeyCode();
            if (keyCode == KeyEvent.VK_LEFT) {
                if(Win.GetPlayer().getX() >= 0)
                    Win.GetPlayer().subX();
            }
            if (keyCode == KeyEvent.VK_RIGHT) {
                if(Win.GetPlayer().getX() <= (Win.GetWndWidth() - Tile.TILE_WIDTH))
                    Win.GetPlayer().addX();
            }
            if (keyCode == KeyEvent.VK_UP) {
                if(Win.GetPlayer().getY() >= 0)
                    Win.GetPlayer().subY();
            }
            if (keyCode == KeyEvent.VK_DOWN) {
                if(Win.GetPlayer().getY() <= (Win.GetWndHeight() - Tile.TILE_HEIGHT))
                    Win.GetPlayer().addY();
            }
            if (keyCode == KeyEvent.VK_ESCAPE) {
                Win.DisplayCLIMenu();
            }
        }
    }

    public class MouseControls extends MouseInputAdapter {
        public void mouseMoved(MouseEvent e) {
            Win.SetMouse(distortX[e.getY()][e.getX()], distortY[e.getY()][e.getX()]);
        }
    }

    public class MenuControl extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent event) {
            char key = event.getKeyChar();
            int keyCode = event.getKeyCode();
            if(keyCode == KeyEvent.VK_BACK_SPACE && !Objects.equals(Win.GetCliWindow().getUserInput(), "")) {
                if(!Objects.equals(Win.GetCliWindow().getUserInput(), ">"))
                    Win.GetCliWindow().setUserInput(Win.GetCliWindow().getUserInput().substring(0,Win.GetCliWindow().getUserInput().length() - 1));
            }
            else if(keyCode == KeyEvent.VK_ENTER) {
                String prompt = Win.GetCliWindow().getUserInput();
                String[] args = prompt.split(" ");
                Win.GetCliWindow().addHistory();
                switch (args[0]) {
                    case "exit":
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
                    case "help":
                        Win.GetCliWindow().addText(Messages.help);
                        break;
                    case "title":
                        Win.GetCliWindow().addText(Messages.title);
                        break;
                }
            }
            else if (keyCode == KeyEvent.VK_ESCAPE) {
                Win.HideCLIMenu();
            }
            else if(Character.isLetter(key) || Character.isDigit(key) || key == ' '){
                Win.GetCliWindow().setUserInput(Win.GetCliWindow().getUserInput() + key);
                //System.out.println(cliMenu.getUserInput());
            }
        }
    }

}
