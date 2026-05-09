package rroyo.JUtils.Utils.Core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rroyo.JUtils.Utils.Logging.LoggerAux;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Validator utility.
 */
class ValidatorTest {

    /**
     * Silences the logger before each test.
     */
    @BeforeEach
    void silenceLogger() {
        LoggerAux.setConsoleOutputEnabled(false);
    }

    /**
     * Verifies that notNull accepts objects and rejects null.
     */
    @Test
    void notNullAcceptsObjectsAndRejectsNull() {
        assertDoesNotThrow(() -> Validator.notNull("value", "must exist"));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Validator.notNull(null, "must exist")
        );
        assertEquals("must exist", exception.getMessage());
    }

    /**
     * Verifies that notBlank rejects null, empty, and whitespace.
     */
    @Test
    void notBlankRejectsNullEmptyAndWhitespace() {
        assertDoesNotThrow(() -> Validator.notBlank(" value ", "required"));

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> Validator.notBlank(null, "required")),
                () -> assertThrows(IllegalArgumentException.class, () -> Validator.notBlank("", "required")),
                () -> assertThrows(IllegalArgumentException.class, () -> Validator.notBlank("   ", "required"))
        );
    }

    /**
     * Verifies that inRange is inclusive for integers and doubles.
     */
    @Test
    void inRangeIsInclusiveForIntegersAndDoubles() {
        assertAll(
                () -> assertDoesNotThrow(() -> Validator.inRange(1, 1, 3, "range")),
                () -> assertDoesNotThrow(() -> Validator.inRange(3, 1, 3, "range")),
                () -> assertDoesNotThrow(() -> Validator.inRange(1.5, 1.5, 2.5, "range")),
                () -> assertDoesNotThrow(() -> Validator.inRange(2.5, 1.5, 2.5, "range"))
        );

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> Validator.inRange(0, 1, 3, "range")),
                () -> assertThrows(IllegalArgumentException.class, () -> Validator.inRange(4, 1, 3, "range")),
                () -> assertThrows(IllegalArgumentException.class, () -> Validator.inRange(1.4, 1.5, 2.5, "range")),
                () -> assertThrows(IllegalArgumentException.class, () -> Validator.inRange(2.6, 1.5, 2.5, "range"))
        );
    }

    /**
     * Verifies that boolean and positive guards use expected truth tables.
     */
    @Test
    void booleanAndPositiveGuardsUseExpectedTruthTables() {
        assertAll(
                () -> assertDoesNotThrow(() -> Validator.isPositive(0.1, "positive")),
                () -> assertDoesNotThrow(() -> Validator.assertTrue(true, "true")),
                () -> assertDoesNotThrow(() -> Validator.assertFalse(false, "false")),
                () -> assertThrows(IllegalArgumentException.class, () -> Validator.isPositive(0, "positive")),
                () -> assertThrows(IllegalArgumentException.class, () -> Validator.isPositive(-1, "positive")),
                () -> assertThrows(IllegalArgumentException.class, () -> Validator.assertTrue(false, "true")),
                () -> assertThrows(IllegalArgumentException.class, () -> Validator.assertFalse(true, "false"))
        );
    }
}
