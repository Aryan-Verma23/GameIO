import java.util.Scanner;

public class TicTacToe {
    private static char[][] board = new char[3][3];
    private static char currentPlayer = 'X';

    public static void main(String[] args) {
        initializeBoard();
        printBoard();

        while (true) {
            playMove();
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
//board initialisation
    private static void initializeBoard() {
        //represents iteration in a row
        for (int i = 0; i < 3; i++) {
            // represents iteration in a coloumn
            for (int j = 0; j < 3; j++) {
             //this represents an empty cell
                board[i][j] = '-';
            }
        }
    }

    private static void printBoard()
    {
       //prints rows in board
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
          //to start new row
            System.out.println();
            System.out.println("-------------");
        }
    }

    private static void playMove()
    {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Player " + currentPlayer + ", enter your move (row and column):");
        int row = scanner.nextInt();
        int col = scanner.nextInt();


        if (row < 0 || row >= 3 || col < 0 || col >= 3 || board[row][col] != '-') {
           //same
            System.out.println("Invalid move! Try again.");
     
            playMove();
        } else {
            board[row][col] = currentPlayer;
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

    private static boolean isBoardFull()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (board[i][j] == '-')
                {
                    return false;
                }
            }
        }
        return true;
    }

    private static void switchPlayer() {
        if (currentPlayer == 'X') {
            currentPlayer = 'O';
        } else {
            currentPlayer = 'X';
        }
    }
}

