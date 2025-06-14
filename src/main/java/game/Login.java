package game;

import java.util.Scanner;
import java.sql.*;

public class Login {
    private static DatabaseManager db = new DatabaseManager();

    public static Player loginOrRegister(Scanner scanner) {
        while (true) {
            System.out.println("[1] Login\n[2] Register\nChoice: ");
            String choice = scanner.nextLine();

            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (choice.equals("1")) {
                Player p = db.login(username, password);
                if (p != null) {
                    System.out.println("Login successful.");
                    return p;
                } else {
                    System.out.println("Login failed. Try again.");
                }

            } else if (choice.equals("2")) {
                boolean registered = db.registerPlayer(username, password);
                if (registered) {
                    System.out.println("Registration successful.");
                    return new Player(username, password);
                } else {
                    System.out.println("Username already exists.");
                }
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    public static void saveMatchResult(Player p1, Player p2, Player winner) {
        db.saveMatch(p1.getUsername(), p2.getUsername(),
                winner == null ? "draw" : winner.getUsername());
    }

    public static void showStats(String username) {
        db.printPlayerStats(username);
    }
    
    
    public static void showMatchHistory() {
        String sql = "SELECT * FROM matches ORDER BY timestamp DESC";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:xou_dou_qi.db");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n=== Match History ===");
            while (rs.next()) {
                System.out.println(rs.getString("timestamp") + ": " +
                    rs.getString("player1") + " vs " +
                    rs.getString("player2") + " → Winner: " +
                    rs.getString("winner"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
