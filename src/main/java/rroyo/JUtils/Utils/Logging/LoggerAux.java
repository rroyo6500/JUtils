package rroyo.JUtils.Utils.Logging;

import rroyo.JUtils.Utils.Validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for event logging with console style support.
 * <p>
 * This class allows printing formatted messages with timestamps and severity levels
 * visually differentiated through colors and styles defined in {@link TStyle}.
 * </p>
 *
 * @author rroyo
 */
public final class LoggerAux {

    private LoggerAux() {}

    /**
     * Enumerates the severity levels supported by the logger.
     */
    private enum LogType {
        /** General information about program flow. */
        INFO,
        /** Warnings about unexpected non-critical situations. */
        WARNING,
        /** Errors that prevent the correct execution of an operation. */
        ERROR
    }

    /** Standard date and time formatter (Year-Month-Day Hour:Minute:Second). */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Logs an information message to the console.
     *
     * @param msg The informative message to display.
     * @return The log message
     */
    public static String info(String msg) {
        return log(LogType.INFO, msg);
    }

    /**
     * Logs a warning visualized in yellow color.
     *
     * @param msg The warning message.
     * @return The log message
     */
    public static String warn(String msg) {
        return log(LogType.WARNING, TStyle.yellow(msg));
    }

    /**
     * Logs a critical error visualized in red color.
     *
     * @param msg The descriptive error message.
     * @return The log message
     */
    public static String error(String msg) {
        return log(LogType.ERROR, TStyle.red(msg));
    }

    /**
     * Logs a critical error including an exception description.
     *
     * @param msg       Personalized error message.
     * @param exception Captured exception from which the technical message will be extracted.
     * @return The log message
     */
    public static String error(String msg, Exception exception) {
        Validator.notNull(exception, "Exception cannot be null");
        return log(LogType.ERROR, TStyle.red(String.format("%s%n%s", msg,
                (exception.getMessage() != null)
                        ? exception.getMessage() : exception.toString()
        )));
    }

    /**
     * Logs a critical error by extracting information directly from an Exception object.
     * <p>
     * This method validates that the exception is not null using {@link Validator}
     * before processing. It attempts to retrieve the specific error message;
     * if unavailable, it defaults to the exception's string representation.
     * </p>
     *
     * @param exception The exception to be logged.
     * @return The formatted error message string as processed by the internal log system.
     * @throws IllegalArgumentException if the provided exception is null.
     */
    public static String error(Exception exception) {
        Validator.notNull(exception, "Exception cannot be null");
        return log(LogType.ERROR, TStyle.red(
                exception.toString()
        ));
    }

    /**
     * Internal method that centralizes formatting and printing of logs.
     * <p>
     * Applies italic styles and colors to both the level tag and the content
     * based on the provided log type.
     * </p>
     *
     * @param logType Severity level.
     * @param msg     Processed message with its corresponding styles.
     * @return The log message
     */
    private static String log(LogType logType, String msg) {
        Validator.notBlank(msg, "Message is blank");
        String str = String.format(
                "[%s] [%s] %s%n%n",
                LocalDateTime.now().format(formatter),
                switch (logType) {
                    case INFO -> TStyle.italic(logType);
                    case WARNING -> TStyle.italic(TStyle.yellow(logType));
                    case ERROR -> TStyle.italic(TStyle.red(logType));
                },
                msg
        );
        System.out.println(str);
        return str;
    }
}