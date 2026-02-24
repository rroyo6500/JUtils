package rroyo.JUtils.Utils;

import java.io.*;

/**
 * The FileUtilHandler interface provides utility methods for reading from and writing to files.
 * It includes functions to handle file operations such as reading file contents, writing to files,
 * and appending data to files.
 *
 * Methods in this interface handle validation of input parameters (e.g., file existence, readability,
 * and writability) and throw appropriate exceptions when invalid arguments are provided.
 *
 * @author _rroyo65_
 */
public interface FileUtilHandler {

    // Read

    /**
     * Reads the contents of a file specified by its path and returns it as a String.
     *
     * @param src The path to the file to read. It must not be null, empty, or blank.
     * @return The content of the file as a String.
     * @throws IllegalArgumentException if the source path is null, blank, or does not resolve to a valid file.
     */
    static String readFile(String src) {
        if (src == null || src.isBlank()) throw new IllegalArgumentException("Source path cannot be blank");
        return readFile(new File(src));
    }

    /**
     * Reads the entire content of the specified file and returns it as a String.
     *
     * @param file The file to read. It must not be null, must exist, must not be a directory,
     *             must be a regular file, and must be readable.
     * @return The content of the file as a String. The returned string is trimmed of any trailing whitespace.
     * @throws IllegalArgumentException if the file is null, does not exist, is a directory,
     *                                  is not a regular file, or cannot be read.
     */
    static String readFile(File file) {
        if (file == null) throw new IllegalArgumentException("File cannot be null");
        if (!file.exists()) throw new IllegalArgumentException("File does not exist");
        if (file.isDirectory()) throw new IllegalArgumentException("File cannot be a directory");
        if (!file.isFile()) throw new IllegalArgumentException("File is not a regular file");
        if (!file.canRead()) throw new IllegalArgumentException("File cannot be read");

        String line, text = "";
        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader(file));
            while ((line = br.readLine()) != null)
                text += line + "\n";
            br.close();
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: File not found [" + file.getAbsolutePath() + "]");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return text.trim();
    }

    // Write

    /**
     * Writes a message to a file specified by its path. If the file does not exist,
     * it will be created. Existing contents of the file will be replaced.
     *
     * @param src The path of the file where the message should be written. It must not be null, empty, or blank.
     * @param msg The message to be written to the file. It must not be null.
     * @throws IllegalArgumentException if the source path is null, blank, or invalid.
     */
    static void writeFile(String src, String msg) {
        if (src == null || src.isBlank()) throw new IllegalArgumentException("Source path cannot be blank");
        writeFile(new File(src), msg, false);
    }

    /**
     * Writes a message to a file specified by its path. If the file does not exist, it will be created.
     * Optionally appends the message to the file if the append flag is set to true.
     *
     * @param src The path of the file where the message should be written. It must not be null, empty, or blank.
     * @param msg The message to be written to the file. It must not be null.
     * @param append A flag indicating whether the message should be appended to the file. If false, the file's
     *               existing content (if any) will be overwritten.
     * @throws IllegalArgumentException if the source path is null, blank, or invalid.
     */
    static void writeFile(String src, String msg, boolean append) {
        if (src == null || src.isBlank()) throw new IllegalArgumentException("Source path cannot be blank");
        writeFile(new File(src), msg, append);
    }

    /**
     * Writes a message to the specified file. If the file does not exist, it will be created.
     * The message will overwrite any existing content in the file.
     *
     * @param file The file where the message will be written. It must not be null and must not be a directory.
     * @param msg The message to be written to the file. It must not be null.
     * @throws IllegalArgumentException if the file is null or is a directory.
     * @throws RuntimeException if an I/O error occurs during the file operation.
     */
    static void writeFile(File file, String msg) {
        writeFile(file, msg, false);
    }

    /**
     * Writes a message to the specified file. If the file does not exist, it will be created.
     * Optionally appends the message to the file if the append flag is set to true.
     * The file must not be null or a directory.
     *
     * @param file The file where the message will be written. It must not be null and must not be a directory.
     * @param msg The message to be written to the file. It must not be null.
     * @param append A flag indicating whether the message should be appended to the file.
     *               If false, the existing content of the file (if any) will be overwritten.
     * @throws IllegalArgumentException if the file is null or is a directory.
     * @throws RuntimeException if an I/O error occurs during the file operation.
     */
    static void writeFile(File file, String msg, boolean append) {
        if (file == null) throw new IllegalArgumentException("File cannot be null");
        if (file.isDirectory()) throw new IllegalArgumentException("File cannot be a directory");

        try {
            if (file.createNewFile()) System.out.println("File created: " + file.getName());
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, append));
            bw.write(msg);
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
