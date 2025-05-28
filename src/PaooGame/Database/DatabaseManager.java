package PaooGame.Database;
import PaooGame.Levels.Level;

import java.security.PublicKey;
import java.sql.*;
import java.util.Arrays;

public class DatabaseManager {
    public static int[][][] getLevel(int number) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/databases/maps.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(1) FROM MAP_LEVEL_" + number + ";");
            rs.next();
            int roomNumber = rs.getInt(1);
            rs.close();
            int[][][] level = new int[roomNumber][Level.LEVEL_HEIGHT][Level.LEVEL_WIDTH];
            rs = stmt.executeQuery( "SELECT * FROM MAP_LEVEL_" + number + ";" );
            while(rs.next()) {
                int room = rs.getInt("room");
                for(int i = 0; i < Level.LEVEL_HEIGHT; ++i) {
                    String rowString = rs.getString("row_" + (i + 1));
                    String[] rowVector = rowString.split(",");
                    for(int j = 0; j < Level.LEVEL_WIDTH; ++j)
                        level[room][i][j] = Integer.parseInt(rowVector[j]);
                }
            }
            rs.close();
            stmt.close();
            c.close();
            return level;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return null;
    }

    public static void load(String name) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/databases/players.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM players WHERE name='" + name + "';" );
            PlayerData.name = name;
            PlayerData.levelsFinished = new boolean[3];
            while(rs.next()) {
                PlayerData.opt.setWASD(rs.getBoolean("wasd"));
                PlayerData.opt.setSpace(rs.getBoolean("space"));
                PlayerData.opt.setDebug(rs.getBoolean("debug"));
                PlayerData.opt.setWinDesc(rs.getBoolean("winDesc"));
                String rowString = rs.getString("lvlFinished");
                String[] rowVector = rowString.split(", ");
                for(int j = 0; j < 3; ++j)
                    PlayerData.levelsFinished[j] = Boolean.parseBoolean(rowVector[j].replace("[","").replace("]",""));
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static void save() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/databases/players.db");
            stmt = c.createStatement();
            String sql = "UPDATE players SET " +
                    "wasd = " + (PlayerData.opt.getWASD() ? (1) : (0)) + ", " +
                    "space = " + (PlayerData.opt.getSpace() ? (1) : (0)) + ", " +
                    "debug = " + (PlayerData.opt.getDebug() ? (1) : (0)) + ", " +
                    "winDesc = " + (PlayerData.opt.getWinDesc() ? (1) : (0)) + ", " +
                    "lvlFinished = '" + Arrays.toString(PlayerData.levelsFinished) + "' " +
                    "WHERE name='" + PlayerData.name + "';";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static void addNewPlayer() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/databases/players.db");
            stmt = c.createStatement();
            String sql = "INSERT INTO players (name,wasd,space,debug,winDesc,lvlFinished) " +
                    "VALUES " +
                    "('" +PlayerData.name+"', " +
                    (PlayerData.opt.getWASD() ? (1) : (0)) + ", " +
                    (PlayerData.opt.getSpace() ? (1) : (0))+ ", " +
                    (PlayerData.opt.getDebug() ? (1) : (0)) + ", " +
                    (PlayerData.opt.getWinDesc() ? (1) : (0))+ ", '" +
                    Arrays.toString(PlayerData.levelsFinished) + "' );";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static void create() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/databases/players.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS players " +
                    "(name TEXT NOT NULL, " +
                    " wasd BOOL NOT NULL, " +
                    " space BOOL NOT NULL, " +
                    " debug BOOL NOT NULL, " +
                    " winDesc BOOL NOT NULL," +
                    " lvlFinished TEXT NOT NULL)";
            stmt.execute(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static boolean playerExists(String name) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/databases/players.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT count(*) FROM players WHERE name = '" + PlayerData.name + "';");
            int cnt = 0;
            if(rs.next())
                cnt = rs.getInt(1);
            rs.close();
            stmt.close();
            c.close();
            return cnt > 0;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return false;
    }
}
