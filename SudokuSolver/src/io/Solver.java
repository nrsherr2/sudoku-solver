package io;

import java.io.File;
import java.io.FileNotFoundException;
//import java.util.Arrays;
import java.util.Scanner;

import board.Board;

public class Solver {

    static Board sudoku;
    static long numChecks;

    /**
     * Main method.
     * 
     * @param args
     *            the command line arguments. Args[0] is the sudoku document.
     */
    public static void main(String[] args) {
        long l1 = System.currentTimeMillis();
        numChecks = 0;
        if (args.length != 1) {
            throw new IllegalArgumentException(
                    "Wrong number of command line args");
        }
        Scanner lineReader;
        try {
            lineReader = new Scanner(new File(args[0]));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found- " + args[0]);
        }

        sudoku = new Board();
        int rowNum = 0;
        while (lineReader.hasNextLine()) {
            String line = lineReader.nextLine();
            rowNum++;
            if (rowNum >= 10) {
                lineReader.close();
                throw new IllegalArgumentException("Too many lines");
            }
            if (line.length() != 9) {
                lineReader.close();
                throw new IllegalArgumentException(
                        "File is incorrectly formatted");
            }
            for (int colNum = 0; colNum < 9; colNum++) {
                if (line.charAt(colNum) >= '1' && line.charAt(colNum) <= '9') {
                    // System.out.println("colNum = " + colNum + " rowNum = "
                    // + rowNum + " setNum = "
                    // + (line.charAt(colNum) - '0'));
                    sudoku.setNum(rowNum, colNum + 1,
                            line.charAt(colNum) - '0');
                }
            }
        }

        lineReader.close();
        // System.out.println(sudoku.getNumSpacesFilled());
        System.out.println(sudoku.getGrid());
        // System.out.println(sudoku.getAvailabilites());

        while (sudoku.getNumSpacesFilled() < 81) {
            int[] coordinates = findNextSpot();
            if (coordinates[0] != -1 && coordinates[1] != -1) {
                sudoku.setNum(coordinates[0] + 1, coordinates[1] + 1,
                        coordinates[2]);
                continue;
            }
            System.out.println(sudoku.getGrid());
            System.out.println(sudoku.getAvailabilites());
            long l2 = System.currentTimeMillis();
            long l3 = l2 - l1;
            System.out.println("It took " + l3
                    + " milliseconds to not solve this puzzle\n");
            throw new IllegalArgumentException(
                    "Unable to find a spot. But on the bright side, the system performed "
                            + numChecks + " checks.");
        }
        long l2 = System.currentTimeMillis();
        long l3 = l2 - l1;
        System.out.println("the system performed " + numChecks + " checks.");
        System.out.println(
                "It took " + l3 + " milliseconds to solve this puzzle\n");
        System.out.println(sudoku.getGrid());

    }

    /**
     * Finds the next spot
     * 
     * @return the row, column, and value of what you're going to place
     */
    private static int[] findNextSpot() {
        int[] coords = { -1, -1, -1 };
        int[] search = { -1, -1, -1 };
        search = findSingletons();
        if (search[0] != -1 && search[1] != -1) {
            return search;
        }
        search = findByBlock();
        // System.out.println(Arrays.toString(search));
        if (search[0] != -1 && search[1] != -1) {
            return search;
        }
        search = findByRow();
        if (search[0] != -1 && search[1] != -1) {
            return search;
        }
        search = findByCol();
        if (search[0] != -1 && search[1] != -1) {
            return search;
        }
        return coords;
    }

    private static int[] findByCol() {
        int[] search = { -1, -1, -1 };
        for (int i = 1; i < 10; i++) {
            for (int col = 0; col < 9; col++) {
                if (countPerCol(col, i) == 1) {
                    int[] coords = findNumInCol(col, i);
                    search[0] = coords[0];
                    search[1] = coords[1];
                    search[2] = i;
                    return search;
                }
            }
        }
        return search;
    }

    private static int[] findNumInCol(int col, int i) {
        for (int row = 0; row < 9; row++) {
            numChecks++;
            if (sudoku.checkAvailable(row, col, i) == true) {
                int[] ret = { row, col };
                return ret;
            }
        }
        return null;
    }

