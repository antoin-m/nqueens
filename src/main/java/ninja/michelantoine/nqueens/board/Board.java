package ninja.michelantoine.nqueens.board;

import java.util.Collection;
import java.util.Objects;

/**
 * Represents the Chess Board on which to place the queens
 */
public interface Board {
    enum QueenPresence {
        PRESENT,
        ABSENT
        ;
    }

    /**
     * A Point, or "Tile" on the {@link Board}
     */
    final class Point {
        final int x;
        final int y;

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    static Board fromSize(int size) {
        if (0 >= size) {
            throw new IllegalArgumentException("size should be > 0");
        }

        return new ArrayBoard(size);
    }

    /**
     * Technically returns the square root of the number of tiles
     * @return The size of ONE SIDE of the board
     */
    int size();

    /**
     * Whether or not a queen is present at some position on the board
     * @param y row number
     * @param x col number
     * @return The presence (or absence) of the queen on the tile
     */
    QueenPresence at(int y, int x);

    /**
     * The number of queens already present on some diagonals ((x + y) and (x - y + n - 1))
     * @param y
     * @param x
     * @return
     */
    int onDiagonals(int y, int x);

    void putQueenAt(int y, int x);
    void removeQueenAt(int y, int x);

    /**
     * Finds and returns all the positions of the queens present on the board
     * @return A collection of tile coordinates where a queen is present
     */
    Collection<Point> queens();

    default void debugPrint() {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < size(); y++) {
            for (int x = 0; x < size(); x++) {
                if (at(y, x) == QueenPresence.PRESENT)
                    sb.append("Q");
                else
                    sb.append(".");
            }
            sb.append("\n");
        }

        System.err.println(sb);
    }
}
