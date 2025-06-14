package game;

public class Player {

    private String username;
    private String password; // Can be hashed for security
    private int wins;
    private int losses;
    private int draws;

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
    }

    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getWins() { return wins; }
    public int getLosses() { return losses; }
    public int getDraws() { return draws; }

    // Setters (used by DatabaseManager)
    public void setWins(int wins) { this.wins = wins; }
    public void setLosses(int losses) { this.losses = losses; }
    public void setDraws(int draws) { this.draws = draws; }

    // In-game updates
    public void addWin() { wins++; }
    public void addLoss() { losses++; }
    public void addDraw() { draws++; }
}

