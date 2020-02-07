# A Java NQueens Problem Solver

## Project Structure

The project is making use of the [hexagonal architecture](https://en.wikipedia.org/wiki/Hexagonal_architecture_(software\)), which means the domain is separated from the adapters.

In the root src folder you'll find all the sources of the engine (backtracking/constraint + configurable set of rules to apply)

In the cli-adapter submodule there's an adapter to run it on the command line

## Usage

To run on the CLI:
```
$ ./gradlew cli-adapter:shadowJar
$ java -jar cli-adapter/build/libs/cli-adapter-1.0-SNAPSHOT-all.jar -h
```

## Performance

At the moment there are 3 different engines you can use:

- The BackTrackingEngine

A simple BT engine that tries every possibility until it finds a solution.

- The ConstraintEngine

A more sophisticated engine that tries to "repair" an existing board iteratively.
Meaning that for a board that already contains queens it will look at the "most conflicting queen" and try to move it to a "less conflicting" position.
This approach makes use of pseudorandom features and thus may not always deliver a solution BUT it is a lot faster than the backtracking one for large n.

- The DelegatingEngine

An engine that will use either other engine depending on the given size of the board.

---
Here are some benchmarks made with JMH to take a closer at the performances:
```
Benchmark                                               Mode  Cnt    Score    Error  Units
BackTrackingEngineBenchmark.solve8                      avgt   10    0.126 ±  0.012  ms/op
BackTrackingEngineBenchmark.solve8_withAdditionalRule   avgt   10    1.958 ±  0.127  ms/op
BackTrackingEngineBenchmark.solve16                     avgt   10   31.798 ±  1.285  ms/op
BackTrackingEngineBenchmark.solve16_withAdditionalRule  avgt   10  162.451 ± 13.867  ms/op
```
```
Benchmark                                              Mode  Cnt       Score        Error  Units
ConstraintEngineBenchmark.solve8                       avgt   10       0.750 ±      0.639  ms/op
ConstraintEngineBenchmark.solve8_withAdditionalRule    avgt   10      12.851 ±      1.022  ms/op
ConstraintEngineBenchmark.solve16                      avgt   10       1.337 ±      0.146  ms/op
ConstraintEngineBenchmark.solve16_withAdditionalRule   avgt   10     214.410 ±     52.035  ms/op
ConstraintEngineBenchmark.solve32                      avgt   10       2.808 ±      0.109  ms/op
ConstraintEngineBenchmark.solve32_withAdditionalRule   avgt   10    3860.836 ±   4310.840  ms/op
ConstraintEngineBenchmark.solve64                      avgt   10       8.652 ±      0.817  ms/op
ConstraintEngineBenchmark.solve64_withAdditionalRule   avgt   10   24510.751 ±  18176.765  ms/op
ConstraintEngineBenchmark.solve128                     avgt   10      38.075 ±      2.764  ms/op
ConstraintEngineBenchmark.solve128_withAdditionalRule  avgt   10  190137.192 ± 243113.835  ms/op
```
