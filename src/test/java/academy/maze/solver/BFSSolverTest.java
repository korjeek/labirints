package academy.maze.solver;

import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;

public class BFSSolverTest extends MazeSolverTestBase {
    @Override
    protected Path solve(Maze maze, Point start, Point end) {
        return new BFSSolver().solve(maze, start, end);
    }
}
