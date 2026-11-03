
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
 * @author Tejas Rajulapudi
 * @version November 3 2026
 */
void main(){
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
}
 /**
  * This method is used to print the board at any given time.
  * When you use this method, it prints the columns 1-7, then prints the rows with the pieces.
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
