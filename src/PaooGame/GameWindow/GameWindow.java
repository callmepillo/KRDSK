package PaooGame.GameWindow;

import PaooGame.Database.DatabaseManager;
import PaooGame.Database.Options;
import PaooGame.Database.PlayerData;
import PaooGame.Entity.Player;
import PaooGame.Graphics.Messages;
import PaooGame.Input.InputController;
import PaooGame.Levels.*;
import PaooGame.Tiles.Tile;
import org.sqlite.Function;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Objects;

public class GameWindow
{
    private JFrame  wndFrame;       /*!< fereastra principala a jocului*/
    private String  wndTitle;       /*!< titlul ferestrei*/
    private int     wndWidth;       /*!< latimea ferestrei in pixeli*/
    private int     wndHeight;      /*!< inaltimea ferestrei in pixeli*/
    private Canvas  canvas;         /*!< "panza/tablou" in care se poate desena*/

    private static List<FauxWindow> windows = new ArrayList<>();
    private CliWindow cliMenu;
    private Bar     statusBar;
    private Player player;
    private Level level;

    private KeyListener menuControl;
    private KeyListener playerControl;
    private MouseMotionListener mouseMotionControl;
    private MouseListener mousePressedControl;
    private int     mouseX;
    private int     mouseY;
    private boolean mousePressed;
    private CliDictionary dict;

    private boolean stop;
    private boolean inLevel;
    public static boolean gameOver;
    public static boolean loggedIn;
    public static boolean gameWon;

    public GameWindow(String title, int width, int height){
        wndTitle    = title;    /*!< Retine titlul ferestrei.*/
        wndWidth    = width;    /*!< Retine latimea ferestrei.*/
        wndHeight   = height;   /*!< Retine inaltimea ferestrei.*/
        wndFrame    = null;     /*!< Fereastra nu este construita.*/
        player      = null;
        level       = null;
        stop        = false;
        inLevel = false;
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

        //Fullscreen
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(wndFrame);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(wndWidth, wndHeight));
        canvas.setMaximumSize(new Dimension(wndWidth, wndHeight));
        canvas.setMinimumSize(new Dimension(wndWidth, wndHeight));

        menuControl = ctrl.kControl;
        mouseMotionControl = ctrl.mMControl;
        mousePressedControl = ctrl.mPControl;
        playerControl = ctrl.pControl;

//        canvas.addKeyListener(menuControl);
//        canvas.addMouseListener(new MouseControls());
//        canvas.addMouseMotionListener(new MouseControls());

//        windows.add(new CliWindow(0,0, wndWidth, wndHeight));
//        player = new Player(0,0);

        FauxWindow.setWin(this);
        FauxWindow.setWindowSize(8*Tile.TILE_WIDTH, 6*Tile.TILE_HEIGHT);
        cliMenu = new CliWindow(0,0, wndWidth, wndHeight);
        dict = new CliDictionary(this);

        DisplayLoginMenu();

