package game;



public class Rat extends Piece {
	public Rat(int x, int y, boolean isBlue) {
        super("Rat", 1, x, y, isBlue);
    }

    @Override
    public boolean canMoveTo(int targetX, int targetY, Board board) {
        // Allow 1-step vertical/horizontal move (and water allowed)
        return board.isAdjacent(x, y, targetX, targetY);
    }

    @Override
    public boolean canCapture(Piece target, Board board) {
        if (target == null || target.isBlue() == this.isBlue) return false;

        // Cannot capture directly from water to land or vice versa
        if (board.isWater(x, y) && !board.isWater(target.getX(), target.getY())) return false;

        // Special rule: Rat can capture Elephant
        return target.getRank() <= this.rank || target instanceof Elephant;
    }
}