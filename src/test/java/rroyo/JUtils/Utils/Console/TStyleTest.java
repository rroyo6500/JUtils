package rroyo.JUtils.Utils.Console;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for TStyle utility.
 */
class TStyleTest {

    /**
     * Verifies that color methods wrap text and clearStyle removes ANSI codes.
     */
    @Test
    void colorMethodsWrapTextAndClearStyleRemovesAnsiCodes() {
        String styled = TStyle.red("error");

        assertEquals(TStyle._RED + "error" + TStyle.RESET, styled);
        assertEquals("error", TStyle.clearStyle(styled));
    }

    /**
     * Verifies that clearStyle handles null and mixed styles.
     */
    @Test
    void clearStyleHandlesNullAndMixedStyles() {
        String styled = TStyle.bold(TStyle.green("ok"));

        assertEquals("", TStyle.clearStyle(null));
        assertEquals("ok", TStyle.clearStyle(styled));
    }

    /**
     * Verifies that nested style reapplies parent after inner reset.
     */
    @Test
    void nestedStyleReappliesParentAfterInnerReset() {
        String nested = TStyle.red("before " + TStyle.bold("strong") + " after");

        assertTrue(nested.contains(TStyle.RESET + TStyle._RED + " after"));
        assertEquals("before strong after", TStyle.clearStyle(nested));
    }
}
