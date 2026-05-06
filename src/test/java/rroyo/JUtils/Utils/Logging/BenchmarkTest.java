package rroyo.JUtils.Utils.Logging;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BenchmarkTest {

    @BeforeEach
    void silenceLogger() {
        LoggerAux.setConsoleOutputEnabled(false);
    }

    @Test
    void startedBenchmarkCanBeStopped() {
        Benchmark.start("unit");

        assertDoesNotThrow(() -> Benchmark.stop("unit"));
    }

    @Test
    void stopWithoutStartThrowsHelpfulException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Benchmark.stop("missing")
        );

        assertTrue(exception.getMessage().contains("missing"));
    }

    @Test
    void blankBenchmarkNameIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> Benchmark.start(" "));
    }
}
