package academy.maze.solver;

import static academy.maze.dto.CellType.PASS;
import static academy.maze.dto.CellType.WALL;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import org.junit.jupiter.api.Test;

public abstract class MazeSolverTestBase {

    protected abstract Path solve(Maze maze, Point start, Point end);

    @Test
    void shouldFindPathInSimpleMaze() {
        Maze maze = createSimpleTestMaze();
        Point start = new Point(1, 1);
        Point end = new Point(3, 3);

        Path path = solve(maze, start, end);

        assertAll(
                () -> assertTrue(path.points().length > 0, "Path should not be empty"),
                () -> assertEquals(start, path.points()[0], "Path should start at start point"),
                () -> assertEquals(end, path.points()[path.points().length - 1], "Path should end at end point"),
                () -> assertTrue(isPathValid(maze, path), "Path should consist of passable cells only"));
    }

    @Test
    void shouldFindOptimalPath() {
        Maze maze = createMazeForOptimalPathTest();
        Point start = new Point(1, 1);
        Point end = new Point(5, 1);

        Path path = solve(maze, start, end);

        assertEquals(5, path.points().length, "Should find optimal path length");
    }

    @Test
    void shouldReturnEmptyPathWhenNoSolution() {
        Maze maze = createImpossibleMaze();
        Point start = new Point(1, 1);
        Point end = new Point(3, 3);

        Path path = solve(maze, start, end);

        assertEquals(0, path.points().length, "Should return empty path when no solution exists");
    }

    @Test
    void shouldHandleStartEqualsEnd() {
        Maze maze = createSimpleTestMaze();
        Point point = new Point(1, 1);

        Path path = solve(maze, point, point);

        assertAll(
                () -> assertEquals(1, path.points().length, "Path should contain single point"),
                () -> assertEquals(point, path.points()[0], "Path should contain the start/end point"));
    }

    @Test
    void shouldReturnEmptyPathForInvalidStartPoint() {
        Maze maze = createSimpleTestMaze();

        Point wallStart = new Point(0, 0);
        Point validEnd = new Point(1, 1);

        Path path = solve(maze, wallStart, validEnd);
        assertEquals(0, path.points().length, "Should return empty path when start is wall");
    }

    @Test
    void shouldReturnEmptyPathForInvalidEndPoint() {
        Maze maze = createSimpleTestMaze();

        Point validStart = new Point(1, 1);
        Point wallEnd = new Point(0, 0);

        Path path = solve(maze, validStart, wallEnd);
        assertEquals(0, path.points().length, "Should return empty path when end is wall");
    }

    @Test
    void shouldReturnEmptyPathForOutOfBoundsPoints() {
        Maze maze = createSimpleTestMaze();
        Point validPoint = new Point(1, 1);

        Point outOfBounds1 = new Point(-1, 1);
        Point outOfBounds2 = new Point(10, 10);

        Path path1 = solve(maze, outOfBounds1, validPoint);
        Path path2 = solve(maze, validPoint, outOfBounds2);
        Path path3 = solve(maze, outOfBounds1, outOfBounds2);

        assertAll(
                () -> assertEquals(0, path1.points().length, "Should return empty path for out-of-bounds start"),
                () -> assertEquals(0, path2.points().length, "Should return empty path for out-of-bounds end"),
                () -> assertEquals(0, path3.points().length, "Should return empty path for both points out-of-bounds"));
    }

    @Test
    void shouldReturnEmptyPathWhenBothPointsAreWalls() {
        Maze maze = createSimpleTestMaze();
        Point wall1 = new Point(0, 0);
        Point wall2 = new Point(0, 1);

        Path path = solve(maze, wall1, wall2);
        assertEquals(0, path.points().length, "Should return empty path when both points are walls");
    }

    protected Maze createSimpleTestMaze() {
        CellType[][] cells = {
            {WALL, WALL, WALL, WALL, WALL},
            {WALL, PASS, PASS, PASS, WALL},
            {WALL, WALL, WALL, PASS, WALL},
            {WALL, PASS, PASS, PASS, WALL},
            {WALL, WALL, WALL, WALL, WALL}
        };
        return new Maze(cells, 5, 5);
    }

    protected Maze createMazeForOptimalPathTest() {
        CellType[][] cells = {
            {WALL, WALL, WALL, WALL, WALL, WALL, WALL},
            {WALL, PASS, PASS, PASS, PASS, PASS, WALL},
            {WALL, WALL, PASS, WALL, PASS, WALL, WALL},
            {WALL, PASS, PASS, PASS, PASS, PASS, WALL},
            {WALL, WALL, WALL, WALL, WALL, WALL, WALL}
        };
        return new Maze(cells, 5, 7);
    }

    protected Maze createImpossibleMaze() {
        CellType[][] cells = {
            {WALL, WALL, WALL, WALL, WALL},
            {WALL, PASS, PASS, WALL, WALL},
            {WALL, PASS, PASS, WALL, WALL},
            {WALL, WALL, WALL, WALL, WALL},
            {WALL, WALL, WALL, WALL, WALL}
        };
        return new Maze(cells, 5, 5);
    }

    protected boolean isPathValid(Maze maze, Path path) {
        for (Point point : path.points()) {
            if (maze.cells()[point.y()][point.x()] != CellType.PASS) {
                return false;
            }
        }
        return true;
    }
}