        wndFrame.add(canvas);
        wndFrame.pack();
        wndFrame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                //System.out.println("Bye");
                windowFocusLost();
            }

            public void windowGainedFocus(WindowEvent e) {

            }

        });

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
    public int GetMouseX() { return mouseX; }
    public int GetMouseY() { return mouseY; }
    public boolean GetMousePressed() { return mousePressed; }
    public Player GetPlayer() { return player; }
    public List<FauxWindow> GetWindows() { return windows; }
    public Bar GetBar() { return statusBar; }
    public Container GetContent() { return wndFrame.getContentPane(); }
    public boolean GetStop() { return stop; }
    public CliWindow GetCliWindow() { return cliMenu; }
    public boolean IsInLevel() { return inLevel; }

    public void SetMouse(int x, int y) {
        mouseX = x;
        mouseY = y;
    }
    public void SetMousePressed(boolean state) { mousePressed = state; }
    public void SetStop(boolean state) { stop = state; }

    public void Stop() {
        wndFrame.dispose();
    }
    public void StartLevel(int levelNumber) {
        switch (levelNumber) {
            case(1):
                level = new LevelOne();
                break;
            case(2):
                level = new LevelTwo();
                break;
            case(3):
                level = new LevelThree();
                break;
            default:
                GetCliWindow().addText(Messages.lvlNotAvalible);
                return;
        }
        inLevel = true;
        gameWon = false;
        gameOver = false;
        removeAllListeners();
        windows.remove(cliMenu);
        statusBar = new Bar(50, wndHeight - 150, 100, 50, level.GetNumberOfRooms());
        for(int i = 0; i < level.GetNumberOfRooms(); ++i) {
            windows.add(new FauxWindow(100, 500, level, i));
        }

        canvas.addMouseListener(mousePressedControl);
        canvas.addMouseMotionListener(mouseMotionControl);
        statusBar.SetActive(true);

        player = Player.getInstance();
        player.reset(0, 3*Tile.TILE_HEIGHT);
        getRoom(0).enterPlayer(player, 0, 3*Tile.TILE_HEIGHT);
        //EnterRoom(0, 0, 3*Tile.TILE_HEIGHT);
        canvas.addKeyListener(playerControl);
    }

    public void ExitLevel() {
        this.statusBar = null;
        cliMenu.setTransparent(false);
        cliMenu.clearHistory();
        gameOver = false;
        gameWon = false;
        windows.clear();
        removeAllListeners();
        DisplayStartMenu();
    }

    public void EnterRoom(int room, int playerX, int playerY) {
        getRoom(GetCurrentRoom()).leavePlayer();
        getRoom(room).enterPlayer(player, playerX, playerY);
    }

    public int GetCurrentRoom() {
        return FauxWindow.getPlayerRoom();
    }

    public void DisplayPauseMenu() {
        if(!windows.contains(cliMenu) && inLevel) {
            cliMenu.setTransparent(true);
            windows.add(cliMenu);
            cliMenu.clearHistory();
            cliMenu.addText(Messages.paused);

            removeAllListeners();
            canvas.addKeyListener(menuControl);
        }
    }

    public void DisplayStartMenu() {
        if(!windows.contains(cliMenu)) {
            inLevel = false;
            windows.add(cliMenu);
            cliMenu.clearHistory();
            cliMenu.addText(Messages.title);

            removeAllListeners();
            canvas.addKeyListener(menuControl);
        }
    }

    public void DisplayLoginMenu() {
        loggedIn = false;
        cliMenu.setStringX(500);
        cliMenu.setOffsetLines(10);
        cliMenu.addText("Please login using the command: login {your_name}");
        windows.add(cliMenu);
        canvas.addKeyListener(menuControl);
    }

    public void HidePauseMenu() {
        if(windows.size() != 1 && inLevel) {
            cliMenu.setTransparent(false);
            windows.remove(cliMenu);

            removeAllListeners();
            canvas.addMouseListener(mousePressedControl);
            canvas.addMouseMotionListener(mouseMotionControl);
            canvas.addKeyListener(playerControl);
        }
    }

    public void removeAllListeners() {
        canvas.removeMouseMotionListener(mouseMotionControl);
        canvas.removeMouseListener(mousePressedControl);
        canvas.removeKeyListener(playerControl);
        canvas.removeKeyListener(menuControl);
    }

    public void removeRoom(int number) {
        for(FauxWindow win: windows) {
            if(win.GetRoom() == number)
                win.setVisible(false);
        }
    }

    public void addRoom(int number) {
        for(FauxWindow win: windows) {
            if(win.GetRoom() == number)
                win.setVisible(true);
        }
    }

    public static FauxWindow getRoom(int number) {
        for(FauxWindow win: windows) {
            if(win.GetRoom() == number)
                return win;
        }
        return null;
    }

    public void windowFocusLost() {
        if(player != null) {
            player.resetDirBooleans();
        }
    }

    public void handleWindowCommand(String prompt) {
//        String[] args = prompt.split(" ");
//
//        switch (args[0]) {
//            case "exit":
//                if(IsInLevel())
//                    ExitLevel(); //save/load?
//                else
//                    SetStop(true);
//                break;
//            case "clear":
//                GetCliWindow().clearHistory();
//                break;
//            case "play":
//                if(args.length > 1)
//                    try {
//                        StartLevel(Integer.parseInt(args[1]));
//                    }
//                    catch(NumberFormatException ex) {
//                        System.out.println("whoops");
//                    }
//                else
//                    GetCliWindow().addText(Messages.lvlNotAvalible);
//                break;
//            case "db":
//                if(args.length > 2)
//                    switch (args[1]) {
//                        case "enter":
//                            try {
//                                EnterRoom(Integer.parseInt(args[2]), 0, 2*Tile.TILE_HEIGHT);
//                                HidePauseMenu();
//                            }
//                            catch(NumberFormatException ex) {
//                                System.out.println("whoops");
//                            }
//                    }
//                break;
//            case "option":
//                if(args.length > 2)
//                    switch (args[1]) {
//                        case "wasd":
//                            try {
//                                PlayerData.opt.setWASD(Boolean.parseBoolean(args[2]));
//                                GetCliWindow().addText(Messages.option("wasd", args[2]));
//                                HidePauseMenu();
//                            }
//                            catch(NumberFormatException ex) {
//                                System.out.println("whoops");
//                            }
//                            break;
//                        case "space":
//                            try {
//                                PlayerData.opt.setSpace(Boolean.parseBoolean(args[2]));
//                                GetCliWindow().addText(Messages.option("space", args[2]));
//                                HidePauseMenu();
//                            }
//                            catch(NumberFormatException ex) {
//                                System.out.println("whoops");
//                            }
//                            break;
//                        default:
//                            GetCliWindow().addText(Messages.optionNotAvalible);
//                    }
//                else if (args.length == 2 && args[1].equals("status"))
//                    GetCliWindow().addText(Messages.optionStatus());
//
//                break;
//            case "help":
//                if(args.length > 1) {
//                    switch (args[1]) {
//                        case "option":
//                            GetCliWindow().addText(Messages.optionHelp);
//                            break;
//                        default:
//                            GetCliWindow().addText(Messages.helpPageNotAvalible);
//                            break;
//                    }
//                }
//                else
//                    GetCliWindow().addText(Messages.help);
//                break;
//            case "title":
//                GetCliWindow().addText(Messages.title);
//                break;
//            case "paused":
//                GetCliWindow().addText(Messages.paused);
//                break;
//            case "numpie":
//                GetCliWindow().addText("Matematici Discrete - Sebi 2024");
//                break;
//            default:
//                GetCliWindow().addText("Command \"" + prompt + "\" not found");
//                break;
//
        String[] args = prompt.split(" ", 2);
        if(args.length > 1)
            dict.execute(args[0], args[1]);
        else
            dict.execute(args[0], null);
    }

    public void GameOver() {
        if(!windows.contains(cliMenu) && inLevel) {
            cliMenu.setTransparent(true);
            windows.add(cliMenu);
            cliMenu.clearHistory();
            cliMenu.addText(Messages.gameOver);

            removeAllListeners();
            canvas.addKeyListener(menuControl);}
    }

    public void EndLevel() {
        if(!windows.contains(cliMenu) && inLevel) {
            cliMenu.setTransparent(true);
            windows.add(cliMenu);
            cliMenu.clearHistory();
            cliMenu.addText("you won.");
            PlayerData.levelsFinished[level.getLevelId()] = true;
            System.out.println(level.getLevelId());
            removeAllListeners();
            canvas.addKeyListener(menuControl);}
    }

    public void Login(String s) {
        System.out.println(s + " logged in.");
        DatabaseManager.load(s);
        loggedIn = true;
        PlayerData.name = s;
        windows.remove(cliMenu);
        cliMenu.setStringX(50);
        DisplayStartMenu();
    }
}
