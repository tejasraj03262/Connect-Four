/**
 * This code outputs a two-person game of Connect Four.
 * Players choose a column and the piece drops down
 * to the lowest empty row in that column. After one person goes,
 * the next person goes.
 * After each turn, it checks for a win (four in a row).
 * The code checks horizontally, vertically, and diagonally to confirm a win.
 * It declares a draw if there is no winner and the board is full.
 * After each game, it displays the win count for each player
 * and asks if they want to keep playing or stop.
 *
 * @author Tejas Rajulapudi
 * @version November 3 2026
 */
void main() {
    IO.println("Welcome to Connect Four!");


    int player1 = 1;
    int player2 = 2;

    // The outer loop represents one full game each time.
    // The game will keep going until the players say they do not want to play again.
    while (true) {
        // We use 6 rows and 7 columns to match a real Connect Four board.
        String[][] board = new String[6][7];
        // We create the board here so that it becomes empty at the very start at every game.
        // We use "." to mark an empty spot before any piece has been dropped.
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = ".";
            }
        }
        printTheBoard(board);
        // We reset the current player here so that the Player 1 always starts each game.
        int currentPlayer = player1;


        String symbol;
        // The inner loop represents one turn at a time. It will keep going until someone wins or when the board is full.
        while(true) {
            //Player 1 is always X and player 2 is always O
            // Depending on who the current player is we can change symbol to later drop the piece in the board.
            if (currentPlayer == 1) {
                symbol = "X";
            } else {
                symbol = "O";

            }

            // We keep asking the user until the column is valid. We do this
            // so an invalid column never gets placed on the board.
            // We always start the col at -1, so that the while loop always runs at least once.
            int col = -1;
            // parseInt throws an exception on any letters or empty input, so we catch it later.
            // We do this so that the game does not throw an exception and crash the game.
            // We keep asking until we get a valid column.
            while (!isValidColPick(board, col)) {
                try {
                    col = Integer.parseInt(IO.readln("Player " + currentPlayer + " (" + symbol + ") " + "- Choose a column:\n"));
                    if (!isValidColPick(board, col)) {

                        IO.println("Invalid Column");
                    }
                } catch (NumberFormatException e) {
                    IO.println("That is not a number");
                }
            }
            // We made sure to check that the column is valid, so now we can drop the piece into the board and then print it.
            whichRow(board, col, symbol);
            printTheBoard(board);

            // We check for a win before we switch players, so that we can declare the current player as the winner.
            // We use breaks and not return because putting a return will end the whole program.
            // A break will only leave the inner loop so we can still keep playing.
            if (checkWin(board)) {
                IO.println("Player " + currentPlayer + " Wins!");
                break;
            } else if (isBoardFull(board)) {
                IO.println("Game Ends in a Draw!");
                break;
            }
            // After every turn the current player switches like a real game.
            if (currentPlayer == player1) {
                currentPlayer = player2;
            } else {
                currentPlayer = player1;
            }
        }



        // The game is over, so we ask if they want to play again.
        // Anything other than "y" case ignored ends thr program.
        String answer = IO.readln("Play again? (y/n)\n");
        if(!answer.equalsIgnoreCase("y")){
            break;
        }
    }
    IO.println("Thanks for playing!");
}

/**
 * This method is used to print the board at any given time.
 * When you use this method, it prints the columns 1-7, then prints the rows with the pieces.
 *
 * @param board, the 6 by 7 2D Array, which holds the game board.
 */
void printTheBoard(String[][] board) {
    // We start the column numbers at 1 and not at 0 because users
    // type the column numbers starting from 1.
    String top = "";
    for (int i = 1; i <= 7; i++) {
        top += i;
        // No space after the last number so the row does not end with a trailing space.
        if (i < 7) {
            top += " ";
        }
    }

    IO.println(top);
    for (int i = 0; i < 6; i++) {
        String line = "";
        for (int j = 0; j < 7; j++) {
            line += board[i][j];
            // We put spaces after each piece except the very last piece
            // so the spaces will line up with the column number.
            if (j < 6) {
                line += " ";
            }
        }
        IO.println(line);
    }
}

/**
 * This method checks if the column the player picked is actually valid.
 * It makes sure to check if a column is between 1 and 7 and cannot be full.
 * This mirrors a real life Connect Four game as it has to be between column 1 and 7,
 * you cannot add a piece on an already full column.
 *
 * @param board, the 6 by 7 2D Array, which holds the game board.
 * @param c,     the column the player choose.
 * @return true if the column picked is valid, and false if not.
 */
boolean isValidColPick(String[][] board, int c) {
    if (c < 1 || c > 7) {
        return false;
    }
    // Players will type a number between 1-7, but we have to subtract 1 because the indexes are from 0-6.
    // We also only have to check the top row because the pieces fall down and have to stack from
    // bottom to top. Essentially if the top spot is empty, we can still insert a piece in the column.
    return board[0][c - 1].equals(".");

}

