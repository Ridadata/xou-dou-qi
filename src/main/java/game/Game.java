package game;

import java.util.Scanner;


public class Game {
    private Player player1;
    private Player player2;
    private Board board;
    private boolean isPlayer1Turn;
    private Scanner scanner;

    private Player winner;

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE   = "\u001B[94m";
    public static final String CYAN = "\u001B[36m";
    public static final String YELLOW = "\u001B[33m";
    public static final String GREEN = "\u001B[32m";
    public static final String WHITE = "\u001B[37m";
    public static final String BOLD = "\u001B[1m";

    
    
    
    public Player getWinner() {
        return winner;
    }
    
    
    
    
    
    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = new Board();
        this.isPlayer1Turn = true;
        this.scanner = new Scanner(System.in);

        initializePieces();
    }

    private void initializePieces() {
        // Blue (Player 1)
        board.placePiece(new Elephant(2, 6, true));
        board.placePiece(new Lion(0, 0, true));
        board.placePiece(new Tiger(0, 6, true));
        board.placePiece(new Panther(2, 2, true));
        board.placePiece(new Dog(1, 1, true));
        board.placePiece(new Wolf(2, 4, true));
        board.placePiece(new Cat(1, 5, true));
        board.placePiece(new Rat(2, 0, true));

        // Red (Player 2)
        board.placePiece(new Elephant(6, 0, false));
        board.placePiece(new Lion(8, 6, false));
        board.placePiece(new Tiger(8, 0, false));
        board.placePiece(new Panther(6, 4, false));
        board.placePiece(new Dog(7, 5, false));
        board.placePiece(new Wolf(6, 2, false));
        board.placePiece(new Cat(7, 1, false));
        board.placePiece(new Rat(6, 6, false));
    }

    public void start() {
        System.out.println("Welcome to Xou Dou Qi!");
        System.out.println("Commands: move <Initial> <direction> | show | help | exit");

        while (true) {
            displayBoard();
            Player currentPlayer = isPlayer1Turn ? player1 : player2;
            boolean isBlue = isPlayer1Turn;
            System.out.print(currentPlayer.getUsername() + " > ");

            String input = scanner.nextLine().trim();
            String[] parts = input.split("\\s+");

            if (parts[0].equalsIgnoreCase("exit")) {
                System.out.println("Exiting game...");
                break;
            } else if (parts[0].equalsIgnoreCase("help")) {
                printHelp();
                continue;
            } else if (parts[0].equalsIgnoreCase("show")) {
                displayBoard();
                continue;
            } else if (parts[0].equalsIgnoreCase("move") && parts.length == 3) {
                char pieceInitial = parts[1].toUpperCase().charAt(0);
                String dirString = parts[2].toLowerCase();

                Direction direction = switch (dirString) {
                    case "f" -> Direction.FORWARD;
                    case "b" -> Direction.BACKWARD;
                    case "l" -> Direction.LEFT;
                    case "r" -> Direction.RIGHT;
                    default -> null;
                };

                if (direction == null) {
                    System.out.println("Invalid direction.");
                    continue;
                }

                Piece piece = findPieceByInitial(pieceInitial, isBlue);
                if (piece == null) {
                    System.out.println("No such piece found for you.");
                    continue;
                }

                int[] offset = getDirectionOffset(direction, isBlue);
                int toX = piece.getX() + offset[0];
                int toY = piece.getY() + offset[1];

                if (!isValidPosition(toX, toY)) {
                    System.out.println("Move out of bounds.");
                    continue;
                }

                if (!piece.canMoveTo(toX, toY, board)) {
                    System.out.println("This piece can't move that way.");
                    continue;
                }

                Piece target = board.getPieceAt(toX, toY);
                if (target != null && !piece.canCapture(target, board)) {
                    System.out.println("Cannot capture that piece.");
                    continue;
                }

                if (board.isOwnDen(toX, toY, isBlue)) {
                    System.out.println("Cannot enter your own den.");
                    continue;
                }

                if (board.isTrap(toX, toY, !isBlue)) {
                    piece.setInTrap(true);
                } else {
                    piece.setInTrap(false);
                }

                board.movePiece(piece.getX(), piece.getY(), toX, toY);
                System.out.println("Moved " + piece.getName() + " to (" + toX + "," + toY + ")");

                if (board.isOpponentDen(toX, toY, isBlue)) {
                    System.out.println(currentPlayer.getUsername() + " wins by reaching the opponent's den!");
                    winner = currentPlayer;
                    break;
                }

                isPlayer1Turn = !isPlayer1Turn;
            } else {
                System.out.println("Invalid command. Type 'help' for list of commands.");
            }
        }
    }

    private void printHelp() {
        System.out.println("""
        Available Commands:
        move <Letter> <direction> - Move a piece (e.g. move R forward)
        show                      - Display board
        help                      - Show help
        exit                      - Exit game

        Directions:use abrevation of forward(f), backward(b), left(l), right(r)
        Piece Initials: R (Rat), C (Cat), D (Dog), W (Wolf), P (Panther), T (Tiger), L (Lion), E (Elephant)
        """);
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < 9 && y >= 0 && y < 7;
    }

    private Piece findPieceByInitial(char initial, boolean isBlue) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 7; y++) {
                Piece p = board.getPieceAt(x, y);
                if (p != null && p.isBlue() == isBlue && p.getName().toUpperCase().charAt(0) == initial) {
                    return p;
                }
            }
        }
        return null;
    }

    private int[] getDirectionOffset(Direction direction, boolean isBlue) {
        return switch (direction) {
            case FORWARD -> isBlue ? new int[]{1, 0} : new int[]{-1, 0};
            case BACKWARD -> isBlue ? new int[]{-1, 0} : new int[]{1, 0};
            case LEFT -> new int[]{0, -1};
            case RIGHT -> new int[]{0, 1};
        };
    }

    private void displayBoard() {
        System.out.println("\n" + " ".repeat(20) + BOLD + "🎯 Xou Dou Qi Board 🎯" + RESET);
        String[] colHeaders = {" 0", " 1", " 2", " 3", " 4", " 5", " 6"};

        System.out.print(" ".repeat(18) + "   ");
        for (String header : colHeaders) System.out.print(" " + header + " ");
        System.out.println();

        System.out.println(" ".repeat(18) + "  ╔" + "═══╦".repeat(6) + "═══╗");

        for (int x = 0; x < 9; x++) {
            System.out.print(" ".repeat(18) + x + " ║");
            for (int y = 0; y < 7; y++) {
                Piece p = board.getPieceAt(x, y);
                String cell;

                if (p != null) {
                    String color = p.isBlue() ? BLUE : RED;
                    cell = color + p.getName().charAt(0) + RESET;
                } else if (board.isWater(x, y)) {
                    cell = CYAN + "~" + RESET;
                } else if (board.isDen(x, y, true) || board.isDen(x, y, false)) {
                    cell = YELLOW + "D" + RESET;
                } else if (board.isTrap(x, y, true) || board.isTrap(x, y, false)) {
                    cell = GREEN + "T" + RESET;
                } else {
                    cell = ".";
                }

                System.out.print(" " + cell + " ║");
            }

            if (x != 8) {
                System.out.println();
                System.out.println(" ".repeat(18) + "  ╠" + "═══╬".repeat(6) + "═══╣");
            } else {
                System.out.println();
                System.out.println(" ".repeat(18) + "  ╚" + "═══╩".repeat(6) + "═══╝");
            }
        }
    }

}
