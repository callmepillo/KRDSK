package PaooGame.GameWindow;

import PaooGame.Graphics.Colors;
import PaooGame.Levels.Level;
import PaooGame.Tiles.Tile;

import javax.swing.*;
import java.awt.*;

public class FauxWindow extends JPanel {
    private Level level;
    //private List<Entities> ent;
    protected int posX;
    protected int posY;
    private int offsetMX;
    private int offsetMY;
    protected int width;
    protected int height;
    protected int room;
    private boolean mouseIn;
    private Player player;
    protected boolean visible;

    //This constructor is for a basic FauxWindow, used for CliWindow
    public FauxWindow(int x, int y, int width, int height) {
        this.posX = x;
        this.posY = y;
        this.width = width;
        this.height = height;
        this.room = -1;
        this.visible = true;
    }

    //This one is for a window that contains a level
    public FauxWindow(int x, int y, int width, int height, Level level, int room) {
        this.posX = x;
        this.posY = y;
        this.width = width;
        this.height = height;
        this.level = level;
        this.room = room;
        this.mouseIn = false;
        this.player = null;
        this.visible = false;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public void setLevel(Level level) { this.level = level; }
    public void setVisible(boolean vis) { this.visible = vis; }
    public void enterPlayer(Player player) {
        this.player = player;
        player.setXY(posX, posY + height - Tile.TILE_HEIGHT);
    }
    public void leavePlayer() { this.player = null; }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void move(int mouseX, int mouseY) {
        if(player == null) {
            posX = mouseX + offsetMX;
            posY = mouseY + offsetMY;
        }
    }

    public void Update(int mouseX, int mouseY, boolean mouseP) {
        if(player != null)
            player.Update(posX, posX+width, posY, posY+height);
        if(mouseX >= posX && mouseX <= posX+width && mouseY >= posY && mouseY <= posY+height && visible) {
            if(mouseP && offsetMX == 0 && offsetMY == 0) {
                offsetMX = posX - mouseX;
                offsetMY = posY - mouseY;
            }
            mouseIn = true;
            if(mouseP)
                move(mouseX, mouseY);
        }
        else {
            offsetMX = 0;
            offsetMY = 0;
            mouseIn = false;
        }
    }

    public void Draw(Graphics2D g) {
        if(visible) {
            Color orgColor = g.getColor();
            Stroke orgStroke = g.getStroke();
            level.Draw(g, posX, posX + width, posY, room);
            g.setColor(Colors.term);
            g.setStroke(new java.awt.BasicStroke(3));
            if (mouseIn)
                g.drawRect(posX - 5, posY - 5, width + 10, height + 10);
            else
                g.drawRect(posX, posY, width, height);
            g.setStroke(orgStroke);
            g.setColor(orgColor);
            if (player != null)
                player.Draw(g);
        }
    }

    public int GetRoom() {
        return room;
    }
}
