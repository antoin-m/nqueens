package ninja.michelantoine.nqueens.engine;

import ninja.michelantoine.nqueens.board.Board;
import ninja.michelantoine.nqueens.rule.NoNInLineRule;
import ninja.michelantoine.nqueens.rule.NoQueenCanAttackAnotherRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class NQueensEngineInterfaceTest {
    @Parameterized.Parameters
    public static Collection<Object[]> engines() {
        return List.of(
                new Object[] { new BackTrackingEngine(1) },
                new Object[] { new ConstraintEngine(1) }
        );
    }

    private final NQueensEngine engine;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public NQueensEngineInterfaceTest(NQueensEngine engine) {
        this.engine = engine;
    }

    @Test
    public void shouldSolveEmptyBoard() {
        thrown.expect(IllegalArgumentException.class);
        engine.solve(0, emptyList());
    }

    @Test
    public void shouldSolveSize1Board() {
        Board board = engine.solve(1, emptyList());
        assertEquals(Board.QueenPresence.PRESENT, board.at(0, 0));
    }

    @Test
    public void shouldSolveSize2BoardWithoutRules() {
        Board board = engine.solve(2, emptyList());
        assertEquals(
                1,
                Stream.of(board.at(0, 0), board.at(0, 1)).filter(Board.QueenPresence.PRESENT::equals).count()
        );
        assertEquals(
                1,
                Stream.of(board.at(1, 0), board.at(1, 1)).filter(Board.QueenPresence.PRESENT::equals).count()
        );
    }

    @Test
    public void shouldFailToSolveSize2BoardWithBaseRule() {
        thrown.expect(NoSolutionFoundException.class);
        engine.solve(2, singletonList(new NoQueenCanAttackAnotherRule()));
    }

    @Test
    public void shouldSolveRequiredProblem() {
        List<ninja.michelantoine.nqueens.rule.Rule> rules = List.of(new NoQueenCanAttackAnotherRule(), new NoNInLineRule(3));
        Board board = engine.solve(8, rules);

        int sum = board.queens()
                .stream()
                .flatMapToInt(p -> rules.stream().mapToInt(r -> r.conflicts(board, p.getY(), p.getX())))
                .sum();

        board.debugPrint();
        assertEquals(0, sum);
    }
}
