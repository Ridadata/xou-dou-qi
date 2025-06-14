package game;



public class Wolf extends Piece {
	
    public Wolf(int x, int y, boolean isBlue) {
        super("Wolf", 3, x, y, isBlue);
    }

    @Override
    public boolean canMoveTo(int targetX, int targetY, Board board) {
        return board.isAdjacent(x, y, targetX, targetY) && !board.isWater(targetX, targetY);
    }

    @Override
    public boolean canCapture(Piece target, Board board) {
        if (target == null || target.isBlue() == this.isBlue) return false;
        return target.getRank() <= this.rank;
    }

}

