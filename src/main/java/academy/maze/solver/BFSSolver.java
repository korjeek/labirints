package academy.maze.solver;

import academy.maze.dto.CellType;
import academy.maze.dto.Direction;
import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * Реализация алгоритма поиска в ширину (BFS) для нахождения пути в лабиринте.
 *
 * <p>Алгоритм работает следующим образом:
 *
 * <ol>
 *   <li>Инициализируется очередь и множество посещенных точек
 *   <li>Начальная точка добавляется в очередь и помечается как посещенная
 *   <li>Пока очередь не пуста:
 *       <ul>
 *         <li>Извлекается текущая точка из начала очереди
 *         <li>Если достигнута конечная точка - восстанавливается путь с помощью {@link #traceBackPath}
 *         <li>Для каждого соседа текущей точки:
 *             <ul>
 *               <li>Если сосед - проход и еще не посещен - добавляется в очередь
 *               <li>Запоминается предок для восстановления пути
 *             </ul>
 *       </ul>
 * </ol>
 */
public class BFSSolver extends AbstractSolver {
    @Override
    public Path solve(Maze maze, Point start, Point end) {
        if (isValidTargetPoints(maze, start, end)) {
            return new Path(new Point[0]);
        }

        Queue<Point> queue = new ArrayDeque<>();
        Set<Point> visited = new HashSet<>();

        Point[][] traceBackArray = new Point[maze.height()][maze.width()];
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            if (current.equals(end)) {
                return traceBackPath(traceBackArray, end);
            }

            for (Direction direction : Direction.getDirections()) {
                var neighbor = new Point(current.x() + direction.deltaX, current.y() + direction.deltaY);

                if (maze.cells()[neighbor.y()][neighbor.x()] != CellType.PASS || visited.contains(neighbor)) continue;

                queue.add(neighbor);
                visited.add(neighbor);
                traceBackArray[neighbor.y()][neighbor.x()] = current;
            }
        }

        return new Path(new Point[0]);
    }
}
