package academy.maze.renderer;

import academy.maze.dto.Maze;
import academy.maze.dto.Path;

/**
 * Интерфейс для отрисовки лабиринтов. Определяет методы для отображения сгенерированного лабиринта и лабиринта с
 * решением.
 */
public interface MazeRenderer {
    /**
     * Отрисовывает сгенерированный лабиринт без решения.
     *
     * @param maze объект лабиринта для отрисовки
     * @return строковое представление лабиринта
     */
    String renderGeneratedMaze(Maze maze);
    /**
     * Отрисовывает лабиринт с отмеченным путем решения.
     *
     * @param maze объект лабиринта для отрисовки
     * @param path путь решения для отображения
     * @return строковое представление лабиринта с решением
     */
    String renderSolvedMaze(Maze maze, Path path);
}
