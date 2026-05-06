package rroyo.JUtils.Utils.IO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import rroyo.JUtils.Utils.Logging.LoggerAux;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DataFileUtilTest {

    @TempDir
    Path tempDir;

    @BeforeEach
    void silenceLoggers() {
        LoggerAux.setConsoleOutputEnabled(false);
        FileUtilHandler.setLogging(false);
    }

    @Test
    void readDataParsesEntriesAndIgnoresComments() {
        String data = """
                \\* ignored comment *\\
                \u00A1 name:
                ^ Alice ~

                \u00A1 city:
                ^ Madrid ~
                """;

        Map<String, String> result = DataFileUtil.readData(data);

        assertEquals(Map.of("name", "Alice", "city", "Madrid"), result);
    }

    @Test
    void readDataKeepsFirstValueForDuplicateKeys() {
        String data = """
                \u00A1 key:
                ^ first ~
                \u00A1 key:
                ^ second ~
                """;

        Map<String, String> result = DataFileUtil.readData(data);

        assertEquals("first", result.get("key"));
    }

    @Test
    void writeDataFileSortsKeysAndRoundTripsThroughParser() throws IOException {
        File file = tempDir.resolve("data.jutils").toFile();
        Map<String, String> input = new LinkedHashMap<>();
        input.put("zeta", "last");
        input.put("alpha", "first");

        DataFileUtil.writeDataFile(input, file);

        String raw = FileUtilHandler.readFile(file);
        assertTrue(raw.indexOf("\u00A1alpha:") < raw.indexOf("\u00A1zeta:"));
        assertEquals(Map.of("alpha", "first", "zeta", "last"), DataFileUtil.readDataFile(file));
    }

    @Test
    void invalidDataAndReservedKeysThrow() {
        Map<String, String> invalidKey = Map.of("bad:key", "value");

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> DataFileUtil.readData(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> DataFileUtil.readData("   ")),
                () -> assertThrows(IllegalArgumentException.class, () -> DataFileUtil.readData("\u00A1missing ^ value ~")),
                () -> assertThrows(IllegalArgumentException.class, () -> DataFileUtil.writeDataFile((Map<String, String>) null, tempDir.resolve("x").toFile())),
                () -> assertThrows(IllegalArgumentException.class, () -> DataFileUtil.writeDataFile(invalidKey, tempDir.resolve("x").toFile()))
        );
    }
}
