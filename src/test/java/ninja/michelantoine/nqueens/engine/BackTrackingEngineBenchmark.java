package ninja.michelantoine.nqueens.engine;

import ninja.michelantoine.nqueens.rule.NoNInLineRule;
import ninja.michelantoine.nqueens.rule.NoQueenCanAttackAnotherRule;
import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1)
@Warmup(iterations = 3)
@Measurement(iterations = 10)
public class BackTrackingEngineBenchmark {
    @Benchmark
    public void solve8() {
        new BackTrackingEngine(Long.MAX_VALUE).solve(8, List.of(new NoQueenCanAttackAnotherRule()));
    }

    @Benchmark
    public void solve16() {
        new BackTrackingEngine(Long.MAX_VALUE).solve(16, List.of(new NoQueenCanAttackAnotherRule()));
    }

    @Benchmark
    public void solve8_withAdditionalRule() {
        new BackTrackingEngine(Long.MAX_VALUE).solve(8, List.of(new NoQueenCanAttackAnotherRule(), new NoNInLineRule(3)));
    }

    @Benchmark
    public void solve16_withAdditionalRule() {
        new BackTrackingEngine(Long.MAX_VALUE).solve(16, List.of(new NoQueenCanAttackAnotherRule(), new NoNInLineRule(3)));
    }
}
