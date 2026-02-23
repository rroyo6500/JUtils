package rroyo.JUtils.Utils;

import java.io.*;

/**
 * A utility class for handling file operations such as reading and writing files.
 * Includes functionality for logging file creation events and supports appending content to files.
 *
 * @author _rroyo65_
 */
public class FileUtilHandler {

    /**
     * Indicates whether logging is enabled or disabled for file operations.
     * If set to {@code true}, logs will be printed to the console for actions such as file creation.
     * If set to {@code false}, no logs will be output.
     */
    public static boolean log = true;

    // Read

    /**
     * Lee un archivo y lo devuelve como String
     * @param src Ruta del archivo
     * @return Contenido del archivo
     */
    public static String readFile(String src) {
        if (src == null || src.isBlank()) throw new IllegalArgumentException("Source path cannot be blank");
        return readFile(new File(src));
    }

    /**
     * Lee un archivo y lo devuelve como String
     * @param file Archivo a leer
     * @return Contenido del archivo
     */
    public static String readFile(File file) {
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
     * Escribe un archivo con el mensaje especificado
     * @param src Ruta del archivo
     * @param msg Mensaje a escribir
     */
    public static void writeFile(String src, String msg) {
        if (src == null || src.isBlank()) throw new IllegalArgumentException("Source path cannot be blank");
        writeFile(new File(src), msg, false);
    }

    /**
     * Escribe un archivo con el mensaje especificado
     * @param src Ruta del archivo
     * @param msg Mensaje a escribir
     * @param append Indica si se debe anexar el mensaje al final del archivo
     */
    public static void writeFile(String src, String msg, boolean append) {
        if (src == null || src.isBlank()) throw new IllegalArgumentException("Source path cannot be blank");
        writeFile(new File(src), msg, append);
    }

    /**
     * Escribe un archivo con el mensaje especificado
     * @param file Archivo a escribir
     * @param msg Mensaje a escribir
     */
    public static void writeFile(File file, String msg) {
        writeFile(file, msg, false);
    }

    /**
     * Escribe un archivo con el mensaje especificado
     * @param file Archivo a escribir
     * @param msg Mensaje a escribir
     * @param append Indica si se debe anexar el mensaje al final del archivo
     */
    public static void writeFile(File file, String msg, boolean append) {
        if (file == null) throw new IllegalArgumentException("File cannot be null");
        if (file.isDirectory()) throw new IllegalArgumentException("File cannot be a directory");

        try {
            if (file.createNewFile() && log) System.out.println("File created: " + file.getName());
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, append));
            bw.write(msg);
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
