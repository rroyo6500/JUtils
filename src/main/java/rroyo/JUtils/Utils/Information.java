package rroyo.JUtils.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.Map;

/**
 * Represents a styled information window that displays a title and content text.
 * The class provides constructors with flexible styling options, including fonts
 * and colors, and supports initialization from key-value maps or data files.
 * The window is designed with a consistent aesthetic theme and functionality to
 * visually present information to the user.
 *
 * This class extends {@code JFrame} to inherit basic windowing behavior and
 * implements {@code DataFileUtil} for file-related utility functionality.
 *
 * @author _rroyo65_
 *
 * @see DataFileUtil
 * @see JFrame
 * @implNote Requires {@code DataFileUtil} to work.
 */
public final class Information extends JFrame {

    /**
     * Represents the background color of the application or component,
     * used to provide a consistent visual appearance.
     *
     * This variable is immutable and initialized to a default color value.
     * The selected color ensures improved readability and aesthetic design
     * for UI elements.
     *
     * Potentially used in UI rendering components to maintain a uniform
     * background style across different visual elements.
     */
    public static final Color backgroundColor = Color.WHITE;
    /**
     * Represents the color used for the foreground elements in the user interface.
     * This variable is immutable and serves as the default color for textual or visual
     * content rendered in the foreground of the application window.
     *
     * The value is a constant and is initially set to {@code Color.BLACK}.
     * It may be utilized by various components of the application to ensure consistency
     * in the visual design and user experience.
     */
    public static final Color foregroundColor = Color.BLACK;
    /**
     * Represents the default font used throughout the application for text rendering.
     * This font is defined as "Arial" with a plain style and a size of 12 points.
     * It provides a consistent appearance for textual content and ensures readability.
     * Being final and static, the font cannot be modified and is shared across instances
     * of the containing class.
     */
    public static final Font defaultFont = new Font("Arial", Font.PLAIN, 12);

