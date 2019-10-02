import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;

public class ConcurrentPuzzleSolver<P, M> {
    private final Puzzle<P, M> puzzle;
    private final ExecutorService exec;
    private final ConcurrentMap<P, Boolean> seen;
    final ValueLatch<Node<P, M>> solution
            = new ValueLatch<Node<P, M>>();

    public List<M> solve() throws InterruptedException{
        try {
            P p = puzzle.initialPosition();
            exec.execute(newTask(p, null, null));
            Node<P, M> solnNode = solution.getValue();
            return (solnNode == null) ? null : solnNode.asMoveList();
        } finally {
            exec.shutdown();
        }
    }

    protected Runnable newTask(P p, M m, Node<P, M> n) {
        return new SolverTask(p, m, n);
    }

    class SolverTask extends Node<P, M> implements Runnable {
        public void run() {
            if (solution.isSet()
                || seen.putIfAbsent(pos, true) != null) {
                return ;
            }
            if (puzzle.isGoal(pos)) {
                solution.setValue(this);
            } else {
                for (M m : puzzle.legalMoves(pos)) {
                    exec.execute(
                            newTask(puzzle.move(pos, m), m, this)
                    );
                }
            }
        }
    }
}
