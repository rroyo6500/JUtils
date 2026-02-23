package rroyo.JUtils.Utils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The CustomFonts interface provides static methods for managing a collection of custom TrueType fonts.
 * It allows adding, retrieving, and removing fonts by their respective names, using either file paths or File objects.
 *
 * @author _rroyo65_
 */
public interface CustomFonts {

    /**
     * A map that holds a collection of custom TrueType fonts, with unique string identifiers as keys and Font objects as values.
     * It serves as an internal storage for managing fonts added via the {@link CustomFonts} interface methods.
     *
     * The fonts are stored by their associated names, which can be used to retrieve or remove them from the collection.
     */
    Map<String, Font> fonts = new HashMap<>();

    /**
     * Adds a TrueType font to the internal font collection by specifying the font name,
     * the file's path to the font, and the desired font size.
     * This method internally delegates the font addition task to another implementation
     * that operates with a File object.
     *
     * @param name The identifier for the font to be added. This will be used when retrieving the font later.
     * @param path The file system path pointing to the TrueType font file.
     * @param size The desired size of the font in points.
     */
    static void addFont (String name, String path, float size) {
        if (path == null || path.isBlank()) throw new IllegalArgumentException("Path cannot be blank");
        addFont(name, new File(path), size);
    }

    /**
     * Adds a TrueType font to the internal font collection using a file object.
     * The font is identified by a unique name and can be derived to a specific size.
     *
     * @param name The identifier for the font to be added. This name will be used for retrieving the font.
     * @param file The File object pointing to the TrueType font file.
     * @param size The desired font size to derive from the base font.
     */
    static void addFont (String name, File file, float size) {
        if (file == null) throw new IllegalArgumentException("File cannot be null");
        if (!file.exists()) throw new IllegalArgumentException("File does not exist");
        if (file.isDirectory()) throw new IllegalArgumentException("File cannot be a directory");
        if (!file.isFile()) throw new IllegalArgumentException("File is not a regular file");

        try {
            fonts.put(name, Font.createFont(Font.TRUETYPE_FONT, file).deriveFont(size));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a font from the internal font collection based on its name.
     *
     * @param name The name of the font to retrieve. This name serves as the unique identifier for the font in the collection.
     * @return The Font object associated with the specified name, or null if no font with the given name is found.
     */
    static Font getFont (String name) {
        return fonts.get(name);
    }

    /**
     * Removes a font from the internal font collection based on its name.
     *
     * @param name The name of the font to remove. This name serves as the unique identifier
     *             for the font in the collection.
     */
    static void removeFont (String name) {
        fonts.remove(name);
    }

}
