package game;


	public abstract class Piece {

	    protected String name;
	    protected int rank;         // 1 (Rat) to 8 (Elephant)
	    protected int x, y;         // Position on board
	    protected boolean isBlue;   // true if player 1, false if player 2
	    protected boolean inTrap = false;

	    public Piece(String name, int rank, int x, int y, boolean isBlue) {
	        this.name = name;
	        this.rank = rank;
	        this.x = x;
	        this.y = y;
	        this.isBlue = isBlue;
	    }

	    public abstract boolean canMoveTo(int targetX, int targetY, Board board);
	    public abstract boolean canCapture(Piece target, Board board);

	    // Getters and Setters
	    public int getX() { return x; }
	    public int getY() { return y; }
	    public void setPosition(int x, int y) { this.x = x; this.y = y; }
	    public String getName() { return name; }
	    public int getRank() { return rank; }
	    public boolean isBlue() { return isBlue; }

	    public void setInTrap(boolean inTrap) {
	        this.inTrap = inTrap;
	    }

	    public boolean isInTrap() {
	        return inTrap;
	    }

}

