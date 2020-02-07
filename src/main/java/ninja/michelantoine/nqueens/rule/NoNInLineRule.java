package ninja.michelantoine.nqueens.rule;

import ninja.michelantoine.nqueens.board.Board;

import static java.lang.Math.abs;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * Additional rule for the n queens problem
 * This rule forbids that n queens are in a straight line at any angle
 */
public class NoNInLineRule implements Rule {
    private final int n;

    public NoNInLineRule(int n) {
        if (2 >= n) {
            throw new IllegalArgumentException("n must be > 2");
        }

        this.n = n;
    }

    public int gcd(int a, int b) {
        if (a == 0) {
            return b;
        }

        while (b != 0) {
            if (a > b) {
                a = a - b;
            } else {
                b = b - a;
            }
        }

        return a;
    }

    /**
     * Calculates a straight line equation for all pairs of queens where the one given in parameters is present
     * Then by simply looking at how many times a single coefficient appears we know how many queens are aligned
     * @param board the board to check
     * @param y the row of the last added queen
     * @param x the col of the last added queen
     * @return
     */
    @Override
    public int conflicts(Board board, int y, int x) {
        return (int) board.queens()
                .stream()
                .filter(p -> p.getY() != y && p.getX() != x)
                .map(p -> {
                    int vy = y - p.getY();
                    int vx = x - p.getX();
                    int gcd = gcd(abs(vy), abs(vx));

                    vy /= gcd;
                    vx /= gcd;

                    return ((double) vy) / ((double) vx);
                }).collect(groupingBy(identity(), counting()))
                .entrySet()
                .stream()
                .filter(entry -> n <= entry.getValue() + 1)
                .count();
    }
}
