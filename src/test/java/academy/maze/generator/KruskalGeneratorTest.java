package academy.maze.generator;

import academy.maze.dto.Maze;

public class KruskalGeneratorTest extends MazeGeneratorTestBase {
    @Override
    protected Maze generateMaze(int width, int height) {
        return new KruskalGenerator().generate(width, height);
    }
}
