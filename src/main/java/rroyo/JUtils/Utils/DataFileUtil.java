package rroyo.JUtils.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Utility class for reading and writing data using a custom text-based file format.
 * This class provides methods to parse and generate files containing key-value pairs,
 * where a specific format is required to store the data.
 *
 * The custom format follows these rules:
 * - `¡` is used as a section separator.
 * - `:` separates keys from their values.
 * - `^` marks the start of a value.
 * - `~` marks the end of a value.
 * - Comments within the files are enclosed between `\*Comment*\`.
 *
 * This class is intended for static use and cannot be instantiated.
 *
 * Thread Safety:
 * This class is thread-safe as it contains only static, stateless methods.
 */
public final class DataFileUtil {

    /*
    ¡<key>:
    ^<value>~

    \*Comment*\
    \*
    ¡   -> Section Separator
    :   -> Key - Value Separator
    ^   -> Value Start
    ~   -> Value End
    *\
     */

    private DataFileUtil(){}

    /**
     * Parses the given data string and extracts key-value pairs based on specific delimiters
     * and formatting rules. The method removes comments, trims the content, and validates
     * the structure of the data before constructing the output map.
     *
     * @param data The input string containing the raw data to be parsed. This string
     *             must follow the expected format where each key-value pair is separated
     *             by specific delimiters. It must not be null or blank.
     * @return A map containing the parsed key-value pairs as strings. The keys and values
     *         are trimmed and validated before being added to the map.
     * @throws IllegalArgumentException If the input data is null, blank, or does not
     *                                  conform to the expected formatting rules.
     */
    public static Map<String, String> readData(String data) {
        if (data == null || data.isBlank()) throw new IllegalArgumentException("Data cannot be blank");

        String[] splittedContent = data.replaceAll("(?s)\\\\\\*.*?\\*\\\\", "")
                .trim()
                .split("(?s)\\s*¡(?=(?:[^\\^~]*\\^[^~]*~)*[^\\^~]*$)");

        Map<String, String> out = new HashMap<>();

        for (String s : splittedContent) {
            s = s.trim();
            if (s.isEmpty()) throw new IllegalArgumentException("Data file error");

            int endT = s.indexOf(':');
            int startV = (endT >= 0) ? s.indexOf('^', endT + 1) : -1;
            int endV = s.lastIndexOf('~');

            if (endT < 0 || startV < 0 || endV < 0 || endV <= startV)
                throw new IllegalArgumentException("Data file error");

            String key = s.substring(0, endT).trim();
            String value = s.substring(startV + 1, endV).trim();

            if (key.isEmpty()) throw new IllegalArgumentException("Data file error");

            out.putIfAbsent(key, value);
        }

        return out;
    }

    /**
     * Reads the content of the specified file and parses it to extract key-value pairs based on
     * specific delimiters and formatting rules. The method internally utilizes the `readFile`
     * method from `FileUtilHandler` to read the file contents as a string and the `readData`
     * method to process and return the key-value mappings.
     *
     * @param file The file to read and parse. It must not be null, must exist, must
     *             not be a directory, and must be readable.
     * @return A map containing the parsed key-value pairs from the file. Both keys and values
     *         are strings, trimmed of any unnecessary white spaces.
     * @throws IllegalArgumentException If the input file is null, does not exist,
     *                                  is a directory, or cannot be read.
     */
    public static Map<String, String> readDataFile (File file) {
        String content = FileUtilHandler.readFile(file);
        return readData(content);
    }

    /**
     * Reads the content of the file at the specified path and parses it to extract key-value pairs
     * based on specific delimiters and formatting rules. This method internally invokes the
     * overloaded {@code readDataFile(File file)} method to perform the processing.
     *
     * @param path The path to the file to read and parse. It must not be null, must not be blank,
     *             must point to a valid file, and the file must be readable.
     * @return A map containing the parsed key-value pairs from the file. Both keys and values
     *         are strings, trimmed of any unnecessary white spaces.
     * @throws IllegalArgumentException If the path is null, blank, does not point to a valid file,
     *                                  or the file cannot be read.
     */
    public static Map<String, String> readDataFile (String path) {
        if (path == null || path.isBlank()) throw new IllegalArgumentException("Path cannot be blank");
        return readDataFile(new File(path));
    }

    /**
     * Writes the provided data string to a file specified by its path. If the path is blank
     * or null, an exception will be thrown. Internally, this method delegates to
     * the {@code writeDataFile(File file, String data)} method.
     *
     * @param path The file path where the data will be written. It must not be null or blank.
     *             The path must also point to a valid, writable file location.
     * @param dataMessage The data to be written into the file. It must not be null or blank.
     * @throws IllegalArgumentException If the path is null, blank, or if the dataMessage is null or blank.
     */
    public static void writeDataFile(String path, String dataMessage) {
        if (path == null || path.isBlank()) throw new IllegalArgumentException("Path cannot be blank");
        writeDataFile(new File(path), dataMessage);
    }

    /**
     * Writes the provided data string to the specified file. The data will be trimmed
     * before being written. If the data string is null or blank, an exception will be thrown.
     * This method utilizes the {@code writeFile} method from {@code FileUtilHandler} to perform
     * the file writing operation.
     *
     * @param file The file where the data will be written. It must not be null and must point
     *             to a valid, writable file.
     * @param data The data string to write into the file. It must not be null or blank.
     * @throws IllegalArgumentException If the data is null or blank.
     */
    public static void writeDataFile(File file, String data) {
        if (data == null || data.isBlank()) throw new IllegalArgumentException("Data cannot be blank");
        FileUtilHandler.writeFile(file, data.trim());
    }

    /**
     * Writes the contents of the provided map to the specified file. The map entries
     * are sorted by their keys, formatted according to specific delimiters, and then
     * written to the file in the required format. This method delegates the actual
     * file-writing operation to the {@code writeDataFile(File, String)} method.
     *
     * @param dataMap The map containing string key-value pairs to be written to the file.
     *                Keys and values must not be null. The map itself must not be null.
     * @param file    The file where the map data will be written. It must not be null
     *                and must point to a valid, writable file.
     * @throws IllegalArgumentException If the dataMap is null, if a map entry contains a
     *                                  null key or value, or if a key contains reserved
     *                                  delimiter characters ('¡', ':').
     */
    public static void writeDataFile (Map<String, String> dataMap, File file) {
        if (dataMap == null) throw new IllegalArgumentException("Data map cannot be null");

        String ls = System.lineSeparator();
        StringBuilder sb = new StringBuilder();

        for (var e : new TreeMap<>(dataMap).entrySet()) {
            String key = e.getKey();
            String value = getString(e);

            sb.append('¡')
                    .append(key)
                    .append(':').append(ls)
                    .append('^')
                    .append(value)
                    .append('~').append(ls).append(ls);
        }

        writeDataFile(file, sb.toString());
    }

    /**
     * Writes the contents of the provided map to a file specified by its path. The map entries
     * are sorted by their keys, formatted according to specific delimiters, and then written
     * to the file in the required format. This method delegates the actual file-writing operation
     * to the {@code writeDataFile(File, String)} method.
     *
     * @param dataMap The map containing string key-value pairs to be written to the file.
     *                Keys and values must not be null. The map itself must not be null.
     * @param path    The file path where the map data will be written. It must not be null,
     *                blank, or point to an invalid location.
     * @throws IllegalArgumentException If the path is null, blank, or if the dataMap is null.
     */
    public static void writeDataFile (Map<String, String> dataMap, String path) {
        if (path == null || path.isBlank()) throw new IllegalArgumentException("Path cannot be blank");
        writeDataFile(dataMap, new File(path));
    }

    /**
     * Retrieves the value associated with the given map entry after validating the key and value.
     * The method ensures that the key and value are non-null and verifies that the key does not
     * contain reserved delimiter characters ('¡', ':').
     *
     * @param e The map entry containing the key-value pair. The entry must not be null, and both the key
     *          and value must be non-null. The key must not contain the reserved delimiter characters ('¡', ':').
     * @return The value associated with the map entry.
     * @throws NullPointerException If the map entry is null, or if the key or value in the entry is null.
     * @throws IllegalArgumentException If the key in the map entry contains reserved delimiter characters ('¡', ':').
     */
    private static String getString(Map.Entry<String, String> e) {
        if (e == null) throw new NullPointerException("Map entry cannot be null");

        String key = e.getKey();
        String value = e.getValue();

        if (key == null || value == null) {
            throw new NullPointerException("Key or value is null");
        }

        if (key.indexOf('¡') >= 0 || key.indexOf(':') >= 0)
            throw new IllegalArgumentException("Key contains reserved delimiter characters: '¡', ':'");
        return value;
    }

}
