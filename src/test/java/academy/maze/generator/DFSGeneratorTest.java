package academy.maze.generator;

import academy.maze.dto.Maze;

public class DFSGeneratorTest extends MazeGeneratorTestBase {
    @Override
    protected Maze generateMaze(int width, int height) {
        return new DFSGenerator().generate(width, height);
    }
}
