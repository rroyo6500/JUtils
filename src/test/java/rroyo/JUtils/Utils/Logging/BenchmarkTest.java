package rroyo.JUtils.Utils.Logging;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Benchmark utility.
 */
class BenchmarkTest {

    /**
     * Silences the logger before each test.
     */
    @BeforeEach
    void silenceLogger() {
        LoggerAux.setConsoleOutputEnabled(false);
    }

    /**
     * Verifies that a started benchmark can be stopped.
     */
    @Test
    void startedBenchmarkCanBeStopped() {
        Benchmark.start("unit");

        assertDoesNotThrow(() -> Benchmark.stop("unit"));
    }

    /**
     * Verifies that stopping without starting throws a helpful exception.
     */
    @Test
    void stopWithoutStartThrowsHelpfulException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Benchmark.stop("missing")
        );

        assertTrue(exception.getMessage().contains("missing"));
    }

    /**
     * Verifies that a blank benchmark name is rejected.
     */
    @Test
    void blankBenchmarkNameIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> Benchmark.start(" "));
    }
}
