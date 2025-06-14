package game;



public class Elephant extends Piece {
	public Elephant(int x, int y, boolean isBlue) {
        super("Elephant", 8, x, y, isBlue);
    }

    @Override
    public boolean canMoveTo(int targetX, int targetY, Board board) {
        return board.isAdjacent(x, y, targetX, targetY) && !board.isWater(targetX, targetY);
    }

    @Override
    public boolean canCapture(Piece target, Board board) {
        if (target == null || target.isBlue() == this.isBlue) return false;

        // Cannot capture Rat
        return !(target instanceof Rat) && target.getRank() <= this.rank;
    }
}
