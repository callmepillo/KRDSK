package PaooGame.GameWindow;

public class Options {
    private static boolean wasd = false;
    private static boolean space = false;
    private static boolean debug = false;
    private static boolean winDec = false;
    public static void setWASD(boolean val) { wasd = val; }
    public static void setSpace(boolean val) { space = val; }
    public static boolean getWASD() { return wasd; }
    public static boolean getSpace() { return space; }
    public static String getStatus() {
        return  "wasd: " + wasd + "\n" +
                "space: " + space + "\n";
    }
}
