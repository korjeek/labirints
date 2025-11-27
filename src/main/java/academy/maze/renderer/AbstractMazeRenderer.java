package academy.maze.renderer;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import academy.maze.styles.StylesStorage;

/**
 * Базовый абстрактный класс для рендереров лабиринта. Содержит общую логику отрисовки, которую наследуют конкретные
 * реализации.
 */
public abstract class AbstractMazeRenderer implements MazeRenderer {
    protected final StylesStorage styles = new StylesStorage();

    /**
     * Возвращает идентификатор стиля отрисовки.
     *
     * @return идентификатор стиля
     */
    protected abstract String getStyle();

    /**
     * Возвращает символ для отображения стены в указанной позиции.
     *
     * @param maze лабиринт
     * @param x координата X
     * @param y координата Y
     * @return символ стены
     */
    protected abstract Character getWallSymbol(Maze maze, int x, int y);

    /**
     * Отрисовывает сгенерированный лабиринт без решения.
     *
     * @param maze объект лабиринта для отрисовки
     * @return строковое представление лабиринта
     */
    @Override
    public String renderGeneratedMaze(Maze maze) {
        Character[][] textSolvedMaze = createEmptyTextMaze(maze);
        return buildFinalMazeString(maze, textSolvedMaze);
    }

    /**
     * Отрисовывает лабиринт с отмеченным путем решения.
     *
     * @param maze объект лабиринта для отрисовки
     * @param path путь решения для отображения
     * @return строковое представление лабиринта с решением
     */
    @Override
    public String renderSolvedMaze(Maze maze, Path path) {
        Character[][] textSolvedMaze = createEmptyTextMaze(maze);
        markPathInMaze(textSolvedMaze, path);
        return buildFinalMazeString(maze, textSolvedMaze);
    }

    /**
     * Создает пустую текстовую матрицу для лабиринта.
     *
     * @param maze лабиринт
     * @return пустая матрица символов
     */
    private Character[][] createEmptyTextMaze(Maze maze) {
        return new Character[maze.height()][maze.width()];
    }

    /**
     * Отмечает путь решения в текстовой матрице лабиринта.
     *
     * @param textSolvedMaze текстовая матрица лабиринта
     * @param path путь решения
     */
    private void markPathInMaze(Character[][] textSolvedMaze, Path path) {
        int n = path.points().length;
        for (int i = 0; i < n; i++) {
            Point point = path.points()[i];
            if (i == 0) {
                textSolvedMaze[point.y()][point.x()] = styles.getSymbol(getStyle(), CellType.ENTER);
            } else if (i == n - 1) {
                textSolvedMaze[point.y()][point.x()] = styles.getSymbol(getStyle(), CellType.EXIT);
            } else {
                textSolvedMaze[point.y()][point.x()] = styles.getSymbol(getStyle(), CellType.PATH);
            }
        }
    }

    /**
     * Собирает финальное строковое представление лабиринта.
     *
     * @param maze лабиринт
     * @param textSolvedMaze текстовая матрица с отмеченным путем
     * @return строковое представление лабиринта
     */
    private String buildFinalMazeString(Maze maze, Character[][] textSolvedMaze) {
        var sb = new StringBuilder();

        for (int y = 0; y < maze.height(); y++) {
            for (int x = 0; x < maze.width(); x++) {
                CellType type = maze.cells()[y][x];
                if (type == CellType.WALL) {
                    textSolvedMaze[y][x] = getWallSymbol(maze, x, y);
                } else if (textSolvedMaze[y][x] == null) {
                    textSolvedMaze[y][x] = styles.getSymbol(getStyle(), type);
                }
                sb.append(textSolvedMaze[y][x]);
            }
            sb.append('\n');
        }

        return sb.toString();
    }
}
