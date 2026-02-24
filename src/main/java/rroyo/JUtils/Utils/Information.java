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
public class Information extends JFrame implements DataFileUtil {

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

        JTextArea contentLabel = new JTextArea(content);
        contentLabel.setEditable(false);
        contentLabel.setFont(contentFont.deriveFont(12f));
        contentLabel.setBackground(backgroundColor);
        contentLabel.setForeground(foregroundColor);
        contentLabel.setFocusable(false);
        contentLabel.setOpaque(true);

        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        center.setBackground(backgroundColor);
        center.add(contentLabel);
        root.add(center, BorderLayout.CENTER);

        int titleWidth = titleLabel.getPreferredSize().width;
        int contentWidth = contentLabel.getPreferredSize().width;
        int windowInnerWidth = Math.max(titleWidth, contentWidth);

        Dimension titlePref = titleLabel.getPreferredSize();
        titleLabel.setPreferredSize(new Dimension(windowInnerWidth, titlePref.height));

        Dimension centerPref = center.getPreferredSize();
        center.setPreferredSize(new Dimension(windowInnerWidth, centerPref.height));

        pack();
        setMaximumSize(new Dimension(1500, 800));
        setLocationRelativeTo(null);
        setVisible(true);
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
        this(title, content, defaultFont, defaultFont, backgroundColor, foregroundColor);
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
        this(data.get(titleKey), data.get(contentKey));
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
        this(data.get(titleKey), data.get(contentKey), titleFont, contentFont, backgroundColor, foregroundColor);
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
        this(data.get(titleKey), data.get(contentKey), titleFont, contentFont, backgroundColor, foregroundColor);
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

}
