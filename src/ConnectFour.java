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
    // We use 6 rows and 7 columns to match a real Connect Four board.
    String[][] board = new String[6][7];
    // We use "." to mark an empty spot before any piece has been dropped.
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[i].length; j++) {
            board[i][j] = ".";
        }
    }
    printTheBoard(board);
    int player1 = 1;
    int player2 = 2;

    int currentPlayer = player1;

    String symbol;


    //Player 1 is always X and player 2 is always O
    // Depending on who the current player is we can change symbol to later drop the piece in the board.
    if (currentPlayer == 1) {
        symbol = "X";
    } else {
        symbol = "O";

    }
    // We keep asking the user until the column is valid. We do this
    // ...so an invalid column like a column
    // We always start the col at -1, so that the while loop always runs at least once.
    int col = -1;
    // parseInt throws an exception on any letters or empty input, so we catch it later.
    // We do this so that the games does not throw an exception and crash the game.
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
