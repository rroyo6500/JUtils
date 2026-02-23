package rroyo.JUtils.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.Map;

/**
 * The Information class is a GUI component that displays information
 * from a data file in a window. It extends the JFrame class and
 * implements the DataFileUtil interface to utilize file-reading functionality.
 *
 * This class provides two constructors:
 * - One allows for customization of the title font, content font,
 *   background color, and foreground color.
 * - The other uses default configurations.
 *
 * Features:
 * - Displays a title and content retrieved from a data file.
 * - Supports customization of appearance, such as fonts and colors.
 * - Content is displayed in a non-editable text area.
 * - The window is non-resizable and centered on the screen.
 *
 * Usage:
 * Intended to display static information from data files,
 * such as configuration files or localized content.
 * Files must adhere to the format defined by the DataFileUtil interface.
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
     * Constructs an Information window with a specified file, text keys, fonts, and colors.
     * This window displays a title and content loaded from the provided data file.
     *
     * @param file            the data file containing key-value pairs for the title and content text.
     * @param titleKey        the key to retrieve the title text from the data file.
     * @param contentKey      the key to retrieve the content text from the data file.
     * @param titleFont       the font used to style the title text.
     * @param contentFont     the font used to style the content text.
     * @param backgroundColor the background color of the window and its components.
     * @param foregroundColor the foreground color of the title and content text.
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
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle("Information");

        Map<String, String> data = DataFileUtil.readDataFile(file);

        int paddingX = 50;
        int paddingY = 20;
        int titleGap = 10;

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(backgroundColor);
        root.setBorder(new EmptyBorder(paddingY, paddingX / 2, paddingY, paddingX / 2));
        setContentPane(root);

        JLabel title = new JLabel(data.get(titleKey));
        title.setFont(titleFont.deriveFont(30f));
        title.setForeground(foregroundColor);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(new EmptyBorder(0, 0, titleGap, 0));
        root.add(title, BorderLayout.NORTH);

        JTextArea content = new JTextArea(data.get(contentKey));
        content.setEditable(false);
        content.setFont(contentFont.deriveFont(12f));
        content.setBackground(backgroundColor);
        content.setForeground(foregroundColor);
        content.setFocusable(false);
        content.setOpaque(true);

        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        center.setBackground(backgroundColor);
        center.add(content);
        root.add(center, BorderLayout.CENTER);

        int titleWidth = title.getPreferredSize().width;
        int contentWidth = content.getPreferredSize().width;
        int windowInnerWidth = Math.max(titleWidth, contentWidth);

        Dimension titlePref = title.getPreferredSize();
        title.setPreferredSize(new Dimension(windowInnerWidth, titlePref.height));

        Dimension centerPref = center.getPreferredSize();
        center.setPreferredSize(new Dimension(windowInnerWidth, centerPref.height));

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
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
        if (filePath == null || filePath.isBlank()) throw new IllegalArgumentException("File path cannot be blank / null");
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
        if (filePath == null || filePath.isBlank()) throw new IllegalArgumentException("File path cannot be blank / null");
        this(new File(filePath), titleKey, contentKey, defaultFont, defaultFont, backgroundColor, foregroundColor);
    }

}
