package ninja.michelantoine.nqueens.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * A {@link Board } implementation that only allows one queen per row
 * Queens' x positions are stored in a y-indexed int array and in diagonal "id" indexed arrays
 */
class ArrayBoard implements Board {
    private final int[] queens;
    private final int[] leftDiagonals;
    private final int[] rightDiagonals;

    ArrayBoard(int n) {
        queens = new int[n];
        leftDiagonals = new int[2 * n - 1];
        rightDiagonals = new int[2 * n - 1];

        Arrays.fill(queens, -1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        if (o instanceof Board) {
            Board thatBoard = (Board) o;

            if (size() != thatBoard.size()) return false;

            if (o instanceof ArrayBoard) {
                ArrayBoard that = (ArrayBoard) o;

                return Arrays.equals(queens, that.queens);
            }

            for (int y = 0; y < size(); y++)
                for (int x = 0; x < size(); x++)
                    if (at(y, x) != thatBoard.at(y, x))
                        return false;

            return true;
        }

        return false;
    }

    @Override
    public Collection<Point> queens() {
        List<Point> queens = new ArrayList<>();

        for (int y = 0; y < this.queens.length; y++) {
            if (-1 != this.queens[y]) {
                queens.add(new Point(y, this.queens[y]));
            }
        }

        return queens;
    }

    @Override
    public int size() {
        return queens.length;
    }

    @Override
    public QueenPresence at(int y, int x) {
        return queens[y] == x ? QueenPresence.PRESENT : QueenPresence.ABSENT;
    }

    @Override
    public int onDiagonals(int y, int x) {
        return leftDiagonals[x + y] + rightDiagonals[x - y + size() - 1];
    }

    @Override
    public void putQueenAt(int y, int x) {
        queens[y] = x;
        leftDiagonals[x + y] += 1;
        rightDiagonals[x - y + size() - 1] += 1;
    }

    @Override
    public void removeQueenAt(int y, int x) {
        queens[y] = -1;
        leftDiagonals[x + y] -= 1;
        rightDiagonals[x - y + size() - 1] -= 1;
    }
}
