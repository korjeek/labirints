package academy.maze;

import academy.ResourceLoader;
import java.util.Properties;

public final class MazeConfig {
    public static final int BORDER_WIDTH;

    static {
        var resourceLoader = new ResourceLoader();
        Properties props = resourceLoader.loadMazeConfig();

        BORDER_WIDTH = Integer.parseInt(props.getProperty("maze.border.width"));
    }
}
