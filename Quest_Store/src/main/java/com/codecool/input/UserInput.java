package com.codecool.input;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInput {

    private static final int TEMP = -1;
    private Scanner scanner;

    private void initializeScanner() {
        scanner = new Scanner(System.in);
    }
    /**
     * @param message Message to print
     * @return int;
     */
    public int getInt(String message) {

        boolean isBadInput = false;
        int userInput = TEMP;

        while(!isBadInput) {

            this.initializeScanner();
            System.out.println(message);
            try {

                userInput = scanner.nextInt();
                isBadInput = true;

            } catch (InputMismatchException e) {
                System.out.println("Type right option!");
            }
        }
        return userInput;
    }
    /**
     * @param message Message to print
     * @return String;
     */
    public String getString(String message) {

        initializeScanner();
        System.out.println(message);
        return scanner.nextLine();

    }
}
