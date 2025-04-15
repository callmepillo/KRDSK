package PaooGame.GameWindow;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

import PaooGame.Graphics.Colors;

public class CliWindow extends FauxWindow {
    private String userInput;
    private int stringX;
    private int stringY;
    private ArrayList<String> history;
    private boolean transparent;
    private int line;

    public CliWindow(int x, int y, int width, int height) {
        super(x, y, width, height);
        userInput = "";
        stringX = 50;
        stringY = 50;
        transparent = false;
        history = new ArrayList<String>();
        line = 2;
    }

    public void addText(String src) {
        String[] lines = src.split("\n");
        for(String line: lines)
            history.add(line);
    }

    public String getUserInput() {
        if(!Objects.equals(userInput, ""))
            return userInput.substring(1);
        else return "";
    }

    public void setUserInput(String src) {
        userInput = ">" + src;
    }

    public void addHistory() {
        history.add(userInput);
        userInput = "";
    }

    public ArrayList<String> getHistory() {
        return history;
    }

    public void clearHistory() {
        history.clear();
        line = 2;
    }

    public void setTransparent(boolean op) {
        transparent = op;
    }

    @Override
    public void Update(int mouseX, int mouseY, boolean mouseP) {
    }

    @Override
    public void Draw(Graphics2D g) {
        Color orgColor = g.getColor();
        Stroke orgStroke = g.getStroke();
        if(transparent)
            g.setColor(Colors.background_transp);
        else
            g.setColor(Colors.background);
        g.fillRect(posX, posY, width, height);
        if(transparent)
            g.setColor(Colors.term_transp);
        else
            g.setColor(Colors.term);
        g.setStroke(new java.awt.BasicStroke(10));
        g.drawRect(posX, posY, width, height);
        line = 2;
        for(String str : history)
        {
            g.drawString(str, stringX, line*30);
            line+=1;
        }
        if(Objects.equals(userInput, ""))
            g.drawString( ">_", stringX, line*30);
        else
            g.drawString(userInput + "_", stringX, line*30);
        g.setStroke(orgStroke);
        g.setColor(orgColor);
    }
}
