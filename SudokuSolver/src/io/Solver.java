package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solver {

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Wrong number of command line args");
        }
        Scanner lineReader;
        try {
            lineReader = new Scanner(new File(args[0]));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found- " + args[0]);
        }
        
        
        lineReader.close();

    }

}
