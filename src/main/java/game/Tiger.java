package game;



public class Tiger extends Piece {

	
    public Tiger(int x, int y, boolean isBlue) {
        super("Tiger", 6, x, y, isBlue);
    }

    @Override
    public boolean canMoveTo(int targetX, int targetY, Board board) {
        if (board.isAdjacent(x, y, targetX, targetY)) return true;
        return board.canJumpOverRiver(x, y, targetX, targetY); // custom method
    }

    @Override
    public boolean canCapture(Piece target, Board board) {
        return target != null && target.isBlue() != this.isBlue && target.getRank() <= this.rank;
    }

}
