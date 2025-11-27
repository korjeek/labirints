package academy.maze.renderer;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;

/**
 * Реализация рендерера лабиринта с использованием ASCII символов. Простая отрисовка без сложных соединений стен,
 * подходит для терминалов с ограниченной поддержкой символов.
 */
public class DefaultMazeRenderer extends AbstractMazeRenderer {
    private static final String STYLE = "ascii";

    @Override
    protected String getStyle() {
        return STYLE;
    }

    @Override
    protected Character getWallSymbol(Maze maze, int x, int y) {
        return styles.getSymbol(STYLE, CellType.WALL);
    }
}
