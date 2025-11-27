package academy.cli;

import academy.converters.GeneratorConverter;
import academy.maze.dto.Maze;
import academy.maze.generator.Generator;
import academy.maze.renderer.MazeRenderer;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "generate", description = "Generate a maze with specified algorithm and dimensions.")
public class GenerateCommand extends BaseCommand {
    @Option(
            names = {"--algorithm", "-a"},
            description = "Algorithm: dfs, prim, kruskal",
            required = true,
            converter = GeneratorConverter.class)
    private Generator algorithm;

    @Option(
            names = {"--width", "-w"},
            description = "Maze width",
            required = true)
    private int width;

    @Option(
            names = {"--height", "-h"},
            description = "Maze height",
            required = true)
    private int height;

    @Override
    public void run() {
        try {
            MazeRenderer renderer = useRenderer();
            Maze maze = algorithm.generate(width, height);
            String textMaze = renderer.renderGeneratedMaze(maze);
            outputResult(textMaze);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            CommandLine.usage(this, System.err);
        }
    }
}
