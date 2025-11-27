package academy.cli;

import academy.converters.PointConverter;
import academy.converters.SolverConverter;
import academy.maze.MazeParser;
import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import academy.maze.renderer.MazeRenderer;
import academy.maze.solver.Solver;
import java.util.List;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "solve", description = "Solve a maze with specified algorithm and points.")
public class SolveCommand extends BaseCommand {
    @Option(
            names = {"--algorithm", "-a"},
            description = "Algorithm: astar, dijkstra, bfs",
            required = true,
            converter = SolverConverter.class)
    private Solver solver;

    @Option(
            names = {"--file", "-f"},
            description = "Maze file",
            required = true)
    private String mazeFile;

    @Option(
            names = {"--start", "-s"},
            description = "Start point (format: x,y)",
            required = true,
            converter = PointConverter.class)
    private Point startPoint;

    @Option(
            names = {"--end", "-e"},
            description = "End point (format: x,y)",
            required = true,
            converter = PointConverter.class)
    private Point endPoint;

    @Override
    public void run() {
        try {
            MazeRenderer renderer = useRenderer();
            var parser = new MazeParser();

            List<String> MazeRows = fileService.loadMazeFromFile(mazeFile);
            Maze maze = parser.parse(MazeRows);
            Path path = solver.solve(maze, startPoint, endPoint);
            String solvedTextMaze = renderer.renderSolvedMaze(maze, path);

            outputResult(solvedTextMaze);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            CommandLine.usage(this, System.err);
        }
    }
}
