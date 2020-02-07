package ninja.michelantoine.nqueens.engine;

import ninja.michelantoine.nqueens.board.Board;
import ninja.michelantoine.nqueens.rule.Rule;

import java.util.List;

/**
 * Delegates to another engine depending on the size of the board
 * The back tracking algorithm is faster than the repairing one on smaller boards
 */
class DelegatingEngine implements NQueensEngine {
    private final long timeout;

    public DelegatingEngine(long timeout) {
        this.timeout = timeout;
    }

    @Override
    public Board solve(int size, List<Rule> rules) {
        if (0 >= size) {
            throw new IllegalArgumentException("size should be > 0");
        } else if (16 >= size) {
            return new BackTrackingEngine(timeout).solve(size, rules);
        } else {
            return new ConstraintEngine(timeout).solve(size, rules);
        }
    }
}
