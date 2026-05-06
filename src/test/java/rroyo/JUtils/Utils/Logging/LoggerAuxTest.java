package rroyo.JUtils.Utils.Logging;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import rroyo.JUtils.Utils.Console.TStyle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class LoggerAuxTest {

    @TempDir
    Path tempDir;

    @BeforeEach
    void silenceLogger() {
        LoggerAux.setConsoleOutputEnabled(false);
        LoggerAux.setDebugEnabled(true);
    }

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

    @Test
    void setLogDirectoryRejectsNonDirectory() {
        Path file = tempDir.resolve("not-dir.txt");

        assertDoesNotThrow(() -> Files.writeString(file, "x"));
        assertThrows(IllegalArgumentException.class, () -> LoggerAux.setLogDirectory(file.toFile()));
    }
}
