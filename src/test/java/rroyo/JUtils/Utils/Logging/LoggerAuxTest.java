package rroyo.JUtils.Utils.Logging;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import rroyo.JUtils.Utils.Console.TStyle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for LoggerAux utility.
 * Verifies logging functionality including message formatting, file output, and directory validation.
 */
class LoggerAuxTest {

    /** Temporary directory for test file operations. */
    @TempDir
    Path tempDir;

    /**
     * Disables console output and enables debug mode before each test.
     */
    @BeforeEach
    void silenceLogger() {
        LoggerAux.setConsoleOutputEnabled(false);
        LoggerAux.setDebugEnabled(true);
    }

    /**
     * Verifies that all logging methods (info, warn, error, debug) return timestamped messages
     * with the expected severity level and content.
     */
    @Test
    void logMethodsReturnTimestampedMessagesWithExpectedSeverity() {
        String info = LoggerAux.info("hello");
        String warn = LoggerAux.warn("careful");
        String error = LoggerAux.error("broken");
        String debug = LoggerAux.debug("trace");

        assertAll(
                () -> assertTrue(TStyle.clearStyle(info).contains("[INFO]")),
                () -> assertTrue(TStyle.clearStyle(warn).contains("[WARNING]")),
                () -> assertTrue(TStyle.clearStyle(error).contains("[ERROR]")),
                () -> assertTrue(TStyle.clearStyle(debug).contains("[DEBUG]")),
                () -> assertTrue(TStyle.clearStyle(info).contains("hello"))
        );
    }

    /**
     * Verifies that when a log directory is set, log messages are written to a plain text log file
     * without ANSI escape codes.
     */
    @Test
    void setLogDirectoryWritesPlainTextLogFile() throws IOException {
        LoggerAux.setLogDirectory(tempDir.toFile());

        LoggerAux.info("persist me");

        Path logFile = Files.list(tempDir)
                .filter(path -> path.getFileName().toString().endsWith(".log"))
                .findFirst()
                .orElseThrow();
        String content = Files.readString(logFile);
        assertTrue(content.contains("persist me"));
        assertFalse(content.contains("\u001B["));
    }

    /**
     * Verifies that setLogDirectory rejects a non-directory file path
     * and throws IllegalArgumentException.
     */
    @Test
    void setLogDirectoryRejectsNonDirectory() {
        Path file = tempDir.resolve("not-dir.txt");

        assertDoesNotThrow(() -> Files.writeString(file, "x"));
        assertThrows(IllegalArgumentException.class, () -> LoggerAux.setLogDirectory(file.toFile()));
    }
}
