package academy.maze.solver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import org.junit.jupiter.api.Test;

public class AStarSolverTest extends MazeSolverTestBase {
    @Override
    protected Path solve(Maze maze, Point start, Point end) {
        return new AStarSolver().solve(maze, start, end);
    }

    @Test
    void heuristicShouldCalculateManhattanDistance() {
        AStarSolver solver = new AStarSolver();
        Point a = new Point(1, 1);
        Point b = new Point(4, 5);

        int distance = solver.heuristic(a, b);
        assertEquals(7, distance);
    }
}
