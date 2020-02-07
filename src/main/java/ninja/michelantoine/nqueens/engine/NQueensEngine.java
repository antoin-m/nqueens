package ninja.michelantoine.nqueens.engine;

import ninja.michelantoine.nqueens.board.Board;
import ninja.michelantoine.nqueens.rule.Rule;

import java.util.List;

/**
 * An engine able to solve an N Queens problem
 */
public interface NQueensEngine {
    /**
     * Solves and returns the completed {@link Board}
     * @param size the size of the board to fill with queens
     * @param rules the rules to follow while filling the board
     * @throws NoSolutionFoundException if no solution can be found OR no solution is found in time
     * @return the completed board
     */
    Board solve(int size, List<Rule> rules);

    /**
     * Creates the default engine
     * @param timeout timeout in seconds before the engine stops looking for a solution
     * @return the default {@link NQueensEngine}
     */
    static NQueensEngine create(long timeout) {
        return new DelegatingEngine(timeout);
    }
}
