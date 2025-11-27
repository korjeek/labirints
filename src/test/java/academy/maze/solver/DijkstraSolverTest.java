package academy.maze.solver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import org.junit.jupiter.api.Test;

public class DijkstraSolverTest extends MazeSolverTestBase {
    @Override
    protected Path solve(Maze maze, Point start, Point end) {
        return new DijkstraSolver().solve(maze, start, end);
    }

    @Test
    void heuristicShouldAlwaysReturnZero() {
        DijkstraSolver solver = new DijkstraSolver();
        Point a = new Point(1, 1);
        Point b = new Point(4, 5);

        int distance = solver.heuristic(a, b);
        assertEquals(0, distance);
    }
}