/**
 * This method drops the piece into the column the player choose.
 * It moves down until the spot below is taken.
 *
 * @param board, the 6 by 7 2D Array, which holds the game board.
 * @param c      the column the player choose.
 * @return the row in which the piece landed.
 */

int whichRow(String[][] board, int c, String symbol) {
    int row = 0;
    // Players type numbers from 1-7 but indexes are 0-6, so we have to subtract 1.
    int col = c - 1;
    // We start at the top and move all the way down until the next spot is taken.
    // We do row<5 since our check is row+1. This code mimics a real piece falling.
    while (row < 5 && !(board[row + 1][col].equals("X") || board[row + 1][col].equals("O"))) {
        row++;
    }
    board[row][col] = symbol;
    return row;
}

/**
 * This checks the board for a ConnectFour or four pieces of the same symbol.
 * It checks the horizontal, vertical and diagonal direction to check for a win.
 *
 * @param board, the 6 by 7 2D Array, which holds the game board.
 * @return true if the game has a symbol four in a row, false if not.
 */

boolean checkWin(String[][] board) {

    for (int i = 0; i < 6; i++) {
        // This is the horizontal check. j only goes to 3 because the check uses j + 3, and we have to stay inside the 7 columns.
        // We check each row if 4 pieces in a row are the same symbol. If not, we move on to the next row.
        for (int j = 0; j < 4; j++) {
            if (board[i][j].equals("X") && board[i][j + 1].equals("X")
                    && board[i][j + 2].equals("X") && board[i][j + 3].equals("X")) {
                return true;
            } else if (board[i][j].equals("O") && board[i][j + 1].equals("O")
                    && board[i][j + 2].equals("O") && board[i][j + 3].equals("O")) {
                return true;
            }


        }
    }

    for (int i = 0; i < 7; i++) {
        // This is the Vertical check. j only goes to 2 because the check uses j + 3, and we have to stay inside the 6 rows.
        // We check each column if 4 pieces in a row are the same symbol. If not, we move on to the next column.
        for (int j = 0; j < 3; j++) {
            if (board[j][i].equals("X") && board[j + 1][i].equals("X")
                    && board[j + 2][i].equals("X") && board[j + 3][i].equals("X")) {
                return true;
            } else if (board[j][i].equals("O") && board[j + 1][i].equals("O")
                    && board[j + 2][i].equals("O") && board[j + 3][i].equals("O")) {
                return true;
            }


        }
    }
    for (int i = 0; i < 3; i++) {
        // This is the diagonal piece going down-right. Both the row and column go up by 1 each step.
        // We stop i before 3 and j before 4 because our checks are i+3 and j+3, and so that we don't go over the board.
        // We check each row and column to see if 4 pieces in a row are the same symbol. If not, we move on to the next row and column or diagonal.
        for (int j = 0; j < 4; j++) {
            if (board[i][j].equals("X") && board[i + 1][j + 1].equals("X")
                    && board[i + 2][j + 2].equals("X") && board[i + 3][j + 3].equals("X")) {
                return true;
            } else if (board[i][j].equals("O") && board[i + 1][j + 1].equals("O")
                    && board[i + 2][j + 2].equals("O") && board[i + 3][j + 3].equals("O")) {
                return true;
            }


        }
    }

    for (int i = 0; i < 3; i++) {
        // This is the diagonal check going down-left. j starts at 6 because the direction of the check moves left as we go down.
// We stop i before 3 and j at 3 because our checks are i+3 and j-3, and so that we don't go over the board.
// We check each row and column to see if 4 pieces in a row are the same symbol. If not, we move on to the next row and column or diagonal.
        for (int j = 6; j > 2; j--) {
            if (board[i][j].equals("X") && board[i + 1][j - 1].equals("X")
                    && board[i + 2][j - 2].equals("X") && board[i + 3][j - 3].equals("X")) {
                return true;
            } else if (board[i][j].equals("O") && board[i + 1][j - 1].equals("O")
                    && board[i + 2][j - 2].equals("O") && board[i + 3][j - 3].equals("O")) {
                return true;
            }


        }
    }
    return false;


}
/**
 * This checks if every spot on the board is filled with the symbols or not.
 * This function is used to tell if there is a draw or not.
 * @param board, the 6 by 7 2D Array, which holds the game board.
 * @return true if there is no empty spots left on the board, if not return false.
 */
boolean isBoardFull(String[][] board) {

    int count = 0;
    for (int i = 0; i < 6; i++) {
        for (int j = 0; j < 7; j++) {
            if (board[i][j].equals(".")) {
                // If there is an empty spot we add one to count.
                count++;
                // We only need one empty spot in a row to know the board isn't full, so we break out of that row and move to the next one.

                break;
            }
        }

    }
    // If there is no empty spot (count is 0), then board is full and we return true. If there is an empty spot, the board is not full, so we return false.
    if (count == 0) {
        return true;
    } else {
        return false;
    }
}




