package PaooGame;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println(screenDimension.width + " " + screenDimension.height);
        Game paooGame = new Game("PaooGame", screenDimension.width, screenDimension.height);
        paooGame.StartGame();
    }
}
