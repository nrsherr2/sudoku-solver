package board;

/**
 * Class that stores information relating to a regular grid used in Sudoku
 * 
 * @author Nicholas Sherrill
 *
 */
public class Board {

    Square[][] board;
    int numSpacesFilled;

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
        numSpacesFilled = 0;
    }

    /**
     * Checks a specific spot in the grid to see if a value can be placed there
     * 
     * @param row
     *            the row
     * @param col
     *            the column
     * @param val
     *            the value to check
     * @return if that block has that number in its list of availabilities
     */
    public boolean checkAvailable(int row, int col, int val) {
        return board[row][col].canUse(val);
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
            numSpacesFilled++;
        }
    }

    /**
     * Tells the user how close they are to the end
     * 
     * @return the number of spaces filled
     */
    public int getNumSpacesFilled() {
        return numSpacesFilled;
    }

    public void setNumSpacesFilled(int numSpacesFilled) {
        this.numSpacesFilled = numSpacesFilled;
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

    /**
     * Tells the user exactly what can be placed in each spot
     * 
     * @return a string containing what can be placed in each spot
     */
    public String getAvailabilites() {
        String s = "";
        for (int i = 0; i < 9; i++) {
            for (int r = 0; r < 3; r++) {
                for (int j = 0; j < 9; j++) {
                    s = s + "[";
                    for (int checkNum = r * 3; checkNum < (r * 3)
                            + 3; checkNum++) {
                        if (board[i][j].canUse(checkNum + 1)) {
                            s = s + (checkNum + 1);
                        } else {
                            s = s + " ";
                        }
                    }
                    s = s + "]";
                    if (j % 3 == 2) {
                        s = s + "+";
                    }
                }
                s = s + "\n";
            }

            if (i % 3 == 2) {
                for (int t = 0; t < 48; t++) {
                    s = s + "+";
                }
                s = s + "\n";
            } else {
                for (int q = 0; q < 48; q++) {
                    s = s + "-";
                }
                s = s + "\n";
            }
        }
        return s;
    }

}
