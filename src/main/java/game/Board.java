package game;



public class Board {
	
    private Piece[][] grid = new Piece[9][7];

    // Define special zones
    private final int[][] waterCells = {
        {3,1}, {3,2}, {4,1}, {4,2}, {5,1}, {5,2},
        {3,4}, {3,5}, {4,4}, {4,5}, {5,4}, {5,5}
    };

    private final int[][] blueTraps = { {0,2}, {0,4}, {1,3} };
    private final int[][] redTraps =  { {8,2}, {8,4}, {7,3} };

    private final int[] blueDen = {0,3};
    private final int[] redDen =  {8,3};

    // === PIECE HANDLING ===

    public Piece getPieceAt(int x, int y) {
        return grid[x][y];
    }

    public void placePiece(Piece piece) {
        grid[piece.getX()][piece.getY()] = piece;
    }

    public void movePiece(int fromX, int fromY, int toX, int toY) {
        Piece movingPiece = grid[fromX][fromY];
        grid[toX][toY] = movingPiece;
        movingPiece.setPosition(toX, toY);
        grid[fromX][fromY] = null;
    }

    public void removePiece(int x, int y) {
        grid[x][y] = null;
    }

    // === SPECIAL ZONE CHECKS ===

    public boolean isWater(int x, int y) {
        for (int[] cell : waterCells) {
            if (cell[0] == x && cell[1] == y) return true;
        }
        return false;
    }

    public boolean isTrap(int x, int y, boolean isBlue) {
        int[][] traps = isBlue ? redTraps : blueTraps;
        for (int[] cell : traps) {
            if (cell[0] == x && cell[1] == y) return true;
        }
        return false;
    }

    public boolean isDen(int x, int y, boolean isBlue) {
        int[] den = isBlue ? redDen : blueDen;
        return den[0] == x && den[1] == y;
    }

    public boolean isOwnDen(int x, int y, boolean isBlue) {
        int[] ownDen = isBlue ? blueDen : redDen;
        return ownDen[0] == x && ownDen[1] == y;
    }

    // === HELPER METHODS ===

    public boolean isAdjacent(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x1 - x2);
        int dy = Math.abs(y1 - y2);
        return (dx + dy) == 1;
    }

    public boolean canJumpOverRiver(int fromX, int fromY, int toX, int toY) {
        // Lion/Tiger jumps: same row or column, with river between
        if (fromX == toX) {
            if ((fromY < toY && clearRiverHorizontal(fromX, fromY + 1, toY)) ||
                (fromY > toY && clearRiverHorizontal(fromX, toY + 1, fromY))) {
                return true;
            }
        } else if (fromY == toY) {
            if ((fromX < toX && clearRiverVertical(fromY, fromX + 1, toX)) ||
                (fromX > toX && clearRiverVertical(fromY, toX + 1, fromX))) {
                return true;
            }
        }
        return false;
    }

    private boolean clearRiverHorizontal(int row, int startCol, int endCol) {
        for (int y = startCol; y < endCol; y++) {
            if (!isWater(row, y) || getPieceAt(row, y) instanceof Rat) return false;
        }
        return true;
    }

    private boolean clearRiverVertical(int col, int startRow, int endRow) {
        for (int x = startRow; x < endRow; x++) {
            if (!isWater(x, col) || getPieceAt(x, col) instanceof Rat) return false;
        }
        return true;
    }
    public boolean isOpponentDen(int x, int y, boolean isBlue) {
        int[] den = isBlue ? redDen : blueDen;
        return den[0] == x && den[1] == y;
    }

}
