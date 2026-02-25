package rroyo.JUtils.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * A utility interface for reading and writing custom-formatted data files. The utility provides
 * methods to parse a file content into a key-value map and write a map's contents back to a file
 * using a predefined syntax.
 *
 * @author _rroyo65_
 *
 * @implNote Requires {@code FileUtilHandler} to works.
 * @see FileUtilHandler
 */
public final class DataFileUtil {

    private DataFileUtil(){}

    /**
     * Reads and parses the given data string into a map of key-value pairs.
     * The input data is expected to follow a specific format where each
     * key-value pair is separated by the character `&iexcl;`, and the key and value
     * are delineated using ':' and '^...~' respectively. Comments enclosed in
     * `\* *\`
    */
    public static Map<String, String> readData(String data) {
        if (data == null || data.isBlank()) throw new IllegalArgumentException("Data cannot be blank");

        String[] splittedContent = data.replaceAll("(?s)\\\\\\*.*?\\*\\\\", "")
                .trim()
                .split("(?s)\\s*¡(?=(?:[^\\^~]*\\^[^~]*~)*[^\\^~]*$)");

        Map<String, String> out = new HashMap<>();

        for (String s : splittedContent) {
            s = s.trim();
            if (s.isEmpty()) continue;

            int endT = s.indexOf(':');
            int startV = (endT >= 0) ? s.indexOf('^', endT + 1) : -1;
            int endV = s.lastIndexOf('~');

            if (endT < 0 || startV < 0 || endV < 0 || endV <= startV) continue;

            String key = s.substring(0, endT).trim();
            String value = s.substring(startV + 1, endV).trim();

            if (key.isEmpty()) continue;

            out.put(key, value);
        }

        return out;
    }

    /**
     * Reads the content of the specified file and parses it into a map of key-value pairs.
     * The file's content is expected to be in a specific format that can be processed by
     * the {@code readData} method.
     *
     * @param file The file to read and parse. Must not be null.
     * @return A map containing the parsed key-value pairs from the file's content.
     *         Returns an empty map if the file is empty or its content cannot be parsed.
     * @throws NullPointerException If the provided file is null.
     */
    public static Map<String, String> readDataFile (File file) {
        String content = FileUtilHandler.readFile(file);
        return readData(content);
    }

    /**
     * Reads the content of a file located at the specified path and parses it into a map of key-value pairs.
     * The file's content is expected to be in a specific format that can be processed by the {@code readData} method.
     *
     * @param path The path of the file to be read and parsed. Must not be null or blank.
     * @return A map containing the parsed key-value pairs from the file's content.
     *         Returns an empty map if the file is empty or its content cannot be parsed.
     * @throws IllegalArgumentException If the provided path is null or blank.
     */
    public static Map<String, String> readDataFile (String path) {
        if (path == null || path.isBlank()) throw new IllegalArgumentException("Path cannot be blank");
        return readDataFile(new File(path));
    }

    /**
     * Writes the specified data message to a file located at the given path.
     * Throws an exception if the path is null or blank. Utilizes an overloaded
     * method to handle file-based writing.
     *
     * @param path The file path where the data message will be written. Must not be null or blank.
     * @param dataMessage The data message to write to the file. Can be null or blank,
     *                    but the handling is delegated to the overloaded method.
     * @throws IllegalArgumentException If the provided path is null or blank.
     */
    public static void writeDataFile(String path, String dataMessage) {
        if (path == null || path.isBlank()) throw new IllegalArgumentException("Path cannot be blank");
        writeDataFile(new File(path), dataMessage);
    }

    /**
     * Writes the given trimmed data to the specified file. Throws an exception if the data is
     * null or blank. Utilizes the {@code FileUtilHandler.writeFile} method for file writing.
     *
     * @param file The file where the data will be written. Must not be null.
     * @param data The data to write to the file. Must not be null or blank.
     * @throws IllegalArgumentException If the provided data is null or blank.
     */
    public static void writeDataFile(File file, String data) {
        if (data == null || data.isBlank()) throw new IllegalArgumentException("Data cannot be blank");
        FileUtilHandler.writeFile(file, data.trim());
    }

    /**
     * Writes the contents of a given map to the specified file. Each entry in the map is
     * formatted as {@code !key:^value$} with a blank line separating the entries.
     * If either the map or file is null, the method does nothing.
     *
     * @param dataMap A map containing key-value pairs to be written to the file.
     *                The key-value pairs represent the content to be saved.
     *                If null, the method does nothing.
     * @param file    The file to which the formatted data is written.
     *                If null, the method does nothing.
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
     * Writes the provided data map to a file. Each key-value pair in the map is formatted
     * as {@code !key:^value$} and saved to the specified file path.
     *
     * @param dataMap A map containing the data to be written to the file. Each key-value pair
     *                represents an entry to be saved. If null, the method does nothing.
     * @param path The path of the target file where the data will be written. If blank, the method does nothing.
     */
    public static void writeDataFile (Map<String, String> dataMap, String path) {
        if (path == null || path.isBlank()) throw new IllegalArgumentException("Path cannot be blank");
        writeDataFile(dataMap, new File(path));
    }

    /**
     * Retrieves the value from the specified map entry if the provided key and value are valid.
     * Validity is determined by ensuring neither the key nor the value is null, and neither contains
     * any reserved delimiter characters: '¡', ':'.
     *
     * @param e The map entry containing the key-value pair.
     * @return The value associated with the map entry if both the key and value are valid.
     * @throws NullPointerException If either the provided key or the map entry's value is null.
     * @throws IllegalArgumentException If the key or value contains reserved delimiter characters.
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
