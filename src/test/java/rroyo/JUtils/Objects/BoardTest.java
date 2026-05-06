package rroyo.JUtils.Objects;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void constructorFillsEveryPositionWithBaseValue() {
        Board<String> board = new Board<>(3, 2, ".");

        assertEquals(3, board.width);
        assertEquals(2, board.height);
        assertEquals(".", board.base);
        assertEquals(".", board.getPos(0, 0));
        assertEquals(".", board.getPos(2, 1));
    }

    @Test
    void setAndGetUseCoordinatesAndIgnoreOutOfBoundsWrites() {
        Board<Integer> board = new Board<>(2, 2, 0);

        board.setPos(1, 0, 7);
        board.setPos(-1, 0, 9);
        board.setPos(0, 5, 9);

        assertEquals(7, board.getPos(1, 0));
        assertEquals(0, board.getPos(0, 0));
        assertEquals(0, board.getPos(-1, 0));
        assertEquals(0, board.getPos(2, 0));
    }

    @Test
    void copyConstructorCreatesIndependentRows() {
        Board<String> original = new Board<>(2, 2, ".");
        original.setPos(0, 0, "x");

        Board<String> copy = new Board<>(original);
        original.setPos(0, 0, "changed");

        assertEquals("x", copy.getPos(0, 0));
        assertEquals("changed", original.getPos(0, 0));
    }

    @Test
    void clearRemovesStoredRows() {
        Board<String> board = new Board<>(2, 2, ".");

        board.clear();

        assertTrue(board.getBoard().isEmpty());
    }

    @Test
    void getBoardReturnsCurrentInternalRows() {
        Board<String> board = new Board<>(2, 1, ".");
        List<List<String>> rows = board.getBoard();

        rows.getFirst().set(0, "external");

        assertEquals("external", board.getPos(0, 0));
    }
}
