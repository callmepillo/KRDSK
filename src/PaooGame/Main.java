package PaooGame;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1"); //repair scaling
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println(screenDimension.width + " " + screenDimension.height);
        Game paooGame = new Game("PaooGame", screenDimension.width, screenDimension.height);
        paooGame.StartGame();
        //Linia 1
        //linia 2/sebi
        //Linia 3
    }
}
