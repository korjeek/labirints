package academy.maze.renderer;

import academy.maze.dto.CellType;
import academy.maze.dto.Direction;
import academy.maze.dto.Maze;

/**
 * Реализация рендерера лабиринта с использованием Unicode символов. Использует специальные символы для отображения
 * соединений между стенами, что дает более визуально приятное представление лабиринта.
 */
public class UnicodeMazeRenderer extends AbstractMazeRenderer {
    private static final String STYLE = "unicode";

    @Override
    protected String getStyle() {
        return STYLE;
    }

    @Override
    protected Character getWallSymbol(Maze maze, int x, int y) {
        String pattern = getConnectionPattern(maze, x, y);
        return styles.getConnection(STYLE, pattern);
    }

    /**
     * Строит битовый шаблон соединений стены с соседями. Шаблон представляет собой строку из 4 символов '0' или '1',
     * где каждый символ соответствует наличию стены в определенном направлении. Порядок направлений определяется
     * {@code Direction.VALUES}.
     *
     * @param maze лабиринт для проверки соединений
     * @param x координата X текущей стены
     * @param y координата Y текущей стены
     * @return строковый шаблон соединений вида "1010"
     */
    private String getConnectionPattern(Maze maze, int x, int y) {
        StringBuilder pattern = new StringBuilder();
        for (Direction dir : Direction.getDirections()) {
            pattern.append(isWall(maze, x + dir.deltaX, y + dir.deltaY) ? '1' : '0');
        }
        return pattern.toString();
    }

    /**
     * Проверяет, является ли указанная позиция стеной в пределах лабиринта.
     *
     * @param maze лабиринт для проверки
     * @param x координата X для проверки
     * @param y координата Y для проверки
     * @return {@code true} если позиция находится в пределах лабиринта и содержит стену, {@code false} в противном
     *     случае
     */
    private boolean isWall(Maze maze, int x, int y) {
        return x >= 0 && x < maze.width() && y >= 0 && y < maze.height() && maze.cells()[y][x] == CellType.WALL;
    }
}
