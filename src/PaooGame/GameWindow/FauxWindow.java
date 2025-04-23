package PaooGame.GameWindow;

import PaooGame.Graphics.Colors;
import PaooGame.Levels.Level;
import PaooGame.Tiles.Tile;
import jdk.swing.interop.SwingInterOpUtils;

import javax.swing.*;
import java.awt.*;

public class FauxWindow extends JPanel {
    private Level level;
    //private List<Entities> ent;
    protected int posX;
    protected int posY;
    private int offsetMX;
    private int offsetMY;
    protected static int width;
    protected static int height;
    protected int room;
    private boolean mouseIn;
    private Player player;
    protected boolean visible;
    private static FauxWindow draggedWindow = null;
    private boolean isDragged;
    private int levelOffset;

    public static void setWindowSize(int width, int height) {
        FauxWindow.width = width;
        FauxWindow.height = height;
    }

    //This constructor is for a basic FauxWindow, used for CliWindow
    public FauxWindow(int x, int y) {
        this.posX = x;
        this.posY = y;
        this.room = -1;
        this.visible = true;
    }

    //This one is for a window that contains a level
    public FauxWindow(int x, int y, Level level, int room) {
        this.posX = x;
        this.posY = y;
        this.level = level;
        this.room = room;
        this.mouseIn = false;
        this.player = null;
        this.visible = false;
        this.isDragged = false;
        this.levelOffset = 0;
    }

    public void setLevel(Level level) { this.level = level; }
    public void setVisible(boolean vis) { this.visible = vis; }
    public void enterPlayer(Player player) {
        this.player = player;
        player.setXY(posX, posY + height - Tile.TILE_HEIGHT);
    }
    public void leavePlayer() { this.player = null; this.levelOffset = 0; }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isDraggable(int mouseX, int mouseY, boolean mouseP) {
        return (mouseX >= posX && mouseX <= posX+width && mouseY >= posY && mouseY <= posY+height && visible);
    }

    public void move(int mouseX, int mouseY) {
        if(player == null) {
            posX = mouseX + offsetMX;
            posY = mouseY + offsetMY;
        }
    }

    public void Update(int mouseX, int mouseY, boolean mouseP) {
        if(player != null && visible) {
            int newOffset = CollisionChecker.CheckCloseToBorder(player.getRectangle(), levelOffset, posX, posX + width);
            if(newOffset > levelOffset)
                player.setXY(player.getX() - 1, player.getY());
            else if(newOffset < levelOffset)
                player.setXY(player.getX() + 1, player.getY());
            levelOffset = newOffset;
            player.Update(posX - levelOffset, posY, level.GetRoomMap(room));
        }

        if (draggedWindow == this) {
            if (mouseP) {
                move(mouseX, mouseY);
            } else {
                // Mouse released, stop dragging
                offsetMX = 0;
                offsetMY = 0;
                draggedWindow = null;
                isDragged = false;
            }
            return;
        }

        // If no window is being dragged and the mouse is over this one
        if (draggedWindow == null && isDraggable(mouseX, mouseY, mouseP) && player == null) {
            if (mouseP) {
                offsetMX = posX - mouseX;
                offsetMY = posY - mouseY;
                draggedWindow = this;
                isDragged = true;
            }
            mouseIn = true;
        } else {
            mouseIn = false;
            isDragged = false;
        }
    }

    public void Draw(Graphics2D g) {
        if(visible) {
            Color orgColor = g.getColor();
            Stroke orgStroke = g.getStroke();
            g.setColor(Colors.background);
            g.fillRect(posX, posY, width, height);
            level.Draw(g, posX, posX + width, posY, room, levelOffset);
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
