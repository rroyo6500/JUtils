package rroyo.JUtils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a generic board structure with a fixed width and height.
 * Each position on the board holds a value of type T. The board can be modified
 * by setting specific positions to new values and supports operations like clearing
 * and retrieving its content.
 *
 * @author _rroyo65_
 *
 * @param <T> The type of the elements stored in the board.
 */
public class Board <T> {

    /**
     * Represents a two-dimensional grid structure where each element of the board
     * is stored as a list of rows, and each row is represented as a list of elements
     * of type {@code T}.
     *
     * The board is implemented as a list of lists to allow flexible access and
     * manipulation of the elements. Each sub-list corresponds to a row in the grid.
     *
     * This variable is the primary data structure for holding the state of the board
     * and supports operations defined in the containing class for interacting with
     * its elements.
     *
     * The board is initialized as an empty {@code ArrayList} and is populated using
     * the methods provided in the containing class.
     */
    private final List<List<T>> board = new ArrayList<>();
    /**
     * Represents the default value of type {@code T} used to initialize each cell in the board.
     * This value is assigned to all positions when the board is created and acts as a fallback
     * for out-of-bound access or positions that have not been explicitly updated.
     */
    public final T base;

    /**
     * Represents the number of columns in the board.
     * This variable defines the width of the board and is used to determine
     * the horizontal size of the grid structure.
     */
    public final int width;
    /**
     * Represents the height of a board, which indicates the number of rows it contains.
     * This value is immutable and determines the vertical dimension of the board.
     */
    public final int height;

    /**
     * Creates a new board with the specified width, height, and default base value for all positions.
     * Each cell of the board is initialized with the provided base value.
     *
     * @param width The number of columns in the board.
     * @param height The number of rows in the board.
     * @param base The default value of type {@code T} to initialize each cell in the board.
     */
    public Board (int width, int height, T base) {
        this.base = base;
        this.width = width;
        this.height = height;
        for(int i = 0; i < height; i++){
            board.add(new ArrayList<>());
            for(int j = 0; j < width; j++){
                board.getLast().add(base);
            }
        }
    }

    /**
     * Constructs a new board by copying the contents and dimensions of another board.
     * Each cell in the new board is initialized with the value from the corresponding
     * cell in the provided board.
     *
     * @param other The board to copy from. This parameter must not be null.
     */
    public Board(Board<T> other) {
        this.base = other.base;
        this.width = other.width;
        this.height = other.height;

        for (int y = 0; y < height; y++) {
            List<T> row = new ArrayList<>(width);
            for (int x = 0; x < width; x++) {
                row.add(other.getPos(x, y));
            }
            board.add(row);
        }
    }

    /**
     * Updates the value of a specified position on the board.
     * If the given coordinates are outside the boundaries of the board,
     * no action is performed.
     *
     * @param x The x-coordinate of the position to update.
     * @param y The y-coordinate of the position to update.
     * @param newValue The new value of type {@code T} to be set at the specified position.
     */
    public void setPos (int x, int y, T newValue) {
        if (x < 0 || x >= width || y < 0 || y >= height) return;
        board.get(y).set(x, newValue);
    }

    /**
     * Retrieves the value at the specified position on the board.
     * If the position falls outside the boundaries of the board,
     * the default base value is returned.
     *
     * @param x The x-coordinate of the position to retrieve.
     * @param y The y-coordinate of the position to retrieve.
     * @return The value of type {@code T} at the specified position,
     *         or the base value if the position is out of bounds.
     */
    public T getPos (int x, int y){
        if (x < 0 || x >= width || y < 0 || y >= height) return base;
        return board.get(y).get(x);
    }

    /**
     * Clears the internal board data structure, removing all stored rows and their elements.
     * After calling this method, the board will be empty, and no elements will remain.
     */
    public void clear () {
        board.clear();
    }

    /**
     * Retrieves a copy of the current board as a list of lists, where each inner list represents a row
     * of the board, and the elements within the lists represent the values of type {@code T} at each position.
     * The board is copied to ensure modifications to the returned list do not affect the internal state
     * of the original board.
     *
     * @return A deep copy of the board as a {@code List<List<T>>}.
     */
    public List<List<T>> getBoard () {
        return new Board<>(this).board;
    }

    /**
     * Prints the current state of the board to the standard output.
     * Each row of the board is printed on a new line, using the {@code toString()}
     * method of the elements to represent their values.
     */
    public void print () {
        for (List<T> ts : board) {
            System.out.println(ts);
        }
    }

}


