package academy.maze.generator;

import academy.maze.dto.Maze;

public class PrimGeneratorTest extends MazeGeneratorTestBase {
    @Override
    protected Maze generateMaze(int width, int height) {
        return new PrimGenerator().generate(width, height);
    }
}
