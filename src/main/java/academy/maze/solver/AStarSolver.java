package academy.maze.solver;

import academy.maze.dto.CellType;
import academy.maze.dto.Direction;
import academy.maze.dto.Maze;
import academy.maze.dto.Node;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Реализация алгоритма A* для поиска кратчайшего пути в лабиринте.
 *
 * <p>Алгоритм A* сочетает в себе преимущества Dijkstra (точность) и жадного поиска (эффективность) за счет
 * использования эвристической функции для оценки оставшегося пути.
 *
 * <p>Алгоритм работает следующим образом:
 *
 * <ol>
 *   <li>Инициализируются массивы для отслеживания минимальных стоимостей и предков
 *   <li>Начальная точка добавляется в приоритетную очередь с приоритетом равным {@link #heuristic}
 *   <li>Пока очередь не пуста:
 *       <ul>
 *         <li>Извлекается узел с наименьшим f(n) = g(n) + h(n), где:
 *             <ul>
 *               <li><strong>f(n)</strong> - общая оценка стоимости пути через узел n
 *               <li><strong>g(n)</strong> - реальная стоимость достижения узла n от начальной точки
 *               <li><strong>h(n)</strong> - эвристическая оценка стоимости от узла n до конечной точки
 *             </ul>
 *         <li>Если достигнута конечная точка - восстанавливается путь с помощью {@link #traceBackPath}
 *         <li>Для каждого соседа:
 *             <ul>
 *               <li>Пропускаются стены и посещенные узлы
 *               <li>Если найден более короткий путь - обновляются стоимость и предок
 *               <li>Узел добавляется в очередь с приоритетом = g(n) + h(n)
 *             </ul>
 *       </ul>
 * </ol>
 *
 * @implNote В качестве эвристики используется манхэттенское расстояние
 */
public class AStarSolver extends AbstractSolver {
    /**
     * Стоимость перемещения между соседними клетками. Предполагается равномерная сетка, где все перемещения стоят
     * одинаково.
     */
    private static final int EDGE_COST = 1;

    /**
     * Находит кратчайший путь от начальной до конечной точки в лабиринте, используя алгоритм A*.
     *
     * @param maze лабиринт, в котором ищется путь. Не может быть null
     * @param start начальная точка пути. Должна находиться внутри лабиринта и не быть стеной
     * @param end конечная точка пути. Должна находиться внутри лабиринта и не быть стеной
     * @return путь от начальной до конечной точки в виде последовательности точек {@link Path}. Если путь не найден,
     *     возвращается пустой путь (массив нулевой длины)
     */
    @Override
    public Path solve(Maze maze, Point start, Point end) {
        if (isValidTargetPoints(maze, start, end)) {
            return new Path(new Point[0]);
        }

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        Set<Point> visited = new HashSet<>();

        Point[][] traceBackArray = new Point[maze.height()][maze.width()];
        long[][] minCosts = createMinCosts(maze.width(), maze.height());

        minCosts[start.y()][start.x()] = 0;
        priorityQueue.add(new Node(start, heuristic(start, end)));

        while (!priorityQueue.isEmpty()) {
            Node node = priorityQueue.poll();
            Point current = node.point();

            if (current.equals(end)) {
                return traceBackPath(traceBackArray, end);
            }

            visited.add(current);

            for (Direction direction : Direction.getDirections()) {
                Point neighbor = new Point(current.x() + direction.deltaX, current.y() + direction.deltaY);

                if (maze.cells()[neighbor.y()][neighbor.x()] == CellType.WALL || visited.contains(neighbor)) {
                    continue;
                }

                long distance = minCosts[current.y()][current.x()] + EDGE_COST;
                if (distance < minCosts[neighbor.y()][neighbor.x()]) {
                    traceBackArray[neighbor.y()][neighbor.x()] = current;
                    minCosts[neighbor.y()][neighbor.x()] = distance;

                    long weight = minCosts[neighbor.y()][neighbor.x()] + heuristic(neighbor, end);
                    priorityQueue.add(new Node(neighbor, weight));
                }
            }
        }

        return new Path(new Point[0]);
    }

    /**
     * Эвристическая функция для оценки расстояния между двумя точками.
     *
     * <p>Используется манхэттенское расстояние.
     *
     * @param a первая точка
     * @param b вторая точка
     * @return манхэттенское расстояние между точками |x1-x2| + |y1-y2|
     */
    protected int heuristic(Point a, Point b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    /**
     * Создает и инициализирует матрицу минимальных стоимостей.
     *
     * <p>Все ячейки инициализируются значением Integer.MAX_VALUE, что означает "бесконечную" стоимость (путь еще не
     * найден).
     *
     * @param width ширина матрицы
     * @param height высота матрицы
     * @return инициализированная матрица минимальных стоимостей
     */
    private long[][] createMinCosts(int width, int height) {
        var minCosts = new long[height][width];
        for (var row : minCosts) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        return minCosts;
    }
}
