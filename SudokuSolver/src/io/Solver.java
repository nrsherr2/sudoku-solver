package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import board.Board;

public class Solver {

    public static void main(String[] args) {
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

        Board sudoku = new Board();
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
        System.out.println(sudoku.getGrid());

    }

}
