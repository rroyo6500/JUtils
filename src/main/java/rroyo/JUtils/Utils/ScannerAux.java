package rroyo.JUtils.Utils;

import java.util.Scanner;

public final class ScannerAux {

    private ScannerAux(){}

    private static Scanner in = new Scanner(System.in);

    public static String readString (String prompt) {
        System.out.print(prompt + ": ");
        return in.nextLine();
    }

    public static int readInt (String prompt) {
        System.out.print(prompt + ": ");
        return in.nextInt();
    }

    public static double readDouble (String prompt) {
        System.out.print(prompt + ": ");
        return in.nextDouble();
    }

    public static boolean readBoolean (String prompt) {
        System.out.print(prompt + ": ");
        return in.nextBoolean();
    }

    public static char readChar (String prompt) {
        System.out.print(prompt + ": ");
        return in.next().charAt(0);
    }

}
