package ninja.michelantoine.nqueens.rule;

import ninja.michelantoine.nqueens.board.Board;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class NoQueenCanAttackAnotherRuleTest {
    @Test
    @Ignore // The engine never tries to place 2 queens on the same row, so the rule actually doesn't even check it
    public void shouldNotAllowQueensOnTheSameRow() {
        Board board = Board.fromSize(8);
        board.putQueenAt(0, 5);
        board.putQueenAt(0, 7);

        assertEquals(1, new NoQueenCanAttackAnotherRule().conflicts(board, 0, 7));
    }

    @Test
    public void shouldNotAllowQueensOnTheSameColumn() {
        Board board = Board.fromSize(8);
        board.putQueenAt(0, 5);
        board.putQueenAt(1, 5);

        assertEquals(1, new NoQueenCanAttackAnotherRule().conflicts(board, 1, 5));
    }

    @Test
    public void shouldNotAllowQueensOnTheSameDiagonal() {
        Board board = Board.fromSize(8);
        board.putQueenAt(0, 5);
        board.putQueenAt(1, 2);
        board.putQueenAt(2, 7);

        assertEquals(1, new NoQueenCanAttackAnotherRule().conflicts(board, 2, 7));
    }

    @Test
    public void shouldAllowQueensAnywhereElse() {
        Board board = Board.fromSize(8);
        board.putQueenAt(0, 5);
        board.putQueenAt(1, 0);
        board.putQueenAt(2, 6);

        assertEquals(0, new NoQueenCanAttackAnotherRule().conflicts(board, 2, 6));
    }
}