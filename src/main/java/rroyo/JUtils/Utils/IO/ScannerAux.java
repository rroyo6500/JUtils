package rroyo.JUtils.Utils.IO;

import rroyo.JUtils.Utils.Core.Validator;

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

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private ScannerAux(){}

    /** Single Scanner instance linked to system standard input. */
    private static Scanner in = new Scanner(System.in);

    /**
     * Displays a prompt and reads a full line of text with default separator.
     *
     * @param prompt The message to show the user.
     * @return The entered string.
     */
    public static String readString (String prompt) {
        return readString(prompt, true);
    }

    /**
     * Displays a prompt and reads a full line of text.
     *
     * @param prompt The message to show the user.
     * @return The entered string.
     */
    public static String readString (String prompt, boolean promptSeparator) {
        Validator.notBlank(prompt, "Prompt cannot be blank");
        System.out.print(prompt + ((promptSeparator) ? ": " : ""));
        return in.nextLine();
    }

    /**
     * Displays a prompt and reads an integer with default separator.
     *
     * @param prompt The message to show the user.
     * @return The entered integer.
     */
    public static int readInt (String prompt) {
        return readInt(prompt, true);
    }

    /**
     * Displays a prompt and reads an integer.
     *
     * @param prompt The message to show the user.
     * @return The entered integer.
     */
    public static int readInt (String prompt, boolean promptSeparator) {
        Validator.notBlank(prompt, "Prompt cannot be blank");
        System.out.print(prompt + ((promptSeparator) ? ": " : ""));
        int value = in.nextInt();
        in.nextLine();
        return value;
    }

    /**
     * Displays a prompt and reads a double with default separator.
     *
     * @param prompt The message to show the user.
     * @return The entered double.
     */
    public static double readDouble (String prompt) {
        return readDouble(prompt, true);
    }

    /**
     * Displays a prompt and reads a floating-point number (double).
     *
     * @param prompt The message to show the user.
     * @return The entered double.
     */
    public static double readDouble (String prompt, boolean promptSeparator) {
        Validator.notBlank(prompt, "Prompt cannot be blank");
        System.out.print(prompt + ((promptSeparator) ? ": " : ""));
        double value = in.nextDouble();
        in.nextLine();
        return value;
    }

    /**
     * Displays a prompt and reads a boolean with default separator.
     *
     * @param prompt The message to show the user.
     * @return The entered boolean.
     */
    public static boolean readBoolean (String prompt) {
        return readBoolean(prompt, true);
    }

    /**
     * Displays a prompt and reads a boolean value.
     *
     * @param prompt The message to show the user.
     * @return The entered boolean.
     */
    public static boolean readBoolean (String prompt, boolean promptSeparator) {
        Validator.notBlank(prompt, "Prompt cannot be blank");
        System.out.print(prompt + ((promptSeparator) ? ": " : ""));
        return in.nextBoolean();
    }

    /**
     * Displays a prompt and reads a char with default separator.
     *
     * @param prompt The message to show the user.
     * @return The first character entered.
     */
    public static char readChar (String prompt) {
        return readChar(prompt, true);
    }

    /**
     * Displays a prompt and reads the first character of the input.
     *
     * @param prompt The message to show the user.
     * @return The first character entered.
     */
    public static char readChar (String prompt, boolean promptSeparator) {
        Validator.notBlank(prompt, "Prompt cannot be blank");
        System.out.print(prompt + ((promptSeparator) ? ": " : ""));
        return in.next().charAt(0);
    }
}