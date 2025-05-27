package PaooGame.Database;
import PaooGame.Levels.Level;

import java.sql.*;

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
            ResultSet rs = stmt.executeQuery( "SELECT * FROM " + name + ";" );
            PlayerData.name = name;
            PlayerData.levelsFinished = new boolean[3];
            while(rs.next()) {
                PlayerData.opt.setWASD(rs.getBoolean("wasd"));
                PlayerData.opt.setSpace(rs.getBoolean("space"));
                PlayerData.opt.setDebug(rs.getBoolean("debug"));
                PlayerData.opt.setWinDesc(rs.getBoolean("winDesc"));
                String rowString = rs.getString("levelsFinished");
                String[] rowVector = rowString.split(",");
                for(int j = 0; j < 3; ++j)
                    PlayerData.levelsFinished[j] = Boolean.parseBoolean(rowVector[j]);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
}
