package rroyo.JUtils.Utils.Data;

import rroyo.JUtils.Utils.Validator;

import java.util.Scanner;

/**
 * Utility class to facilitate data reading from standard input (console).
 * <p>
 * Provides static wrapper methods for {@link Scanner}, allowing a custom message
 * (prompt) to be displayed before capturing user input.
 * </p>
 *
 * @author rroyo
 */
public final class ScannerAux {

    private ScannerAux(){}

    /** Single Scanner instance linked to system standard input. */
    private static Scanner in = new Scanner(System.in);

    /**
     * Displays a prompt and reads a full line of text.
     *
     * @param prompt The message to show the user.
     * @return The entered string.
     */
    public static String readString (String prompt) {
        Validator.notBlank(prompt, "Prompt cannot be blank");
        System.out.print(prompt + ": ");
        return in.nextLine();
    }

    /**
     * Displays a prompt and reads an integer.
     *
     * @param prompt The message to show the user.
     * @return The entered integer.
     */
    public static int readInt (String prompt) {
        Validator.notBlank(prompt, "Prompt cannot be blank");
        System.out.print(prompt + ": ");
        int value = in.nextInt();
        in.nextLine();
        return value;
    }

    /**
     * Displays a prompt and reads a floating-point number (double).
     *
     * @param prompt The message to show the user.
     * @return The entered double.
     */
    public static double readDouble (String prompt) {
        Validator.notBlank(prompt, "Prompt cannot be blank");
        System.out.print(prompt + ": ");
        double value = in.nextDouble();
        in.nextLine();
        return value;
    }

    /**
     * Displays a prompt and reads a boolean value.
     *
     * @param prompt The message to show the user.
     * @return The entered boolean.
     */
    public static boolean readBoolean (String prompt) {
        Validator.notBlank(prompt, "Prompt cannot be blank");
        System.out.print(prompt + ": ");
        return in.nextBoolean();
    }

    /**
     * Displays a prompt and reads the first character of the input.
     *
     * @param prompt The message to show the user.
     * @return The first character entered.
     */
    public static char readChar (String prompt) {
        Validator.notBlank(prompt, "Prompt cannot be blank");
        System.out.print(prompt + ": ");
        return in.next().charAt(0);
    }
}