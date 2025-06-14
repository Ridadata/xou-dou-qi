package game;

import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:xou_dou_qi.db";

    public DatabaseManager() {
        createTables();
    }

    private void createTables() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            String playerTable = """
                CREATE TABLE IF NOT EXISTS players (
                    username TEXT PRIMARY KEY,
                    password TEXT NOT NULL,
                    wins INTEGER DEFAULT 0,
                    losses INTEGER DEFAULT 0,
                    draws INTEGER DEFAULT 0
                );
            """;

            String historyTable = """
                CREATE TABLE IF NOT EXISTS matches (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    player1 TEXT,
                    player2 TEXT,
                    winner TEXT,
                    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
                );
            """;

            stmt.execute(playerTable);
            stmt.execute(historyTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean registerPlayer(String username, String password) {
        String checkSql = "SELECT COUNT(*) FROM players WHERE username = ?";
        String insertSql = "INSERT INTO players (username, password) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL)) {

            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, username);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("⚠️ Ce nom d'utilisateur existe déjà.");
                    return false;
                }
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, username);
                insertStmt.setString(2, password);
                insertStmt.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            System.out.println("❌ Erreur SQL: " + e.getMessage());
            return false;
        }
    }


    public Player login(String username, String password) {
        String sql = "SELECT * FROM players WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Player p = new Player(username, password);
                p.setWins(rs.getInt("wins"));
                p.setLosses(rs.getInt("losses"));
                p.setDraws(rs.getInt("draws"));
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveMatch(String player1, String player2, String winner) {
        String insert = "INSERT INTO matches (player1, player2, winner) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(insert)) {
            pstmt.setString(1, player1);
            pstmt.setString(2, player2);
            pstmt.setString(3, winner != null ? winner : "draw");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // update stats
        updateStats(player1, player2, winner);
    }

    private void updateStats(String player1, String player2, String winner) {
        String updateWin  = "UPDATE players SET wins = wins + 1 WHERE username = ?";
        String updateLoss = "UPDATE players SET losses = losses + 1 WHERE username = ?";
        String updateDraw = "UPDATE players SET draws = draws + 1 WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (winner == null || winner.equals("draw")) {
                try (PreparedStatement stmt1 = conn.prepareStatement(updateDraw);
                     PreparedStatement stmt2 = conn.prepareStatement(updateDraw)) {
                    stmt1.setString(1, player1);
                    stmt2.setString(1, player2);
                    stmt1.executeUpdate();
                    stmt2.executeUpdate();
                }
            } else {
                try (PreparedStatement winStmt = conn.prepareStatement(updateWin);
                     PreparedStatement lossStmt = conn.prepareStatement(updateLoss)) {

                    winStmt.setString(1, winner);
                    lossStmt.setString(1, winner.equals(player1) ? player2 : player1);
                    winStmt.executeUpdate();
                    lossStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    


    public void printPlayerStats(String username) {
        String sql = "SELECT * FROM players WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Stats for " + username + ":");
                System.out.println("Wins: " + rs.getInt("wins"));
                System.out.println("Losses: " + rs.getInt("losses"));
                System.out.println("Draws: " + rs.getInt("draws"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}