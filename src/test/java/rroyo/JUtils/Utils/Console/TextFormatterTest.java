package rroyo.JUtils.Utils.Console;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rroyo.JUtils.Utils.Logging.LoggerAux;

import static org.junit.jupiter.api.Assertions.*;

class TextFormatterTest {

    @BeforeEach
    void silenceLogger() {
        LoggerAux.setConsoleOutputEnabled(false);
    }

    @Test
    void centerPadsEvenlyAndTruncatesWhenWidthIsTooSmall() {
        assertEquals("  hi  ", TextFormatter.center("hi", 6));
        assertEquals("ab...", TextFormatter.center("abcdef", 5));
    }

    @Test
    void capitalizeNormalizesWordCasingAndWhitespace() {
        assertEquals("Java Utils", TextFormatter.capitalize("jAVA   uTILS"));
        assertEquals("", TextFormatter.capitalize(""));
        assertNull(TextFormatter.capitalize(null));
    }

    @Test
    void truncateLeavesShortTextAndAddsEllipsisForLongText() {
        assertEquals("abc", TextFormatter.truncate("abc", 3));
        assertEquals("ab...", TextFormatter.truncate("abcdef", 5));
        assertThrows(IllegalArgumentException.class, () -> TextFormatter.truncate(null, 5));
    }

    @Test
    void progressBarFormatsFilledWidthAndPercentage() {
        String bar = TextFormatter.progressBar(3, 10, 10);

        assertEquals("[###-------] 30%", TStyle.clearStyle(bar));
        assertTrue(bar.contains(TStyle._GREEN + "###" + TStyle.RESET));
    }
}
