package PaooGame.GameWindow;

import PaooGame.Graphics.Colors;
import PaooGame.Levels.Level;

import javax.swing.*;
import java.awt.*;

public class FauxWindow extends JPanel {
    private Level level;
    //private List<Entities> ent;
    protected int posX;
    protected int posY;
    protected int width;
    protected int height;
    protected int room;
    private boolean enlarged;

    //This constructor is for a basic FauxWindow, used for CliWindow
    public FauxWindow(int x, int y, int width, int height) {
        this.posX = x;
        this.posY = y;
        this.width = width;
        this.height = height;
        this.room = -1;
    }

    //This one is for a window that contains a level
    public FauxWindow(int x, int y, int width, int height, Level level, int room) {
        this.posX = x;
        this.posY = y;
        this.width = width;
        this.height = height;
        this.level = level;
        this.room = room;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public void setLevel(Level level) { this.level = level; }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void Update(int mouseX, int mouseY) {
        if(mouseX >= posX && mouseX <= posX+width && mouseY >= posY && mouseY <= posY+height)
            enlarged = true;
        else
            enlarged = false;
    }

    public void Draw(Graphics2D g) {
        Color orgColor = g.getColor();
        Stroke orgStroke = g.getStroke();
        level.Draw(g, posX, posX + width, posY, room);
        g.setColor(Colors.term);
        g.setStroke(new java.awt.BasicStroke(3));
        if(enlarged)
            g.drawRect(posX-5, posY-5, width+10, height+10);
        else
            g.drawRect(posX, posY, width, height);
        g.setStroke(orgStroke);
        g.setColor(orgColor);
    }

    public int GetRoom() {
        return room;
    }
}
