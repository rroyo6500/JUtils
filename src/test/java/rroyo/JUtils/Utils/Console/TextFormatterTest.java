package rroyo.JUtils.Utils.Console;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rroyo.JUtils.Utils.Logging.LoggerAux;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for TextFormatter utility.
 */
class TextFormatterTest {

    /**
     * Silences the logger before each test.
     */
    @BeforeEach
    void silenceLogger() {
        LoggerAux.setConsoleOutputEnabled(false);
    }

    /**
     * Verifies that center pads evenly and truncates when width is too small.
     */
    @Test
    void centerPadsEvenlyAndTruncatesWhenWidthIsTooSmall() {
        assertEquals("  hi  ", TextFormatter.center("hi", 6));
        assertEquals("ab...", TextFormatter.center("abcdef", 5));
    }

    /**
     * Verifies that capitalize normalizes word casing and whitespace.
     */
    @Test
    void capitalizeNormalizesWordCasingAndWhitespace() {
        assertEquals("Java Utils", TextFormatter.capitalize("jAVA   uTILS"));
        assertEquals("", TextFormatter.capitalize(""));
        assertNull(TextFormatter.capitalize(null));
    }

    /**
     * Verifies that truncate leaves short text and adds ellipsis for long text.
     */
    @Test
    void truncateLeavesShortTextAndAddsEllipsisForLongText() {
        assertEquals("abc", TextFormatter.truncate("abc", 3));
        assertEquals("ab...", TextFormatter.truncate("abcdef", 5));
        assertThrows(IllegalArgumentException.class, () -> TextFormatter.truncate(null, 5));
    }

    /**
     * Verifies that progressBar formats filled width and percentage.
     */
    @Test
    void progressBarFormatsFilledWidthAndPercentage() {
        String bar = TextFormatter.progressBar(3, 10, 10);

        assertEquals("[###-------] 30%", TStyle.clearStyle(bar));
        assertTrue(bar.contains(TStyle._GREEN + "###" + TStyle.RESET));
    }
}
