package rroyo.JUtils.Utils.Console;

import rroyo.JUtils.Utils.Core.Validator;
import rroyo.JUtils.Utils.Logging.LoggerAux;

import java.io.IOException;

/**
 * Utility class providing system-level console commands and environment interaction.
 * <p>
 * This class abstracts complex or platform-specific operations such as clearing the
 * terminal screen, setting window titles, and managing system alerts, ensuring
 * compatibility between Windows and Unix-based systems.
 * </p>
 *
 * @author rroyo
 */
public final class CSystem {

    /**
     *
     */
    private CSystem() {}

    /**
     * Clears the current console screen.
     * <p>
     * Implementation details:
     * <ul>
     *   <li><b>Windows:</b> Executes the 'cls' command through a new process.</li>
     *   <li><b>Unix/Linux/macOS:</b> Uses ANSI escape codes (\033[H\033[2J) to reset the cursor and clear the buffer.</li>
     * </ul>
     */
    public static void clear() {
        try {
            String os = System.getProperty("os.name");

            System.out.println("\n".repeat(50));
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }

            LoggerAux.info("Console cleared");
        } catch (IOException | InterruptedException e) {
            LoggerAux.error("Failed to clear console: " + e.getMessage());
        }
    }

    /**
     * Triggers the default system beep sound using the AWT Toolkit.
     * Useful for notifying the user when a long process or a benchmark has finished.
     */
    public static void beep() {
        java.awt.Toolkit.getDefaultToolkit().beep();
        LoggerAux.info("Beep sound triggered");
    }

    /**
     * Halts the program execution until the user presses the 'Enter' key.
     *
     * @param message The custom instruction to display before pausing.
     *                If empty, a default "Press Enter to continue..." message is shown.
     */
    public static void pause(String message) {
        System.out.println("\n" + (message.isEmpty() ? "Press Enter to continue..." : message));
        LoggerAux.info("Execution paused");
        try {
            System.in.read();
            LoggerAux.info("Execution continued");
        } catch (IOException e) {
            LoggerAux.error(e);
        }
    }

    /**
     * Retrieves an environment variable or returns a fallback value.
     *
     * @param key          The name of the environment variable (e.g., "PATH", "USER").
     * @param defaultValue The value to return if the variable is not defined.
     * @return The environment variable value or the provided default.
     * @throws IllegalArgumentException if the key is blank.
     */
    public static String getEnv(String key, String defaultValue) {
        Validator.notBlank(key, "Environment variable key cannot be blank");
        String value = System.getenv(key);
        return (value != null) ? value : defaultValue;
    }

    /**
     * Updates the text displayed on the console window title bar.
     *
     * @param title The new title string.
     * @throws IllegalArgumentException if the title is null.
     * <p>Support for this feature depends on the terminal emulator's capabilities.</p>
     */
    public static void setConsoleTitle(String title) {
        Validator.notNull(title, "Title cannot be null");
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            try {
                // Updates Windows CMD/PowerShell title using internal command
                new ProcessBuilder("cmd", "/c", "title", title).inheritIO().start().waitFor();
            } catch (Exception e) {
                LoggerAux.error(e);
            }
        } else {
            // Uses XTerm control sequence to update Unix-based terminal titles
            System.out.print("\033]0;" + title + "\007");
            System.out.flush();
        }
        LoggerAux.info("Console title setted to: " + title);
    }

    /**
     * Generates a descriptive summary of the current operating system and environment.
     *
     * @return A string containing OS name, architecture, Java version, and current username.
     */
    public static String getSystemSummary() {
        return LoggerAux.info(String.format("OS: %s (%s) | Java: %s | User: %s",
                System.getProperty("os.name"),
                System.getProperty("os.arch"),
                System.getProperty("java.version"),
                System.getProperty("user.name")));
    }

    /**
     * Terminates the current Java Virtual Machine.
     *
     * @param status Exit status code. 0 indicates successful termination,
     *               any other value indicates an error.
     */
    public static void exit(int status) {
        System.out.println(status == 0 ? "Exiting application..." : "Application terminated with errors.");
        LoggerAux.info("Application exitted with status: " + status);
        System.exit(status);
    }
}
