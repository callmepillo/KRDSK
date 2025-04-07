package PaooGame.GameWindow;

import PaooGame.*;
import PaooGame.Graphics.Messages;
import PaooGame.Tiles.Tile;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameWindow
{
    private JFrame  wndFrame;       /*!< fereastra principala a jocului*/
    private Player  player;
    private String  wndTitle;       /*!< titlul ferestrei*/
    private int     wndWidth;       /*!< latimea ferestrei in pixeli*/
    private int     wndHeight;      /*!< inaltimea ferestrei in pixeli*/
    private Canvas  canvas;         /*!< "panza/tablou" in care se poate desena*/
    private int     playerX;
    private int     playerY;
    private int     mouseX;
    private int     mouseY;
    private List<FauxWindow> windows = new ArrayList<>();
    private CliWindow cliMenu;
    private Bar     statusBar;
    private Level   level;
    private boolean stop;
    private KeyListener menuControl;
    private KeyListener playerControl;
    private MouseMotionListener mouseControl;


    public GameWindow(String title, int width, int height){
        wndTitle    = title;    /*!< Retine titlul ferestrei.*/
        wndWidth    = width;    /*!< Retine latimea ferestrei.*/
        wndHeight   = height;   /*!< Retine inaltimea ferestrei.*/
        wndFrame    = null;     /*!< Fereastra nu este construita.*/
        player      = null;
        level       = null;
        stop        = false;
        cliMenu     = null;
        statusBar   = null;
    }

    public void BuildGameWindow()
    {
        if(wndFrame != null)
        {
            return;
        }

        wndFrame = new JFrame(wndTitle);
        wndFrame.setSize(wndWidth, wndHeight);
        wndFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wndFrame.setResizable(false);
        wndFrame.setLocationRelativeTo(null);
        wndFrame.setVisible(true);
        wndFrame.setFocusable(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(wndWidth, wndHeight));
        canvas.setMaximumSize(new Dimension(wndWidth, wndHeight));
        canvas.setMinimumSize(new Dimension(wndWidth, wndHeight));

        menuControl = new MenuControl();
        mouseControl = new MouseControls();
        playerControl = new PlayerControl();

        canvas.addKeyListener(menuControl);
//        canvas.addMouseListener(new MouseControls());
//        canvas.addMouseMotionListener(new MouseControls());

//        windows.add(new CliWindow(0,0, wndWidth, wndHeight));
//        player = new Player(0,0);

        cliMenu = new CliWindow(0,0, wndWidth, wndHeight);
        cliMenu.addText(Messages.title);
        windows.add(cliMenu);

        statusBar = new Bar(50, wndHeight - 150, 100, 50);

        wndFrame.add(canvas);
        wndFrame.pack();
    }

    public int GetWndWidth()
    {
        return wndWidth;
    }
    public int GetWndHeight()
    {
        return wndHeight;
    }
    public Canvas GetCanvas() {
        return canvas;
    }
    public int GetPlayerX() { return playerX; }
    public int GetPlayerY() { return playerY; }
    public int GetMouseX() { return mouseX; }
    public int GetMouseY() { return mouseY; }
    public Player GetPlayer() { return player; }
    public List<FauxWindow> GetWindows() { return windows; }
    public Bar GetBar() { return statusBar; }
    public Container GetContent() { return wndFrame.getContentPane(); }
    public boolean GetStop() { return stop; }

    public void Stop() {
        wndFrame.dispose();
    }
    public void StartLevel(int levelNumber) {
        if(levelNumber == 1) {
            canvas.removeKeyListener(menuControl);

            windows.remove(cliMenu);
            windows.add(new FauxWindow(100, 500, 800, 500));
            windows.add(new FauxWindow(1000, 200, 800, 500));
            canvas.addMouseMotionListener(mouseControl);
            statusBar.SetActive(true);

            player = new Player(300, 910);
            canvas.addKeyListener(playerControl);
        }
    }

    public void DisplayCLIMenu() {
        if(!windows.contains(cliMenu)) {
            cliMenu.setTransparent(true);
            windows.add(cliMenu);

            canvas.removeKeyListener(playerControl);
            canvas.removeMouseMotionListener(mouseControl);
            canvas.addKeyListener(menuControl);
        }
    }

    public void HideCLIMenu() {
        if(windows.size() != 1) {
            cliMenu.setTransparent(false);
            canvas.removeKeyListener(menuControl);
            windows.remove(cliMenu);
            canvas.addMouseMotionListener(mouseControl);
            canvas.addKeyListener(playerControl);
        }
    }


    public class PlayerControl extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent event) {
            int keyCode = event.getKeyCode();
            if (keyCode == KeyEvent.VK_LEFT) {
                if(player.getX() >= 0)
                    player.subX();
            }
            if (keyCode == KeyEvent.VK_RIGHT) {
                if(player.getX() <= (wndWidth - Tile.TILE_WIDTH))
                    player.addX();
            }
            if (keyCode == KeyEvent.VK_UP) {
                if(player.getY() >= 0)
                    player.subY();
            }
            if (keyCode == KeyEvent.VK_DOWN) {
                if(player.getY() <= (wndHeight - Tile.TILE_HEIGHT))
                    player.addY();
            }
            if (keyCode == KeyEvent.VK_ESCAPE) {
                DisplayCLIMenu();
            }
        }
    }
    public class MouseControls extends MouseInputAdapter {
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }
    }
    public class MenuControl extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent event) {
            char key = event.getKeyChar();
            int keyCode = event.getKeyCode();
            if(keyCode == KeyEvent.VK_BACK_SPACE && !Objects.equals(cliMenu.getUserInput(), "")) {
                if(!Objects.equals(cliMenu.getUserInput(), ">"))
                    cliMenu.setUserInput(cliMenu.getUserInput().substring(0,cliMenu.getUserInput().length() - 1));
            }
            else if(keyCode == KeyEvent.VK_ENTER) {
                String prompt = cliMenu.getUserInput();
                String[] args = prompt.split(" ");
                cliMenu.addHistory();
                switch (args[0]) {
                    case "exit":
                        stop = true;
                        break;
                    case "clear":
                        cliMenu.clearHistory();
                        break;
                    case "play":
                        if(args.length > 1)
                            try {
                                StartLevel(Integer.parseInt(args[1]));
                            }
                            catch(NumberFormatException ex) {
                                System.out.println("whoops");
                            }
                        break;
                    case "help":
                        cliMenu.addText(Messages.help);
                        break;
                    case "title":
                        cliMenu.addText(Messages.title);
                        break;
                }
            }
            else if (keyCode == KeyEvent.VK_ESCAPE) {
                HideCLIMenu();
            }
            else if(Character.isLetter(key) || Character.isDigit(key) || key == ' '){
                cliMenu.setUserInput(cliMenu.getUserInput() + key);
                //System.out.println(cliMenu.getUserInput());
            }
        }
    }
}
