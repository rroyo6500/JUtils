package rroyo.JUtils.Utils.Console;

import rroyo.JUtils.Utils.Core.Validator;

/**
 * Utility class for text manipulation, alignment, and terminal visual components.
 * <p>
 * Provides methods for common text formatting tasks that are not natively
 * available in simple String operations, such as centering, capitalization,
 * and visual progress tracking.
 * </p>
 *
 * @author rroyo
 */
public final class TextFormatter {

    /**
     * Aligns the text in the center of a block of the specified width.
     * <p>
     * If the text is longer than the width, it will be truncated to fit.
     * </p>
     *
     * @param text  The string to be centered.
     * @param width The total width of the block.
     * @return The centered string padded with spaces.
     * @throws IllegalArgumentException if text is null.
     */
    public static String center(String text, int width) {
        Validator.notNull(text, "Text cannot be null");
        if (width <= text.length()) return truncate(text, width);

        int padding = (width - text.length()) / 2;
        return " ".repeat(padding) + text + " ".repeat(width - text.length() - padding);
    }

    /**
     * Capitalizes the first letter of every word in the provided string.
     * <p>
     * Example: "java utils" becomes "Java Utils".
     * </p>
     *
     * @param text The string to format.
     * @return A capitalized version of the input, or the original if null/empty.
     */
    public static String capitalize(String text) {
        if (text == null || text.isEmpty()) return text;

        String[] words = text.toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }
        return sb.toString().trim();
    }

    /**
     * Limits a string's length to a maximum threshold.
     * <p>
     * If the text exceeds the limit, it is cut and suffixed with an ellipsis (...).
     * </p>
     *
     * @param text      The string to evaluate.
     * @param maxLength The maximum allowed length including the ellipsis.
     * @return The truncated string or the original if within limits.
     * @throws IllegalArgumentException if text is null.
     */
    public static String truncate(String text, int maxLength) {
        Validator.notNull(text, "Text cannot be null");
        if (text.length() <= maxLength) return text;

        return text.substring(0, Math.max(0, maxLength - 3)) + "...";
    }

    /**
     * Generates a visual ASCII progress bar for the terminal.
     * <p>
     * Example output: [##########----------] 50%
     * </p>
     *
     * @param current The current progress value.
     * @param total   The total value representing 100%.
     * @param width   The character width of the bar (excluding the percentage text).
     * @return A formatted string with a green progress bar and percentage.
     */
    public static String progressBar(int current, int total, int width) {
        // Calculate progress percentage
        double progress = (double) current / total;
        int filledWidth = (int) (progress * width);

        return String.format("[%s%s] %d%%",
                TStyle.green("#".repeat(Math.max(0, filledWidth))),
                "-".repeat(Math.max(0, width - filledWidth)),
                (int)(progress * 100)
        );
    }
}