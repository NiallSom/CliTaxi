package com.ise.taxiapp.cli;

import java.util.Scanner;

public class Util {

    /**
     * Repeatedly prompts the user for a valid numerical input.
     * Valid inputs include any integer from 0 to `maxValid`, inclusive.
     *
     * @param prompt   Prompt to display to the user displaying valid options
     * @param maxValid The largest numerical input that is valid
     */
    public static int promptInput(String prompt, int maxValid, Scanner scanner) {
        while (true) {
            clearScreen();
            System.out.println(prompt);
            System.out.printf("%n(%d) Exit%n", maxValid + 1);
            System.out.print("Choice: ");
            try {
                // Parse this way to avoid infinite loops
                int choice = Integer.parseInt(scanner.next());
                // All prompts should include the option to exit
                if (choice == maxValid + 1) System.exit(0);
                // If the selection is valid
                if (0 <= choice && choice <= maxValid) return choice;
            } catch (NumberFormatException ignored) {
                // If the input was not a number at all, keep looping
            }
        }
    }

    /**
     * Clears the terminal
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Pauses the app until the user presses the enter key
     */
    public static void promptEnter() {
        System.out.println("Press Enter to continue");
        try {
            //noinspection ResultOfMethodCallIgnored
            System.in.read();
        } catch (Exception ignored) {
        }
    }
}
