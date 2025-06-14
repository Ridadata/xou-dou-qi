package game;



import java.util.Scanner;



public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Welcome to Xou Dou Qi ===");

        // Login or Register Player 1
        System.out.println("\n--- Player 1 Login/Register ---");
        Player p1 = Login.loginOrRegister(scanner);

        // Login or Register Player 2
        System.out.println("\n--- Player 2 Login/Register ---");
        Player p2 = Login.loginOrRegister(scanner);

        // Start the game
        Game game = new Game(p1, p2);
        game.start();

        // Get the winner
        Player winner = game.getWinner(); // should be set inside Game when someone wins or null if draw

        // Save match result
        Login.saveMatchResult(p1, p2, winner);

        // Show player stats
        System.out.print("\nShow stats for which player? (enter username or 'no'): ");
        String userInput = scanner.nextLine().trim();
        if (!userInput.equalsIgnoreCase("no")) {
            Login.showStats(userInput);
        }

        // Show full match history
        System.out.print("\nShow full match history? (yes/no): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
            Login.showMatchHistory();
        }

        System.out.println("\nThanks for playing!");
    }
}

