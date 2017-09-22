package board;

/**
 * Class that stores information relating to a regular grid used in Sudoku
 * 
 * @author Nicholas Sherrill
 *
 */
public class Board {

    Square[][] board;

    /**
     * Creates a new board and initializes all of the squares
     */
    public Board() {
        board = new Square[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new Square();
            }
        }
    }

    /**
     * Puts a number in the specified location. If the number is successfully
     * placed in the location, the grid will then remove the possibility of that
     * number appearing in the row, column, or square.
     * 
     * @param row
     *            is the row of the grid the number will be placed in. This
     *            number will be formatted 1-9.
     * @param col
     *            is the column of the grid the number will be placed in. This
     *            number will be formatted 1-9.
     * @param val
     *            the value that will be placed in the location.
     */
    public void setNum(int row, int col, int val) {
        if (row < 1 || row > 9 || col < 1 || col > 9 || val < 1 || val > 9) {
            throw new IllegalArgumentException(
                    "Invalid number in the parameters of setnum");
        } else if (board[row - 1][col - 1].setVal(val)) {
            setBlockFalse(row, col, val);
            setRowFalse(row, val);
            setColFalse(col, val);
        }
    }

    /**
     * Tells all squares in a block that they can't use this number
     * 
     * @param row
     *            the row the number was placed in that they can't use.
     *            Formatted 1-9.
     * @param col
     *            the column the number was placed in that they can't use.
     *            Formatted 1-9.
     * @param val
     *            the value that was set in the square.
     */
    private void setBlockFalse(int row, int col, int val) {
        int startingRow = ((row - 1) / 3) * 3;
        int startingCol = ((col - 1) / 3) * 3;
        for (int i = startingRow; i < startingRow + 3; i++) {
            for (int j = startingCol; j < startingCol + 3; j++) {
                board[i][j].setFalse(val);
            }
        }

    }

    /**
     * Tells all the numbers in a row they can't use a value
     * 
     * @param row
     *            the row that will be affected
     * @param val
     *            the value that will not be available in that row
     */
    private void setRowFalse(int row, int val) {
        for (int i = 0; i < 9; i++) {
            board[row - 1][i].setFalse(val);
        }

    }

    /**
     * Tells all the numbers in a column they can't use a value
     * 
     * @param col
     *            the column that will be affected
     * @param val
     *            the value that will not be available in the column
     */
    private void setColFalse(int col, int val) {
        for (int i = 0; i < 9; i++) {
            board[i][col - 1].setFalse(val);
        }
    }

    /**
     * Creates a representation of a grid like you see in the regular seppuku
     * puzzles
     * 
     * @return the grid of the puzzle
     */
    public String getGrid() {
        String s = "";
        for (int i = 0; i < 9; i++) {
            // if (i % 3 == 0) {
            // for (int k = 0; k < 30; k++) {
            // s = s + "-";
            // }
            // s = s + "\n";
            // }
            for (int j = 0; j < 9; j++) {
                // if (j % 3 == 0) {
                // s = s + "|";
                // }
                s = s + "[";
                if (board[i][j].getVal() == 0) {
                    s = s + " ";
                } else {
                    s = s + board[i][j].getVal();
                }
                s = s + "]";
            }
            s = s + "\n";
        }
        return s;
    }

    public String getAvailabilites() {
        String s = "";
        for (int i = 0; i < 9; i++) {
            //put some shit in here that can get all of the availabilities
        }
        return s;
    }

}