    /**
     * Constructs a new Information window displaying a title and a set of contents
     * in a specified layout and style. This window is not resizable and is
     * displayed centered relative to the screen.
     *
     * @param title The title text to be displayed at the top of the window.
     * @param contents An array of strings representing the contents to be displayed
     *                 below the title.
     * @param rows The number of rows in the layout.
     * @param columns The number of columns in the layout.
     * @param titleFont The font to be used for the title text.
     * @param contentFont The font to be used for the content text.
     * @param backgroundColor The background color of the window and its components.
     * @param foregroundColor The foreground (text) color of the title and contents.
     */
    public Information(
            String title,
            String[] contents,
            int rows,
            int columns,
            Font titleFont,
            Font contentFont,
            Color backgroundColor,
            Color foregroundColor
    ) {
        if (rows * columns != contents.length) {
            throw new IllegalArgumentException(
                    "rows*columns (" + (rows * columns) + ") must match contents.length (" + contents.length + ")"
            );
        }

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle("Information");

        int paddingX = 50;
        int paddingY = 20;
        int titleGap = 10;

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(backgroundColor);
        root.setBorder(new EmptyBorder(paddingY, paddingX / 2, paddingY, paddingX / 2));
        setContentPane(root);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(titleFont.deriveFont(30f));
        titleLabel.setForeground(foregroundColor);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(new EmptyBorder(0, 0, titleGap, 0));
        root.add(titleLabel, BorderLayout.NORTH);

        int contentGap = 10;
        JPanel contentsPanel = new JPanel();
        contentsPanel.setLayout(new GridLayout(rows, columns, contentGap, contentGap));
        contentsPanel.setBackground(backgroundColor);

        for (String s : contents) {
            JTextArea contentArea = new JTextArea(s);
            contentArea.setEditable(false);
            contentArea.setFont(contentFont.deriveFont(12f));
            contentArea.setBackground(backgroundColor);
            contentArea.setForeground(foregroundColor);
            contentArea.setFocusable(false);
            contentArea.setOpaque(true);

            contentsPanel.add(contentArea);
        }

        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        center.setBackground(backgroundColor);
        center.add(contentsPanel);
        root.add(center, BorderLayout.CENTER);

        int titleWidth = titleLabel.getPreferredSize().width;
        int contentWidth = contentsPanel.getPreferredSize().width;
        int windowInnerWidth = Math.max(titleWidth, contentWidth);

        Dimension titlePref = titleLabel.getPreferredSize();
        titleLabel.setPreferredSize(new Dimension(windowInnerWidth, titlePref.height));

        Dimension centerPref = center.getPreferredSize();
        center.setPreferredSize(new Dimension(windowInnerWidth, centerPref.height));

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    /**
     * Constructs an Information window with the specified title, content, fonts, and colors.
     * This window displays a title and content text within a styled user interface.
     *
     * @param title            the title text to be displayed at the top of the window.
     * @param content          the main content text to be displayed in the window.
     * @param titleFont        the font used to style the title text.
     * @param contentFont      the font used to style the content text.
     * @param backgroundColor  the background color of the window and its components.
     * @param foregroundColor  the foreground color of the title and content text.
     */
    public Information(
            String title,
            String content,
            Font titleFont,
            Font contentFont,
            Color backgroundColor,
            Color foregroundColor
    ) {
        this(title, new String[]{content}, 1, 1, titleFont, contentFont, backgroundColor, foregroundColor);
    }

    /**
     * Constructs an Information window with the specified title and content.
     * This constructor initializes the window with default fonts and colors.
     * The window displays the provided title and content within a styled user interface.
     *
     * @param title   the title text to be displayed at the top of the window.
     * @param content the main content text to be displayed in the window.
     */
    public Information(String title, String content) {
        this(title, new String[]{content}, 1, 1, defaultFont, defaultFont, backgroundColor, foregroundColor);
    }

    /**
     * Constructs an Information object with the specified title, contents, rows, and columns.
     * Other attributes are initialized with default values for fonts and colors.
     *
     * @param title the title of the information panel
     * @param contents an array of strings representing the textual content
     * @param rows the number of rows in the information panel
     * @param columns the number of columns in the information panel
     */
    public Information(String title, String[] contents, int rows, int columns) {
        this(title, contents, rows, columns, defaultFont, defaultFont, backgroundColor, foregroundColor);
    }

    /**
     * Constructs an Information window using data from a map and specified keys to retrieve the title and content.
     * This constructor initializes the window with default fonts and colors.
     * The window displays the title and content retrieved from the provided map.
     *
     * @param data       the map containing key-value pairs for the title and content text.
     * @param titleKey   the key to retrieve the title text from the map.
     * @param contentKey the key to retrieve the content text from the map.
     * @throws IllegalArgumentException if the provided map is null.
     */
    public Information(Map<String,String> data, String titleKey, String contentKey) {
        if (data == null) throw new IllegalArgumentException("Data cannot be null");
        if (data.get(titleKey) == null || data.get(contentKey) == null)
            throw new IllegalArgumentException("Data cannot be null");
        this(data.get(titleKey), data.get(contentKey));
    }

    /**
     * Constructs an Information object using specified parameters. This constructor
     * initializes the object with a title, contents, and a specific grid configuration
     * (rows and columns).
     *
     * @param data       A map containing key-value pairs where the key represents the
     *                   title or content identifiers, and the value represents the
     *                   corresponding data.
     * @param titleKey   The key used to retrieve the title from the data map.
     * @param contentKeys An array of keys to retrieve content values from the data map.
     * @param rows       The number of rows for the grid configuration.
     * @param columns    The number of columns for the grid configuration.
     * @throws IllegalArgumentException if the data map is null.
     */
    public Information(Map<String,String> data, String titleKey, String[] contentKeys, int rows, int columns) {
        if (data == null) throw new IllegalArgumentException("Data cannot be null");
        String[] contents = new String[contentKeys.length];
        for (int i = 0; i < contentKeys.length; i++) {
            if (data.get(titleKey) == null || data.get(contentKeys[i]) == null)
                throw new IllegalArgumentException("Data cannot be null");
            contents[i] = data.get(contentKeys[i]);
        }
        this(data.get(titleKey), contents, rows, columns);
    }

    /**
     * Constructs an Information window using data from a map along with specific styling and color customization.
     * This constructor initializes the window with title and content retrieved from the provided map keys,
     * and styles the window using the specified fonts and colors.
     *
     * @param data             the map containing key-value pairs for the title and content text.
     *                         Must not be null.
     * @param titleKey         the key to retrieve the title text from the map.
     * @param contentKey       the key to retrieve the content text from the map.
     * @param titleFont        the font used to style the title text.
     * @param contentFont      the font used to style the content text.
     * @param backgroundColor  the background color of the window and its components.
     * @param foregroundColor  the foreground color of the title and content text.
     * @throws IllegalArgumentException if the provided map is null.
     */
    public Information(
            Map<String, String> data,
            String titleKey,
            String contentKey,
            Font titleFont,
            Font contentFont,
            Color backgroundColor,
            Color foregroundColor
    ) {
        if (data == null) throw new IllegalArgumentException("Data cannot be null");
        if (data.get(titleKey) == null || data.get(contentKey) == null)
            throw new IllegalArgumentException("Data cannot be null");
        this(data.get(titleKey), data.get(contentKey), titleFont, contentFont, backgroundColor, foregroundColor);
    }

    /**
     * Constructs an Information instance with the specified properties and data mappings.
     *
     * @param data            A map containing key-value pairs where keys are used to retrieve
     *                        title and content values.
     * @param titleKey        The key used to fetch the title string from the data map.
     * @param contentKeys     An array of keys used to fetch content strings from the data map.
     * @param rows            The number of rows to be used for layout or display purposes.
     * @param columns         The number of columns to be used for layout or display purposes.
     * @param titleFont       The font used for rendering the title.
     * @param contentFont     The font used for rendering the content.
     * @param backgroundColor The background color of the Information display.
     * @param foregroundColor The foreground (text) color of the Information display.
     * @throws IllegalArgumentException If the data map is null.
     */
    public Information(
            Map<String, String> data,
            String titleKey,
            String[] contentKeys,
            int rows,
            int columns,
            Font titleFont,
            Font contentFont,
            Color backgroundColor,
            Color foregroundColor
    ) {
        if (data == null) throw new IllegalArgumentException("Data cannot be null");
        String[] contents = new String[contentKeys.length];
        for (int i = 0; i < contentKeys.length; i++) {
            if (data.get(titleKey) == null || data.get(contentKeys[i]) == null)
                throw new IllegalArgumentException("Data cannot be null");
            contents[i] = data.get(contentKeys[i]);
        }
        this(data.get(titleKey), contents, rows, columns, titleFont, contentFont, backgroundColor, foregroundColor);
    }

    /**
     * Constructs an Information window with data loaded from a file and styled accordingly.
     * This constructor retrieves the title and content text using keys from the specified file
     * and initializes the window with the provided fonts and colors.
     *
     * @param file             the data file containing key-value pairs for the title and content text.
     * @param titleKey         the key to retrieve the title text from the data file.
     * @param contentKey       the key to retrieve the content text from the data file.
     * @param titleFont        the font used to style the title text.
     * @param contentFont      the font used to style the content text.
     * @param backgroundColor  the background color of the window and its components.
     * @param foregroundColor  the foreground color of the title and content text.
     */
    public Information(
            File file,
            String titleKey,
            String contentKey,
            Font titleFont,
            Font contentFont,
            Color backgroundColor,
            Color foregroundColor
    ) {
        Map<String, String> data = DataFileUtil.readDataFile(file);
        if (data.get(titleKey) == null || data.get(contentKey) == null)
            throw new IllegalArgumentException("Data cannot be null");
        this(data.get(titleKey), data.get(contentKey), titleFont, contentFont, backgroundColor, foregroundColor);
    }

    /**
     * Constructs an Information object using the specified parameters.
     *
     * @param file             The file containing data to initialize the Information object.
     * @param titleKey         The key used to retrieve the title from the data.
     * @param contentKeys      An array of keys used to retrieve content from the data.
     * @param rows             The number of rows for the information layout.
     * @param columns          The number of columns for the information layout.
     * @param titleFont        The font used for the title text.
     * @param contentFont      The font used for the content text.
     * @param backgroundColor  The background color of the information layout.
     * @param foregroundColor  The foreground color (typically text color) of the information layout.
     */
    public Information(
            File file,
            String titleKey,
            String[] contentKeys,
            int rows,
            int columns,
            Font titleFont,
            Font contentFont,
            Color backgroundColor,
            Color foregroundColor
    ) {
        Map<String, String> data = DataFileUtil.readDataFile(file);
        this(data, titleKey, contentKeys, rows, columns, titleFont, contentFont, backgroundColor, foregroundColor);
    }

    /**
     * Constructs an Information window with a specified file path, text keys, fonts, and colors.
     * This window displays a title and content loaded from the provided data file.
     *
     * @param filePath        the path to the data file containing key-value pairs for the title
     *                        and content text.
     * @param titleKey        the key to retrieve the title text from the data file.
     * @param contentKey      the key to retrieve the content text from the data file.
     * @param titleFont       the font used to style the title text.
     * @param contentFont     the font used to style the content text.
     * @param backgroundColor the background color of the window and its components.
     * @param foregroundColor the foreground color of the title and content text.
     * @throws IllegalArgumentException if the provided file path is blank or null.
     */
    public Information(
            String filePath,
            String titleKey,
            String contentKey,
            Font titleFont,
            Font contentFont,
            Color backgroundColor,
            Color foregroundColor
    ) {
        if (filePath == null || filePath.isBlank()) throw new IllegalArgumentException("File path cannot be blank");
        this(new File(filePath), titleKey, contentKey, titleFont, contentFont, backgroundColor, foregroundColor);
    }

    /**
     * Constructs an Information instance using the specified file path and other details.
     * Validates that the file path is not null or blank before proceeding.
     * Delegates initialization to another constructor that accepts a File object.
     *
     * @param filePath the path to the file as a string; must not be null or blank
     * @param titleKey the key used to retrieve the title
     * @param contentKeys an array of keys used to retrieve the content
     * @param rows the number of rows to be used in the associated structure
     * @param columns the number of columns to be used in the associated structure
     * @param titleFont the Font object used for styling the title
     * @param contentFont the Font object used for styling the content
     * @param backgroundColor the Color object used for the background
     * @param foregroundColor the Color object used for the foreground
     */
    public Information(
            String filePath,
            String titleKey,
            String[] contentKeys,
            int rows,
            int columns,
            Font titleFont,
            Font contentFont,
            Color backgroundColor,
            Color foregroundColor
    ) {
        if (filePath == null || filePath.isBlank()) throw new IllegalArgumentException("File path cannot be blank");
        this(new File(filePath), titleKey, contentKeys, rows, columns, titleFont, contentFont, backgroundColor, foregroundColor);
    }

    /**
     * Constructs an Information window with a specified file, title key, and content key.
     * This constructor initializes the window with default fonts and colors.
     * The window displays a title and content loaded from the provided data file.
     *
     * @param file      the data file containing key-value pairs for the title and content text.
     * @param titleKey  the key to retrieve the title text from the data file.
     * @param contentKey the key to retrieve the content text from the data file.
     */
    public Information(File file, String titleKey, String contentKey) {
        this(file, titleKey, contentKey, defaultFont, defaultFont, backgroundColor, foregroundColor);
    }

    /**
     * Constructs an Information object with the specified parameters.
     *
     * @param file        the file to be processed.
     * @param titleKey    the key used to identify the title.
     * @param contentKeys an array of keys used to identify the content.
     * @param rows        the number of rows in the layout.
     * @param columns     the number of columns in the layout.
     */
    public Information(File file, String titleKey, String[] contentKeys, int rows, int columns) {
        this(file, titleKey, contentKeys, rows, columns, defaultFont, defaultFont, backgroundColor, foregroundColor);
    }

    /**
     * Constructs an Information window with a specified file path, title key, and content key.
     * This constructor initializes the window with default fonts and colors.
     * The window displays a title and content loaded from the provided data file.
     *
     * @param filePath   the path to the data file containing key-value pairs for the title and
     *                   content text.
     * @param titleKey   the key to retrieve the title text from the data file.
     * @param contentKey the key to retrieve the content text from the data file.
     * @throws IllegalArgumentException if the provided file path is blank or null.
     */
    public Information(String filePath, String titleKey, String contentKey) {
        if (filePath == null || filePath.isBlank()) throw new IllegalArgumentException("File path cannot be blank");
        this(new File(filePath), titleKey, contentKey);
    }

    /**
     * Constructs an Information object with the specified parameters.
     *
     * @param filePath the path to the file that contains the information; cannot be null or blank
     * @param titleKey the key used to identify the title in the data
     * @param contentKeys an array of keys used to identify the content in the data
     * @param rows the number of rows in the information data structure
     * @param columns the number of columns in the information data structure
     * @throws IllegalArgumentException if the filePath is null or blank
     */
    public Information(String filePath, String titleKey, String[] contentKeys, int rows, int columns) {
        if (filePath == null || filePath.isBlank()) throw new IllegalArgumentException("File path cannot be blank");
        this(new File(filePath), titleKey, contentKeys, rows, columns);
    }

}