    private static int countPerCol(int col, int i) {
        int count = 0;
        for (int row = 0; row < 9; row++) {
            numChecks++;
            if (sudoku.checkAvailable(row, col, i) == true) {
                count++;
            }
        }
        return count;
    }

    private static int[] findSingletons() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (countAvailable(i, j) == 1) {
                    int[] ret = { i, j, findOnlyAvailable(i, j) };
                    return ret;
                }
            }
        }
        int[] ret = { -1, -1, -1 };
        return ret;
    }

    private static int findOnlyAvailable(int row, int col) {
        for (int i = 1; i < 10; i++) {
            numChecks++;
            if (sudoku.checkAvailable(row, col, i) == true) {
                return i;
            }
        }
        return 0;
    }

    private static int countAvailable(int row, int col) {
        int count = 0;
        for (int i = 1; i < 10; i++) {
            numChecks++;
            if (sudoku.checkAvailable(row, col, i) == true) {
                count++;
            }
        }
        return count;
    }

    private static int[] findByRow() {
        int[] search = { -1, -1, -1 };
        for (int i = 1; i < 10; i++) {
            for (int row = 0; row < 9; row++) {
                if (countPerRow(row, i) == 1) {
                    int[] coords = findNumInRow(row, i);
                    search[0] = coords[0];
                    search[1] = coords[1];
                    search[2] = i;
                    return search;
                }
            }
        }
        return search;
    }

    private static int[] findNumInRow(int row, int i) {
        for (int col = 0; col < 9; col++) {
            numChecks++;
            if (sudoku.checkAvailable(row, col, i) == true) {
                int[] ret = { row, col };
                return ret;
            }
        }
        return null;
    }

    private static int countPerRow(int row, int num) {
        int count = 0;
        for (int col = 0; col < 9; col++) {
            numChecks++;
            if (sudoku.checkAvailable(row, col, num) == true) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds the next number to place by checking each block
     * 
     * @return the row, column, and value of what you're going to place
     */
    private static int[] findByBlock() {
        int[] search = { -1, -1, -1 };
        for (int i = 1; i < 10; i++) { // check it for all numbers
            for (int startingRow = 0; startingRow < 9; startingRow += 3) {
                for (int startingCol = 0; startingCol < 9; startingCol += 3) {
                    if (countPerBlock(startingRow, startingCol, i) == 1) {
                        // System.out.println("found one");
                        search = findNumInBlock(startingRow, startingCol, i);
                        int[] data = { search[0], search[1], i };
                        // System.out.println(Arrays.toString(search));
                        return data;
                    }
                }
            }
        }
        return search;
    }

    /**
     * Finds the place to put the value in the block
     * 
     * @param startingRow
     *            the top corner of the block
     * @param startingCol
     *            the left corner of the rock
     * @param num
     *            the number to look for
     * @return the location to place the value
     */
    private static int[] findNumInBlock(int startingRow, int startingCol,
            int num) {
        for (int i = startingRow; i < startingRow + 3; i++) {
            for (int j = startingCol; j < startingCol + 3; j++) {
                numChecks++;
                if (sudoku.checkAvailable(i, j, num) == true) {
                    int[] ret = { i, j };
                    // System.out.println(Arrays.toString(ret));
                    return ret;
                }
            }
        }
        return null;
    }

    /**
     * Counts the occurrences of a number per block
     * 
     * @param startingRow
     *            the top corner
     * @param startingCol
     *            the left corner
     * @param numToCheck
     *            the number you're looking at
     * @return the number of times numToCheck occurs
     */
    private static int countPerBlock(int startingRow, int startingCol,
            int numToCheck) {
        int count = 0;
        for (int i = startingRow; i < 3 + startingRow; i++) {
            for (int j = startingCol; j < 3 + startingCol; j++) {
                numChecks++;
                if (sudoku.checkAvailable(i, j, numToCheck) == true) {
                    count++;
                }
            }
        }
        // if (count == 1) {
        // System.out.println("block " + startingRow + "," + startingCol
        // + " has the value " + numToCheck + " " + count + " times");
        // }
        return count;
    }

}
