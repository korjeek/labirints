package academy.maze.solver;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Абстрактный базовый класс для всех алгоритмов решения лабиринтов.
 *
 * <p>Предоставляет общую функциональность, используемую различными алгоритмами поиска пути/
 *
 * <p>Наследующие классы должны реализовать метод {@link #solve} с конкретным алгоритмом поиска пути, заполняя массив
 * предков для последующего восстановления пути.
 */
public abstract class AbstractSolver implements Solver {
    /**
     * Восстанавливает путь от конечной точки до начальной по массиву предков.
     *
     * <p>Метод работает следующим образом:
     *
     * <ol>
     *   <li>Начинает с конечной точки и движется по цепочке предков
     *   <li>Добавляет каждую точку в список в обратном порядке
     *   <li>Разворачивает список для получения пути от начала до конца
     * </ol>
     *
     * <p>Массив предков должен быть заполнен в процессе работы алгоритма так, что {@code parent[y][x]} содержит точку,
     * из которой был достигнут {@link Point}. Для начальной точки значение в массиве должно быть {@code null}.
     *
     * @param parent двумерный массив точек-предков для каждой ячейки лабиринта
     * @param end конечная точка, с которой начинается восстановление пути
     * @return путь от начальной точки до конечной в правильном порядке
     * @implNote Временная сложность: O(L), где L - длина пути
     */
    protected Path traceBackPath(Point[][] parent, Point end) {
        List<Point> backTrack = new ArrayList<>();
        Point current = end;

        while (current != null) {
            backTrack.add(current);
            current = parent[current.y()][current.x()];
        }

        Point[] path = new Point[backTrack.size()];
        for (int i = 0; i < path.length; i++) path[i] = backTrack.get(path.length - 1 - i);

        return new Path(path);
    }

    protected boolean isValidTargetPoints(Maze maze, Point start, Point end) {
        return !isValidPoint(maze, start) || !isValidPoint(maze, end);
    }

    private boolean isValidPoint(Maze maze, Point point) {
        return point.x() >= 0
                && point.x() < maze.width()
                && point.y() >= 0
                && point.y() < maze.height()
                && maze.cells()[point.y()][point.x()] == CellType.PASS;
    }
}
