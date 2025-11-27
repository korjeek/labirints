package academy;

import academy.cli.GenerateCommand;
import academy.cli.SolveCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
        name = "maze-app",
        version = "Maze 1.0",
        mixinStandardHelpOptions = true,
        description = "Maze generator and solver CLI application.",
        subcommands = {GenerateCommand.class, SolveCommand.class})
public class Application implements Runnable {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new Application()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        System.out.println("Use 'generate' or 'solve' subcommand. See --help for more information.");
    }
}
