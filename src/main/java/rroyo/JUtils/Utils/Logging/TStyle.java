package rroyo.JUtils.Utils.Logging;

/**
 * Utility class to apply ANSI styles and colors to text in supported consoles.
 * <p>
 * Allows modifying the text color (foreground), background color, and applying
 * formats such as bold, italic, or underline. The class automatically manages
 * style propagation when a reset sequence (RESET) is detected in the message.
 * </p>
 *
 * @author rroyo
 */
public final class TStyle {

    private TStyle() {}

    /** Sequence to reset all styles and colors to console default values. */
    public static final String RESET = "\u001B[0m";

    // TEXT COLORS (Foreground)
    public static final String _BLACK = "\u001B[30m";
    public static final String _RED = "\u001B[31m";
    public static final String _GREEN = "\u001B[32m";
    public static final String _YELLOW = "\u001B[33m";
    public static final String _BLUE = "\u001B[34m";
    public static final String _MAGENTA = "\u001B[35m";
    public static final String _CYAN = "\u001B[36m";
    public static final String _WHITE = "\u001B[37m";

    // BACKGROUND COLORS
    public static final String _BG_BLACK = "\u001B[40m";
    public static final String _BG_RED = "\u001B[41m";
    public static final String _BG_GREEN = "\u001B[42m";
    public static final String _BG_YELLOW = "\u001B[43m";
    public static final String _BG_BLUE = "\u001B[44m";
    public static final String _BG_MAGENTA = "\u001B[45m";
    public static final String _BG_CYAN = "\u001B[46m";
    public static final String _BG_WHITE = "\u001B[47m";

    // TEXT STYLES
    public static final String _BOLD = "\u001B[1m";
    public static final String _ITALIC = "\u001B[3m";
    public static final String _UNDERLINE = "\u001B[4m";
    public static final String _REVERSE = "\u001B[7m";
    public static final String _STRIKETHROUGH = "\u001B[9m";

    /**
     * Centralizes the application of ANSI style codes to an object.
     * <p>
     * This method converts the object to a string and prepends the desired style code,
     * always closing it with the {@link #RESET} sequence.
     * </p>
     * <p>
     * Additionally, it implements style persistence logic: if the text already contains
     * a reset sequence (due to nested styling), it reinjects the current style code
     * immediately after the reset so the parent formatting is not lost.
     * </p>
     *
     * @param msg       The object whose content is to be styled.
     * @param styleCode The ANSI escape sequence representing the color or format.
     * @return Formatted text ready to be printed to the console.
     */
    private static String apply(Object msg, String styleCode) {
        String text = msg.toString();
        if (text.contains(RESET))
            text = text.replace(RESET, RESET + styleCode);
        return styleCode + text + RESET;
    }

    /**
     * Applies red color to the provided object.
     * @param msg Object to style.
     * @return String with applied format and style closure.
     */
    public static String red(Object msg) {
        return apply(msg, _RED);
    }

    /**
     * Applies green color to the provided object.
     * @param msg Object to style.
     * @return String with applied format and style closure.
     */
    public static String green(Object msg) {
        return apply(msg, _GREEN);
    }

    /**
     * Applies yellow color to the provided object.
     * @param msg Object to style.
     * @return String with applied format and style closure.
     */
    public static String yellow(Object msg) {
        return apply(msg, _YELLOW);
    }

    /**
     * Applies blue color to the provided object.
     * @param msg Object to style.
     * @return String with applied format and style closure.
     */
    public static String blue(Object msg) {
        return apply(msg, _BLUE);
    }

    /**
     * Applies magenta color to the provided object.
     * @param msg Object to style.
     * @return String with applied format and style closure.
     */
    public static String magenta(Object msg) {
        return apply(msg, _MAGENTA);
    }

    /**
     * Applies cyan color to the provided object.
     * @param msg Object to style.
     * @return String with applied format and style closure.
     */
    public static String cyan(Object msg) {
        return apply(msg, _CYAN);
    }

    /**
     * Applies white color to the provided object.
     * @param msg Object to style.
     * @return String with applied format and style closure.
     */
    public static String white(Object msg) {
        return apply(msg, _WHITE);
    }

    /**
     * Applies black color to the provided object.
     * @param msg Object to style.
     * @return String with applied format and style closure.
     */
    public static String black(Object msg) {
        return apply(msg, _BLACK);
    }

    /**
     * Applies a black background to the provided object.
     * @param msg Object to style.
     * @return String with applied background format.
     */
    public static String bg_black(Object msg) {
        return apply(msg, _BG_BLACK);
    }

    /**
     * Applies a red background to the provided object.
     * @param msg Object to style.
     * @return String with applied background format.
     */
    public static String bg_red(Object msg) {
        return apply(msg, _BG_RED);
    }

    /**
     * Applies a green background to the provided object.
     * @param msg Object to style.
     * @return String with applied background format.
     */
    public static String bg_green(Object msg) {
        return apply(msg, _BG_GREEN);
    }

    /**
     * Applies a yellow background to the provided object.
     * @param msg Object to style.
     * @return String with applied background format.
     */
    public static String bg_yellow(Object msg) {
        return apply(msg, _BG_YELLOW);
    }

    /**
     * Applies a blue background to the provided object.
     * @param msg Object to style.
     * @return String with applied background format.
     */
    public static String bg_blue(Object msg) {
        return apply(msg, _BG_BLUE);
    }

    /**
     * Applies a magenta background to the provided object.
     * @param msg Object to style.
     * @return String with applied background format.
     */
    public static String bg_magenta(Object msg) {
        return apply(msg, _BG_MAGENTA);
    }

    /**
     * Applies a cyan background to the provided object.
     * @param msg Object to style.
     * @return String with applied background format.
     */
    public static String bg_cyan(Object msg) {
        return apply(msg, _BG_CYAN);
    }

    /**
     * Applies a white background to the provided object.
     * @param msg Object to style.
     * @return String with applied background format.
     */
    public static String bg_white(Object msg) {
        return apply(msg, _BG_WHITE);
    }

    /**
     * Applies bold style to the provided object.
     * @param msg Object to style.
     * @return String with applied bold format.
     */
    public static String bold(Object msg){
        return apply(msg, _BOLD);
    }

    /**
     * Applies italic style to the provided object.
     * @param msg Object to style.
     * @return String with applied italic format.
     */
    public static String italic(Object msg){
        return apply(msg, _ITALIC);
    }

    /**
     * Applies underline to the provided object.
     * @param msg Object to style.
     * @return String with applied underline format.
     */
    public static String underline(Object msg){
        return apply(msg, _UNDERLINE);
    }

    /**
     * Applies reverse mode (swaps background and foreground colors) to the provided object.
     * @param msg Object to style.
     * @return String with applied reverse format.
     */
    public static String reversed(Object msg){
        return apply(msg, _REVERSE);
    }

    /**
     * Applies strikethrough to the provided object.
     * @param msg Object to style.
     * @return String with applied strikethrough format.
     */
    public static String strikethrough(Object msg){
        return apply(msg, _STRIKETHROUGH);
    }
}