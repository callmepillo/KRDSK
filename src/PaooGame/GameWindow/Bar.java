package PaooGame.GameWindow;

import PaooGame.Graphics.Colors;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bar {
    private List<Cell> cells;
    private int posX;
    private int posY;
    private int space;
    private boolean active;

    private class Cell {
        private int posX;
        private int size;
        private boolean active;
        private int number;

        public Cell(int num, int sz, int pos) {
            active = false;
            number = num;
            size = sz;
            posX = pos;
        }

        public void Draw(Graphics2D g) {
            Color orgColor = g.getColor();
            Stroke orgStroke = g.getStroke();
            g.setColor(Colors.term);
            g.setStroke(new java.awt.BasicStroke(3));
            g.drawRect(posX, posY, size, size);
            g.drawString(Integer.toString(number), (posX-10+size/2), (posY+10+size/2));
            if(active) {
                g.setColor(Colors.activeCell);
                g.fillRect(posX, posY, size, size);
            }
            g.setStroke(orgStroke);
            g.setColor(orgColor);

        }
    }

    public void SetActive(boolean act) {
        active = act;
    }

    public Bar(int pX, int pY, int size, int space, int noCells) {
        cells = new ArrayList<>();
        posX = pX;
        posY = pY;
        for(int i = 0; i < noCells; ++i) {
            cells.add(new Cell(i, size, posX + i*(size+space)));
        }
        active = false;
    }

    public void Draw(Graphics2D g) {
        if(active) {
            for (Cell c : cells) {
                c.Draw(g);
            }
        }
    }

    public boolean isValid(int digit) {
        if (digit < cells.size() && cells.get(digit) != null)
            return true;
        else
            return false;
    }

    public boolean isActive(int number) {
        return cells.get(number).active;
    }

    public void setActive(int cell, boolean state) {
        if (cell < cells.size())
            cells.get(cell).active = state;
    }

    public void clear() {
        for(Cell c: cells)
            c.active = false;
    }
}
