package rroyo.JUtils.Utils.IO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import rroyo.JUtils.Utils.Logging.LoggerAux;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for FileUtilHandler utility.
 * Verifies file reading, writing, appending, whitespace trimming, and error handling.
 */
class FileUtilHandlerTest {

    /** Temporary directory for test file operations. */
    @TempDir
    Path tempDir;

    /**
     * Silences loggers before each test.
     */
    @BeforeEach
    void silenceLoggers() {
        LoggerAux.setConsoleOutputEnabled(false);
    }

    /**
     * Verifies that writeFile creates a new file and overwrites existing content.
     */
    @Test
    void writeFileCreatesAndOverwritesContent() throws IOException {
        File file = tempDir.resolve("sample.txt").toFile();

        FileUtilHandler.writeFile(file, "first");
        FileUtilHandler.writeFile(file, "second");

        assertEquals("second", FileUtilHandler.readFile(file));
    }

    /**
     * Verifies that writeFile can append content to existing files.
     */
    @Test
    void writeFileCanAppendContent() throws IOException {
        File file = tempDir.resolve("append.txt").toFile();

        FileUtilHandler.writeFile(file, "a", false);
        FileUtilHandler.writeFile(file, "b", true);

        assertEquals("ab", FileUtilHandler.readFile(file));
    }

    /**
     * Verifies that readFile trims trailing whitespace and newlines correctly.
     */
    @Test
    void readFileTrimsTrailingWhitespace() throws IOException {
        File file = tempDir.resolve("trim.txt").toFile();

        FileUtilHandler.writeFile(file, "line 1\nline 2\n\n");

        assertEquals("line 1\nline 2", FileUtilHandler.readFile(file));
    }

    /**
     * Verifies that the utility throws IllegalArgumentException for invalid inputs
     * (null, blank, missing files, directories, etc.).
     */
    @Test
    void invalidInputsThrowIllegalArgumentException() {
        File directory = tempDir.toFile();
        File missing = tempDir.resolve("missing.txt").toFile();

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> FileUtilHandler.readFile((String) null)),
                () -> assertThrows(IllegalArgumentException.class, () -> FileUtilHandler.readFile("   ")),
                () -> assertThrows(IllegalArgumentException.class, () -> FileUtilHandler.readFile(missing)),
                () -> assertThrows(IllegalArgumentException.class, () -> FileUtilHandler.readFile(directory)),
                () -> assertThrows(IllegalArgumentException.class, () -> FileUtilHandler.writeFile(directory, "data"))
        );
    }
}
