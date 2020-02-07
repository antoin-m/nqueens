package ninja.michelantoine.nqueens.rule;

import ninja.michelantoine.nqueens.board.Board;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NoNInLineRuleTest {
    @Test
    public void shouldInvalidate3InLineDiagonalUpLeft() {
        NoNInLineRule rule = new NoNInLineRule(3);
        Board board = Board.fromSize(8);

        board.putQueenAt(0, 0);
        assertEquals(0, rule.conflicts(board, 0, 0));

        board.putQueenAt(1, 2);
        assertEquals(0, rule.conflicts(board, 1, 2));

        board.putQueenAt(2, 4);
        assertEquals(1, rule.conflicts(board, 2, 4));
    }

    @Test
    public void shouldInvalidate4InLineDiagonalUpRight() {
        NoNInLineRule rule = new NoNInLineRule(4);
        Board board = Board.fromSize(16);

        board.putQueenAt(0, 15);
        board.putQueenAt(1, 0);
        assertEquals(0, rule.conflicts(board, 0, 15));

        board.putQueenAt(2, 12);
        board.putQueenAt(3, 0);
        assertEquals(0, rule.conflicts(board, 2, 12));

        board.putQueenAt(4, 9);
        board.putQueenAt(5, 0);
        assertEquals(0, rule.conflicts(board, 4, 9));

        board.putQueenAt(6, 0);
        board.putQueenAt(7, 0);
        board.putQueenAt(8, 3);
        assertEquals(1, rule.conflicts(board, 8, 3));
    }

    @Test
    public void shouldFindAllConflicts() {
        NoNInLineRule rule = new NoNInLineRule(3);
        Board board = Board.fromSize(16);

        board.putQueenAt(0, 1);
        board.putQueenAt(8, 1);

        board.putQueenAt(2, 2);
        board.putQueenAt(6, 2);

        board.putQueenAt(4, 3);
        assertEquals(2, rule.conflicts(board, 4, 3));
    }
}
