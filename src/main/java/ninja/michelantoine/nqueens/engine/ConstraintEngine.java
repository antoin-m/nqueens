package ninja.michelantoine.nqueens.engine;

import ninja.michelantoine.nqueens.board.Board;
import ninja.michelantoine.nqueens.rule.Rule;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * A constraint engine to solve the n queens problem
 * It never tries to put 2 queens on the same row
 * It works by creating a base board where the queens' position are the less conflicting
 * Then it will move iteratively the most conflicting queen to a less conflicting position until the problem is solved
 *
 * This engine makes use of pseudorandom capabilities and thus the time to result is not entirely predictable.
 * Depending on the rules it may not have a solution and may get into an infinite loop
 * But it's a lot faster than the {@link BackTrackingEngine}
 */
class ConstraintEngine implements NQueensEngine {
    private final long timeout;

    ConstraintEngine(long timeout) {
        this.timeout = timeout;
    }

    private static final class PointAndConflicts {
        private final Board.Point point;
        private final int conflicts;

        private PointAndConflicts(Board.Point point, int conflicts) {
            this.point = point;
            this.conflicts = conflicts;
        }

        public Board.Point getPoint() {
            return point;
        }

        public int getConflicts() {
            return conflicts;
        }
    }

    @Override
    public Board solve(int size, List<Rule> rules) {
        if (0 >= size) {
            throw new IllegalArgumentException("size should be > 0");
        }
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Board> future = executor.submit(() -> {
            while (true) {
                Board board = Board.fromSize(size);
                long maxTries = size * size;

                createBaseBoard(board, rules);

                for (long tries = 0; tries < maxTries; tries++) {
                    PointAndConflicts pac = findMostConflictedQueen(board, rules);

                    if (0 == pac.conflicts) {
                        System.err.println(String.format("Solved in %d moves", tries));
                        return board;
                    }

                    PointAndConflicts lessConflictedPositionInRow = findLessConflictedPositionInRow(board, rules, pac.point);

                    if (!lessConflictedPositionInRow.point.equals(pac.point)) {
                        board.removeQueenAt(pac.point.getY(), pac.point.getX());
                        board.putQueenAt(lessConflictedPositionInRow.point.getY(), lessConflictedPositionInRow.point.getX());
                    }
                }
            }
        });

        try {
            return future.get(timeout, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new NoSolutionFoundException();
        }
    }

    private int getConflicts(Board board, List<Rule> rules, Board.Point queen) {
        return rules.stream().mapToInt(r -> r.conflicts(board, queen.getY(), queen.getX())).sum();
    }

    private PointAndConflicts findMostConflictedQueen(Board board, List<Rule> rules) {
        List<PointAndConflicts> mostConflictedQueens = board.queens()
                .stream()
                .map(q -> new PointAndConflicts(q, getConflicts(board, rules, q)))
                .collect(groupingBy(PointAndConflicts::getConflicts, TreeMap::new, toList()))
                .lastEntry()
                .getValue();

        return mostConflictedQueens.get(ThreadLocalRandom.current().nextInt(mostConflictedQueens.size()));
    }

    private PointAndConflicts findLessConflictedPositionInRow(Board board, List<Rule> rules, Board.Point queen) {
        List<PointAndConflicts> lowestConflicts = IntStream.range(0, board.size())
                .mapToObj(x -> {
                    Board.Point point = new Board.Point(queen.getY(), x);
                    return new PointAndConflicts(point, getConflicts(board, rules, point));
                })
                .collect(groupingBy(PointAndConflicts::getConflicts, TreeMap::new, toList()))
                .firstEntry()
                .getValue();

        return lowestConflicts.get(ThreadLocalRandom.current().nextInt(lowestConflicts.size()));
    }

    private void createBaseBoard(Board board, List<Rule> rules) {
        for (int y = 0; y < board.size(); y++) {
            Board.Point lessConflictedPositionInRow = findLessConflictedPositionInRow(board, rules, new Board.Point(y, -1)).point;
            board.putQueenAt(y, lessConflictedPositionInRow.getX());
        }
    }
}
