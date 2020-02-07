package ninja.michelantoine.nqueens.rule;

import ninja.michelantoine.nqueens.board.Board;

/**
 * The base rule for an n queens problem, queens should not be able to see each other
 * As a reminder queens can see everything on the same row, column and diagonal:
 * https://en.wikipedia.org/wiki/Queen_(chess)
 */
public class NoQueenCanAttackAnotherRule implements Rule {
    @Override
    public int conflicts(Board board, int y, int x) {
        return (int) board.queens().stream().mapToInt(Board.Point::getX).filter(i -> i == x).count()
                + board.onDiagonals(y, x)
                - 3 // Because the current queen is found 3 times (twice on the diagonals and once on the column)
                ;
    }
}
