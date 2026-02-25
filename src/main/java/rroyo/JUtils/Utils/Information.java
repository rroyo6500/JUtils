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
     * The default background color for the Information class.
     * This color is used as the base background for the UI components
     * unless explicitly overridden by the constructor or other methods.
     * It is set to {@code Color.WHITE} by default.
     */
    public static final Color backgroundColor = Color.WHITE;
    /**
     * Represents the foreground color used within the {@code Information} class for rendering text
     * or visual elements. This color is typically applied to ensure readability and visual consistency
     * in the user interface. The default value is {@code Color.BLACK}.
     */
    public static final Color foregroundColor = Color.BLACK;
    /**
     * The default font used for rendering content within the Information class.
     * This font is a plain 12-point Arial font, providing a simple and
     * standardized appearance for textual elements.
     */
    public static final Font defaultFont = new Font("Arial", Font.PLAIN, 12);

    /**
     * Constructs an Information window with a grid layout of text contents and a title.
     * The window's appearance is customizable with specified fonts and colors.
     *
     * @param title the title of the information window; cannot be null
     * @param contents the array of text content to display in a grid; cannot be null or empty
     * @param rows the number of rows in the grid layout; must be greater than 0
     * @param columns the number of columns in the grid layout; must be greater than 0
     * @param titleFont the font to use for the title; if null, a default font will be used
     * @param contentFont the font to use for the content; if null, a default font will be used
     * @param backgroundColor the background color of the window; if null, a default color will be used
     * @param foregroundColor the foreground color (text color) of the window; if null, a default color will be used
     * @throws IllegalArgumentException if title is null
     * @throws IllegalArgumentException if contents is null or empty
     * @throws IllegalArgumentException if rows or columns is less than 1
     * @throws IllegalArgumentException if rows * columns does not equal the length of contents
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
        if (title == null) throw new IllegalArgumentException("Title cannot be null");
        if (contents == null) throw new IllegalArgumentException("Contents cannot be null");
        if (contents.length == 0) throw new IllegalArgumentException("Contents cannot be empty");
        if (rows < 1) throw new IllegalArgumentException("Rows must be greater than 0");
        if (columns < 1) throw new IllegalArgumentException("Columns must be greater than 0");
        if (titleFont == null) titleFont = Information.defaultFont;
        if (contentFont == null) contentFont = Information.defaultFont;
        if (backgroundColor == null) backgroundColor = Information.backgroundColor;
        if (foregroundColor == null) foregroundColor = Information.foregroundColor;

        if (rows * columns != contents.length) {
            throw new IllegalArgumentException(
                    "rows*columns (" + (rows * columns) + ") must match contents.length (" + contents.length + ")"
            );
        }

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle("Information: " + title);

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
    }

    /**
     * Constructs an Information instance with a single content value and customizable appearance.
     *
     * @param title the title of the information window; cannot be null
     * @param content the single text content to display in the window; cannot be null
     * @param titleFont the font to use for the title; if null, a default font will be used
     * @param contentFont the font to use for the content; if null, a default font will be used
     * @param backgroundColor the background color of the window; if null, a default color will be used
     * @param foregroundColor the foreground color (text color) of the window; if null, a default color will be used
     * @throws IllegalArgumentException if title is null
     * @throws IllegalArgumentException if content is null
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
     * Constructs an Information instance with a single content value using default fonts and colors.
     *
     * @param title the title of the information window; cannot be null
     * @param content the single text content to display in the window; cannot be null
     * @throws IllegalArgumentException if title is null
     * @throws IllegalArgumentException if content is null
     */
    public Information(String title, String content) {
        this(title, content, defaultFont, defaultFont, backgroundColor, foregroundColor);
    }

    /**
     * Constructs an Information instance with a grid layout of text contents and a title,
     * using default fonts and colors for appearance customization.
     *
     * @param title the title of the information window; cannot be null
     * @param contents the array of text content to display in a grid; cannot be null or empty
     * @param rows the number of rows in the grid layout; must be greater than 0
     * @param columns the number of columns in the grid layout; must be greater than 0
     * @throws IllegalArgumentException if title is null
     * @throws IllegalArgumentException if contents is null or empty
     * @throws IllegalArgumentException if rows or columns is less than 1
     * @throws IllegalArgumentException if rows * columns does not equal the length of contents
     */
    public Information(String title, String[] contents, int rows, int columns) {
        this(title, contents, rows, columns, defaultFont, defaultFont, backgroundColor, foregroundColor);
    }

    public void open () {
        super.setVisible(true);
    }

    public void close () {
        super.setVisible(false);
    }

}