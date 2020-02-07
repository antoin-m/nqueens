package ninja.michelantoine.nqueens.board;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class BoardInterfaceTest {
    @Parameterized.Parameters
    public static Collection<Object[]> boards() {
        return singletonList(
                new Object[] { new ArrayBoard(8) }
        );
    }

    private final Board board;

    public BoardInterfaceTest(Board board) {
        this.board = board;
    }

    @Test
    public void shouldBeSized() {
        assertEquals(8, board.size());
    }

    @Test
    public void shouldBeAbsentByDefault() {
        assertEquals(Board.QueenPresence.ABSENT, board.at(1, 5));
    }

    @Test
    public void shouldAddQueen() {
        board.putQueenAt(1, 5);

        assertEquals(Board.QueenPresence.PRESENT, board.at(1, 5));
    }

    @Test
    public void shouldRemoveQueen() {
        board.putQueenAt(1, 5);
        board.removeQueenAt(1, 5);

        assertEquals(Board.QueenPresence.ABSENT, board.at(1, 5));
    }

    @Test
    public void shouldListAllQueens() {
        board.putQueenAt(1, 5);
        board.putQueenAt(0, 4);
        board.putQueenAt(2, 3);
        board.removeQueenAt(2, 3);

        assertThat(board.queens(), containsInAnyOrder(
                new Board.Point(1, 5),
                new Board.Point(0, 4)
        ));
    }
}
