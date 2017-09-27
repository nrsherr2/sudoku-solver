package board;

/**
 * Class that contains information you would keep track of in a traditional
 * Sudoku square
 * This information would be the current value or what values can be placed in
 * the square
 * 
 * @author Nicholas Sherrill
 *
 */
public class Square {

    private int val;
    private boolean[] availableNums;

    /**
     * Sets up the square, either filling in the necessary value, or getting all
     * numbers ready for markup
     * 
     * @param value
     *            will be 0 if the space is empty, or a number between 1 and 9
     *            if the space is filled
     */
    public Square() {
        this.val = 0;
        availableNums = new boolean[9];
        for (int i = 0; i < 9; i++) {
            availableNums[i] = true;
        }
    }

    /**
     * tells the user the value of the square
     * 
     * @return the value of the square
     */
    public int getVal() {
        return this.val;
    }

    /**
     * Tells the user if they can put a certain value in the square
     * 
     * @param val
     *            the value to check
     * @return if they can put that value here
     */
    public boolean canUse(int val) {
        if (val >= 1 && val <= 9) {
            return availableNums[val - 1];
        } else {
            throw new IllegalArgumentException("Not a valid number: " + val);
        }
    }

    /**
     * Sets the value as no longer able to be in this spot
     * 
     * @param val
     *            the value that can never be here
     */
    public void setFalse(int val) {
        if (val >= 1 && val <= 9) {
            availableNums[val - 1] = false;
        } else {
            throw new IllegalArgumentException("Can't set this false");
        }
    }

    /**
     * Makes sure it can set the value, then sets the value of this square
     * 
     * @param val
     *            the value to set
     * @return true if the value was successfully set, false if the value could
     *         not be set
     */
    public boolean setVal(int val) {
        if (availableNums[val - 1]) {
            this.val = val;
            for (int i = 0; i < 9; i++) {
                availableNums[i] = false;
            }
            return true;
        } else {
            return false;
        }
    }

}
