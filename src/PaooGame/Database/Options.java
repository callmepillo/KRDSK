package PaooGame.Database;

public class Options {
    private boolean wasd = false;
    private boolean space = false;
    private boolean debug = false;
    private boolean winDesc = false;
    public void setWASD(boolean val) { wasd = val; }
    public void setSpace(boolean val) { space = val; }
    public void setDebug(boolean val) { debug = val; }
    public void setWinDesc(boolean val) { winDesc = val; }
    public boolean getWASD() { return wasd; }
    public boolean getSpace() { return space; }
    public boolean getDebug() { return debug; }
    public boolean getWinDesc() { return winDesc; }
    public String getStatus() {
        return  "wasd: " + wasd + "\n" +
                "space: " + space + "\n";
    }
}
