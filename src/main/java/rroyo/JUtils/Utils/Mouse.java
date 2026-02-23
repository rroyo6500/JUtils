package rroyo.JUtils.Utils;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Provides utility methods for mouse interactions on a specified component.
 * Tracks mouse position and click state, and allows configuring components
 * to respond to mouse motion and click events.
 *
 * @author _rroyo65_
 *
 * @see Component
 */
public class Mouse {

    /**
     * Represents the current position of the mouse in a two-dimensional space.
     * The position is stored as an array of two integers, where the first element
     * represents the X-coordinate and the second element represents the Y-coordinate.
     *
     * The coordinates are relative to the top-left corner of a specific component
     * and are updated dynamically as the mouse moves within the bounds of the component.
     */
    public static final int[] mousePosition = new int[2];

    /**
     * Indicates whether a mouse click event has occurred. The value of this variable
     * is set to {@code true} when the mouse is pressed and is reset to {@code false}
     * when the mouse is released.
     *
     * This variable is globally accessible and is primarily used to track the click
     * state of the mouse for UI components or interactions that depend on mouse input.
     */
    public static boolean isClicked = false;

    /**
     * Configura el componente para detectar el movimiento del raton dentro del mismo
     * @param component Componente a configurar
     */
    public static void configureMouse(Component component) {
        if (component == null) throw new IllegalArgumentException("Component cannot be null");

        component.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                mousePosition[0] = e.getX();
                mousePosition[1] = e.getY();
            }
        });

        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                isClicked = true;
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                isClicked = false;
            }
        });
    }

    /**
     * Permite agregar varios listeners de forma manual a un componente
     * @param component Componente a configurar
     * @param ma Listener a agregar
     */
    public static void addConfig(Component component, MouseAdapter ma) {
        if (ma == null) throw new IllegalArgumentException("MouseAdapter cannot be null");
        if (component == null) throw new IllegalArgumentException("Component cannot be null");

        component.addMouseListener(ma);
        component.addMouseMotionListener(ma);
    }

}
