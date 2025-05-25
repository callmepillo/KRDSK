package PaooGame.Input;

import PaooGame.Entity.Player;
import PaooGame.GameWindow.Bar;
import PaooGame.GameWindow.CliWindow;
import PaooGame.GameWindow.GameWindow;
import PaooGame.GameWindow.Options;

public class InputFacade {
    private GameWindow win;
    private CliWindow cli;
    private Player player;
    private Bar bar;

    public InputFacade(GameWindow win) {
        this.win = win;
        this.cli = win.GetCliWindow();
        this.player = win.GetPlayer();
        this.bar = win.GetBar();
    }

    public boolean optGetWasd() {
        return Options.getWASD();
    }

    public boolean optGetSpace() {
        return Options.getSpace();
    }

    public void playerJump(boolean state) {
        update();
        player.setJump(state);
    }

    public void playerLeft(boolean state) {
        update();
        player.setLeft(state);
    }

    public void playerDown(boolean state) {
        update();
        player.setDown(state);
    }

    public void playerRight(boolean state) {
        update();
        player.setRight(state);
    }

    public void winDisplayPauseMenu() {
        win.DisplayPauseMenu();
    }

    public void barToggleRoom(int digit) {
        update();
        if (bar.isActive(digit)) {
            win.removeRoom(digit);
            bar.setActive(digit, false);
        }
        else {
            win.addRoom(digit);
            bar.setActive(digit, true);
        }
    }

    public void winSetMouse(int x, int y) {
        win.SetMouse(x, y);
    }

    public void winSetMousePressed(boolean state) {
        win.SetMousePressed(state);
    }

    public void cliBackspace() {
        update();
        String g = cli.getUserInput();
        if (!g.isEmpty() && !g.equals(">") ) {
            cli.setUserInput(g.substring(0, g.length() - 1));
        }
    }

    public void cliExecute() {
        update();
        String prompt = cli.getUserInput();
        cli.addHistory();
        win.handleWindowCommand(prompt);
    }

    public void winHidePauseMenu() {
        win.HidePauseMenu();
    }

    public void cliAddKey(char key) {
        update();
        cli.setUserInput(cli.getUserInput() + key);
    }

    private void update() {
        if (this.cli == null)
            this.cli = win.GetCliWindow();
        if (this.player == null)
            this.player = win.GetPlayer();
        this.bar = win.GetBar();
    }
}
