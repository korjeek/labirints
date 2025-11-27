package academy.maze.generator;

import static org.junit.jupiter.api.Assertions.*;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import org.junit.jupiter.api.Test;

public abstract class MazeGeneratorTestBase {

    protected abstract Maze generateMaze(int width, int height);

    @Test
    void shouldCreateMazeWithCorrectDimensions() {
        Maze maze = generateMaze(10, 10);
        assertEquals(12, maze.width());
        assertEquals(12, maze.height());
    }

    @Test
    void shouldGenerateValidMazeStructure() {
        Maze maze = generateMaze(7, 7);

        assertAll(
                () -> assertTrue(hasBorderWalls(maze), "Maze should have border walls"),
                () -> assertTrue(hasAtLeastOnePassage(maze), "Maze should have at least one passage"),
                () -> assertTrue(isMazeValid(maze), "Maze should have valid structure"));
    }

    @Test
    void shouldThrowExceptionForInvalidDimensions() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> generateMaze(-1, 10)),
                () -> assertThrows(IllegalArgumentException.class, () -> generateMaze(10, -1)));
    }

    private boolean hasBorderWalls(Maze maze) {
        for (int x = 0; x < maze.width(); x++) {
            if (maze.cells()[0][x] != CellType.WALL || maze.cells()[maze.height() - 1][x] != CellType.WALL) {
                return false;
            }
        }
        for (int y = 0; y < maze.height(); y++) {
            if (maze.cells()[y][0] != CellType.WALL || maze.cells()[y][maze.width() - 1] != CellType.WALL) {
                return false;
            }
        }
        return true;
    }

    private boolean hasAtLeastOnePassage(Maze maze) {
        for (int y = 1; y < maze.height() - 1; y++) {
            for (int x = 1; x < maze.width() - 1; x++) {
                if (maze.cells()[y][x] == CellType.PASS) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isMazeValid(Maze maze) {
        for (int y = 0; y < maze.height(); y++) {
            for (int x = 0; x < maze.width(); x++) {
                CellType cell = maze.cells()[y][x];
                assertNotNull(cell, "Cell should not be null");
                assertTrue(cell == CellType.WALL || cell == CellType.PASS, "Cell should be either WALL or PASS");
            }
        }
        return true;
    }
}
