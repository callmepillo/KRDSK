package PaooGame.GameWindow;

import PaooGame.*;
import PaooGame.Graphics.Messages;
import PaooGame.Levels.Level;
import PaooGame.Levels.TestLevel;
import PaooGame.Tiles.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

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
    private Level level;
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

    public void BuildGameWindow(InputController ctrl)
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

        menuControl = ctrl.kControl;
        mouseControl = ctrl.mControl;
        playerControl = ctrl.pControl;

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
    public CliWindow GetCliWindow() { return cliMenu; }

    public void SetMouse(int x, int y) {
        mouseX = x;
        mouseY = y;
    }
    public void SetStop(boolean state) { stop = state; }

    public void Stop() {
        wndFrame.dispose();
    }
    public void StartLevel(int levelNumber) {
        if(levelNumber == 1) {
            canvas.removeKeyListener(menuControl);

            windows.remove(cliMenu);
            //Recomended size should be a multiple of Tile.TILE_WIDTH and Tile.TILE_HEIGHT
            windows.add(new FauxWindow(100, 500, 8*Tile.TILE_WIDTH, 6*Tile.TILE_HEIGHT, Level.testLevel, 0));
            windows.add(new FauxWindow(1000, 200, 8*Tile.TILE_WIDTH, 6*Tile.TILE_HEIGHT, Level.testLevel, 1));
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
}
