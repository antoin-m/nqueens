package ninja.michelantoine.nqueens.cliadapter;

import ninja.michelantoine.nqueens.board.Board;
import ninja.michelantoine.nqueens.engine.NQueensEngine;
import ninja.michelantoine.nqueens.engine.NoSolutionFoundException;
import ninja.michelantoine.nqueens.rule.NoNInLineRule;
import ninja.michelantoine.nqueens.rule.NoQueenCanAttackAnotherRule;
import ninja.michelantoine.nqueens.rule.Rule;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "nqueen",
        mixinStandardHelpOptions = true,
        description = "Solves an N queens problem"
)
public class CLIApplication implements Callable<Integer> {
    @CommandLine.Parameters(index = "0", description = "The n in n queens", defaultValue = "8")
    private int n;

    @CommandLine.Option(names = {"-l"}, description = "The max number of aligned queens (0 to disable)", defaultValue = "3")
    private int maxAligned;

    @CommandLine.Option(names = {"-t"}, description = "the engine stops looking for a solution", defaultValue = "300")
    private long timeout;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new CLIApplication()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        if (n < 0) {
            System.err.println("n must be a positive integer");
            return 1;
        }

        Board board;

        List<Rule> rules = new ArrayList<>();
        rules.add(new NoQueenCanAttackAnotherRule()); // Base rule

        if (maxAligned > 0) {
            if (2 >= maxAligned) {
                System.err.println("The maximum number of aligned queens should be > 2");
                return 3;
            }
            rules.add(new NoNInLineRule(maxAligned));
        }

        try {
            board = NQueensEngine.create(timeout).solve(n, rules);
        } catch (NoSolutionFoundException e) {
            System.err.println(String.format("Could not find a solution for n=%d, l=%d, t=%d", n, maxAligned, timeout));
            return 2;
        }

        board.debugPrint();
        return 0;
    }
}
