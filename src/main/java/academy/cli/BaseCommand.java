package academy.cli;

import academy.maze.MazeFileService;
import academy.maze.renderer.DefaultMazeRenderer;
import academy.maze.renderer.MazeRenderer;
import academy.maze.renderer.UnicodeMazeRenderer;
import picocli.CommandLine.Option;

public abstract class BaseCommand implements Runnable {
    @Option(
            names = {"--output", "-o"},
            description = "Output file")
    protected String outputFile;

    @Option(
            names = {"--unicode", "-u"},
            description = "Use Unicode characters for the maze display")
    protected boolean unicode;

    protected MazeFileService fileService = new MazeFileService();

    /** Выбирает рендерер на основе флага unicode */
    protected MazeRenderer useRenderer() {
        return unicode ? new UnicodeMazeRenderer() : new DefaultMazeRenderer();
    }

    /** Выводит результат в файл или на консоль */
    protected void outputResult(String text) {
        if (outputFile != null) {
            fileService.saveMazeToFile(text, outputFile);
        } else {
            System.out.print(text);
        }
    }
}
