package ninja.michelantoine.nqueens.engine;

import ninja.michelantoine.nqueens.board.Board;
import ninja.michelantoine.nqueens.rule.Rule;

import java.util.List;
import java.util.concurrent.*;

/**
 * A backtracking engine to solve the n queens problem
 * It never tries to put 2 queens on the same row
 */
class BackTrackingEngine implements NQueensEngine {
    private final long timeout;

    public BackTrackingEngine(long timeout) {
        this.timeout = timeout;
    }

    @Override
    public Board solve(int size, List<Rule> rules) {
        if (0 >= size) {
            throw new IllegalArgumentException("size should be > 0");
        }

        Board board = Board.fromSize(size);

        if (0 == size) {
            return board;
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> {
            if (!solveRec(board.size(), board, rules, 0)) {
                throw new NoSolutionFoundException();
            }
        });

        try {
            future.get(timeout, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            if (e.getCause() instanceof NoSolutionFoundException) {
                throw (NoSolutionFoundException) e.getCause();
            }
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new NoSolutionFoundException();
        }

        return board;
    }

    private boolean solveRec(int n, Board board, List<Rule> rules, int y) {
        if (0 == n) return true;

        for (int x = 0; x < board.size(); x++) {
            board.putQueenAt(y, x);

            int conflicts = 0;
            for (Rule rule : rules) {
                conflicts += rule.conflicts(board, y, x);
            }
            if (0 == conflicts && solveRec(
                    n - 1,
                    board,
                    rules,
                    y + 1
            )) {
                return true;
            }
            board.removeQueenAt(y, x);
        }

        return false;
    }
}
