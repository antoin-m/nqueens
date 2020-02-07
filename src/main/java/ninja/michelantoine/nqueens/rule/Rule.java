package ninja.michelantoine.nqueens.rule;

import ninja.michelantoine.nqueens.board.Board;
import ninja.michelantoine.nqueens.engine.NQueensEngine;

/**
 * A rule the {@link NQueensEngine} will have to follow in order to fill the {@link Board}
 */
public interface Rule {
    /**
     * Finds and returns the number of conflicts for the queen at the specific position
     * If 0 is returned then the position is valid
     * @param board the board to check
     * @param y the row of the last added queen
     * @param x the col of the last added queen
     * @return the number of conflicts for the specified queen position
     */
    int conflicts(Board board, int y, int x);
}
