package rroyo.JUtils.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
public interface DataFileUtil {

    /**
     * Lee el contenido del archivo de instrucciones especificado y lo analiza en un mapa de pares clave-valor.
     * Se espera que el contenido del archivo tenga secciones separadas por el carácter `!`, donde cada sección
     * contiene un par clave-valor con el formato `clave:^valor$`.
     *
     * @param path La ruta del archivo a leer y analizar.
     * @return Un mapa que contiene los pares clave-valor analizados del contenido del archivo.
     */
    static Map<String, String> readDataFile (String path) {
        if (path == null || path.isBlank()) throw new IllegalArgumentException("Path cannot be blank");
        return readDataFile(new File(path));
    }

    /**
     * Lee el contenido del archivo de instrucciones especificado y lo analiza en un mapa de pares clave-valor.
     * Se espera que el contenido del archivo tenga secciones separadas por el carácter `!`, donde cada sección
     * contiene un par clave-valor con el formato `clave:^valor$`.
     * Format:
     * {@code !key: ^value$}
     *
     * @param file El archivo a leer y analizar.
     * @return Un mapa que contiene los pares clave-valor analizados del contenido del archivo.
     */
    static Map<String, String> readDataFile (File file) {
        String content = FileUtilHandler.readFile(file);

        String[] splittedContent = content.replaceAll("(?s)/\\*.*?\\*/", "")
                .trim()
                .split("¡");

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
     * Writes the provided data map to a file. Each key-value pair in the map is formatted
     * as {@code !key:^value$} and saved to the specified file path.
     *
     * @param dataMap A map containing the data to be written to the file. Each key-value pair
     *                represents an entry to be saved. If null, the method does nothing.
     * @param path The path of the target file where the data will be written. If blank, the method does nothing.
     */
    static void writeDataFile (Map<String, String> dataMap, String path) {
        if (path == null || path.isBlank()) throw new IllegalArgumentException("Path cannot be blank");
        writeDataFile(dataMap, new File(path));
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
    static void writeDataFile (Map<String, String> dataMap, File file) {
        if (dataMap == null) throw new IllegalArgumentException("Data map cannot be null");

        String ls = System.lineSeparator();
        StringBuilder sb = new StringBuilder();

        for (var e : new java.util.TreeMap<>(dataMap).entrySet()) {
            String key = e.getKey();
            String value = getString(e, key);

            sb.append('¡')
                    .append(key)
                    .append(':').append(ls)
                    .append('^')
                    .append(value)
                    .append('~').append(ls).append(ls);
        }

        FileUtilHandler.writeFile(file, sb.toString().trim());
    }

    /**
     * Retrieves the value from the specified map entry if the provided key and value are valid.
     * Validity is determined by ensuring neither the key nor the value is null, and neither contains
     * any reserved delimiter characters: '¡', ':', '^', '~'.
     *
     * @param e The map entry containing the key-value pair.
     * @param key The key to be validated alongside the value of the map entry.
     * @return The value associated with the map entry if both the key and value are valid.
     * @throws NullPointerException If either the provided key or the map entry's value is null.
     * @throws IllegalArgumentException If the key or value contains reserved delimiter characters.
     */
    private static String getString(Map.Entry<String, String> e, String key) {
        if (e == null) throw new NullPointerException("Map entry cannot be null");

        String value = e.getValue();

        if (key == null || value == null) {
            throw new NullPointerException("Key or value is null");
        }

        if (key.indexOf('¡') >= 0 || key.indexOf(':') >= 0 ||
                key.indexOf('^') >= 0 || key.indexOf('~') >= 0 ||
                value.indexOf('¡') >= 0 || value.indexOf(':') >= 0 ||
                value.indexOf('^') >= 0 || value.indexOf('~') >= 0
        ) throw new IllegalArgumentException("Key/value contains reserved delimiter characters: '¡', ':', '^', '~'");
        return value;
    }

    /**
     * Writes example data to a file specified by its path. The data includes a comment section
     * and syntax-related information for understanding the format. If the provided path is blank,
     * the method does nothing.
     *
     * @param path The file path as a string where example data will be written. If the path is blank, the operation is skipped.
     */
    static void writeExampleDataFile (String path) {
        if (path == null || path.isBlank()) throw new IllegalArgumentException("Path cannot be blank");
        writeExampleDataFile(new File(path));
    }

    /**
     * Writes example data to the specified file. The content includes a predefined
     * template with syntax-related information and format guidance.
     * This method does nothing if the provided file is null.
     *
     * @param file The file to which the example data will be written. If null, no action is taken.
     */
    static void writeExampleDataFile (File file) {
        String example = """
                ¡<key>:
                ^<value>~
                
                /*comment*/
                /*
                ¡ -> Data separator
                ^ -> Value start
                ~ -> Value end
                */
                """.trim();

        FileUtilHandler.writeFile(file, example);
    }
}
