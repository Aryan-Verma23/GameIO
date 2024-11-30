import java.util.Scanner;
public class Powered {



        private static char[][] board = new char[3][3];
        private static char currentPlayer = 'X';

        public static void main(String[] args) {
            initializeBoard();
            printBoard();

            while (true) {
                if (currentPlayer == 'X') {

                    playMove();
                } else {

                    System.out.println("Computer's turn:");
                    int[] move = getBestMove();
                    board[move[0]][move[1]] = currentPlayer;
                }

                printBoard();

                if (checkWinner()) {
                    System.out.println("Player " + currentPlayer + " wins!");
                    break;
                } else if (isBoardFull()) {
                    System.out.println("It's a draw!");
                    break;
                }

                switchPlayer();
            }
        }

        private static void initializeBoard() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    board[i][j] = '-';
                }
            }
        }

        private static void printBoard() {
            System.out.println("-------------");
            for (int i = 0; i < 3; i++) {
                System.out.print("| ");
                for (int j = 0; j < 3; j++) {
                    System.out.print(board[i][j] + " | ");
                }
                System.out.println();
                System.out.println("-------------");
            }
        }

        private static void playMove() {
            Scanner scanner = new Scanner(System.in);
            int row, col;
            while (true) {
                System.out.println("Player " + currentPlayer + ", enter your move (row and column):");
                row = scanner.nextInt();
                col = scanner.nextInt();

                if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == '-') {
                    board[row][col] = currentPlayer;
                    break;
                } else {
                    System.out.println("Invalid move! Try again.");
                }
            }
        }

        private static boolean checkWinner() {
            for (int i = 0; i < 3; i++) {
                if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) {
                    return true;
                }
            }
            for (int j = 0; j < 3; j++) {
                if (board[0][j] == currentPlayer && board[1][j] == currentPlayer && board[2][j] == currentPlayer) {
                    return true;
                }
            }
            if ((board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
                    (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer)) {
                return true;
            }
            return false;
        }

        private static boolean isBoardFull() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        return false;
                    }
                }
            }
            return true;
        }

        private static void switchPlayer() {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }

        private static int[] getBestMove() {
            int bestScore = Integer.MIN_VALUE;
            int[] bestMove = new int[2];

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = currentPlayer;
                        int score = minimax(false);
                        board[i][j] = '-';
                        if (score > bestScore) {
                            bestScore = score;
                            bestMove[0] = i;
                            bestMove[1] = j;
                        }
                    }
                }
            }
            return bestMove;
        }

        private static int minimax(boolean isMaximizing) {
            if (checkWinner()) return isMaximizing ? -1 : 1;
            if (isBoardFull()) return 0;

            int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = isMaximizing ? 'O' : 'X';
                        int score = minimax(!isMaximizing);
                        board[i][j] = '-';
                        bestScore = isMaximizing
                                ? Math.max(score, bestScore)
                                : Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

