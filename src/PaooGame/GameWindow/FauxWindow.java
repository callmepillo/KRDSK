package PaooGame.GameWindow;

import PaooGame.Entity.Ambassador;
import PaooGame.Entity.Camera;
import PaooGame.Entity.Guard;
import PaooGame.Entity.Player;
import PaooGame.Graphics.Colors;
import PaooGame.Levels.Door;
import PaooGame.Levels.Level;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

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
    private boolean detected = false;
    private int detectionTimer = 0;
    private static FauxWindow draggedWindow = null;
    private static FauxWindow playerWindow = null;
    private boolean isDragged;
    private int levelOffset;
    private static GameWindow win;
    private int randDelay = 0;
    private double rand = 0;

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
    public void enterPlayer(Player player, int playerX, int playerY) {
        this.player = player;
        playerWindow = this;
        this.player.reset(posX + playerX, posY + playerY);
        this.player.setInAir(true);
    }

    public void leavePlayer() {
        playerWindow = null;
        this.player = null;
    }

    public static int getPlayerRoom() {
        if(playerWindow != null)
            return playerWindow.room;
        else return 0;
    }

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

    public static void setWin(GameWindow window) {
        win = window;
    }

    public void Update(int mouseX, int mouseY, boolean mouseP) {
        Guard[] guards = level.getRoomGuards(room);
        Camera[] cameras = level.getRoomCameras(room);
        Ambassador amb = level.getRoomAmbassador(room);
        if(guards != null)
            for(Guard guard: guards)
                guard.Update(posX - levelOffset, posY, level.GetRoomMap(room));
        if(cameras != null)
            for(Camera cam: cameras)
                cam.Update(posX - levelOffset, posY, level.GetRoomMap(room));
        if(amb != null && playerWindow == this) {
            amb.Update(posX - levelOffset, posY, level.GetRoomMap(room));
            GameWindow.gameWon = true;
        }

        if(playerWindow == this && visible) {
            //check door
            Door enterDoor = null;
            if (level.getRoomDoors(room) != null && (enterDoor = CollisionChecker.CheckDoor(player.getRectangle(), posX - levelOffset, posY, level.getRoomDoors(room))) != null) {
                win.EnterRoom(enterDoor.getDestinationRoom(), enterDoor.getDestX(), enterDoor.getDestY());
            } else {
                //check offset
                int newOffset = CollisionChecker.CheckCloseToBorder(player.getRectangle(), levelOffset, posX, posX + width);
                if(newOffset > levelOffset) {
                    player.setXY(player.getX() - 2, player.getY());
                }
                else if(newOffset < levelOffset) {
                    player.setXY(player.getX() + 2, player.getY());
                }
                player.Update(posX - levelOffset, posY, level.GetRoomMap(room));
                levelOffset = newOffset;

                if(guards != null || cameras != null)
                    detected = CollisionChecker.CheckPlayerDetected(player.getRectangle(), guards, cameras);

                if(detected && !GameWindow.gameOver)
                    detectionTimer++;
                else if(detectionTimer > 0 && !GameWindow.gameOver)
                    detectionTimer--;

                if(detectionTimer == 90) {
                    GameWindow.gameOver = true;
                }
            }
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

        if(randDelay == 4) {
            rand = Math.random();
            randDelay = 0;
        }
        randDelay++;
    }

    public void Draw(Graphics2D g) {
        if(visible) {
            Color orgColor = g.getColor();
            Stroke orgStroke = g.getStroke();
            g.setColor(Colors.background);
            g.fillRect(posX, posY, width, height);
            if (!tooFar())
                level.Draw(g, posX, posX + width, posY, room, levelOffset);
            g.setColor(Colors.term);
            g.setStroke(new java.awt.BasicStroke(3));
            if(playerWindow == this)
                g.drawString("Window:" + room + " X:" + posX + " Y:" + posY + " DM: " + detectionTimer, posX, posY - 20);
            else
                g.drawString("Window:" + room + " X:" + posX + " Y:" + posY, posX, posY - 20);
            if (mouseIn)
                g.drawRect(posX - 5, posY - 5, width + 10, height + 10);
            else
                g.drawRect(posX, posY, width, height);
            g.setStroke(orgStroke);
            g.setColor(orgColor);
            if (player != null)
                player.Draw(g);
            if (player != null && detectionTimer > 0)
                drawDetection(g);
            if (Math.abs(playerWindow.room - this.room) > 3)
                drawFog(g);

            //draw hitbox for doors
//            for(Door door: level.getDoors())
//                door.drawHitbox(g,posX - levelOffset, posY);
        }
    }

    public void drawDetection(Graphics2D g) {
        Color orgColor = g.getColor();
        Stroke orgStroke = g.getStroke();

        int offsetX = 0;
        int offsetY = 0;

        if (!GameWindow.gameOver) {
            offsetX = (int) (rand * 10 - 5);
            offsetY = (int) (rand * 10 - 5);
        }
        g.setColor(new Color(255, 0, 0, detectionTimer));
        g.fillRect(posX + detectionTimer/10 * offsetX, posY + detectionTimer/10 * offsetY, width, height);

        g.setStroke(orgStroke);
        g.setColor(orgColor);
    }

    public void drawFog(Graphics2D g) {
        Color orgColor = g.getColor();
        Stroke orgStroke = g.getStroke();

        int offsetX = (int) (rand * 10);
        int offsetY = (int) (rand * 10);
        int width = (int) (rand * 10);


        g.setColor(new Color(255, 255, 255, 50));
        g.fillRect(posX, posY, FauxWindow.width, height);
        g.setColor(new Color(0,0,0, 100));
        g.fillRect(posX + 100 + offsetX, posY + 100 + offsetY, FauxWindow.width - offsetX - 100, 1 + width);
        g.fillRect(posX, posY + 300 + offsetY, FauxWindow.width - 200 + offsetX, 1 + width);
        g.fillRect(posX + offsetX, posY + 200 + offsetY, FauxWindow.width - offsetX, 1 + width);
        g.fillRect(posX, posY + 400 + offsetY, FauxWindow.width - 200 + offsetX, 1 + width);


        g.setStroke(orgStroke);
        g.setColor(orgColor);
    }

    public boolean tooFar() {
        return (Math.abs(playerWindow.room - this.room) > 3);
    }

    public int GetRoom() {
        return room;
    }
}
