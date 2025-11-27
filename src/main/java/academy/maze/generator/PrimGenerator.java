package academy.maze.generator;

import academy.maze.MazeBuilder;
import academy.maze.dto.CellType;
import academy.maze.dto.Direction;
import academy.maze.dto.Maze;
import academy.maze.dto.Node;
import academy.maze.dto.Point;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Генератор лабиринта с использованием алгоритма Прима.
 *
 * <p>Алгоритм Прима создает минимальное остовное дерево для графа, где:
 *
 * <ul>
 *   <li>Узлы - ячейки лабиринта с четными координатами
 *   <li>Ребра - возможные соединения между узлами со случайным весом
 * </ul>
 *
 * <p>Алгоритм работает следующим образом:
 *
 * <ol>
 *   <li>Выбирается случайная стартовая точка и добавляется в приоритетную очередь
 *   <li>Пока очередь не пуста:
 *       <ul>
 *         <li>Извлекается узел с наивысшим приоритетом (случайный вес)
 *         <li>Если узел уже посещен - пропускаем
 *         <li>Для каждого соседа (через 2 шага):
 *             <ul>
 *               <li>Если сосед валиден и не посещен - добавляем в очередь со случайным весом
 *               <li>Запоминаем родителя для соседа
 *             </ul>
 *         <li>Соединяем текущий узел с родителем через промежуточную стену
 *         <li>Отмечаем текущий узел как посещенный
 *       </ul>
 * </ol>
 *
 * <p>Результат - лабиринт с хорошим балансом между длинными коридорами и тупиками.
 *
 * @implNote Сложность: O(N log N), где N - количество узлов. Использование случайных весов обеспечивает равномерное
 *     распределение путей. Массив {@code parent} используется для отслеживания связей между узлами.
 */
public class PrimGenerator extends AbstractGenerator {
    @Override
    public Maze generate(int width, int height) {
        var priorityQueue = new PriorityQueue<Node>();
        var visited = new HashSet<Point>();

        var builder = new MazeBuilder(width, height);
        Point start = getRandomStartPoint(width, height);

        var parent = new Point[builder.height][builder.width];
        priorityQueue.add(new Node(start, RANDOM.nextInt()));

        while (!priorityQueue.isEmpty()) {
            Node node = priorityQueue.poll();
            Point current = node.point();

            if (visited.contains(current)) continue;

            for (Direction direction : Direction.getDirections()) {
                var neighbor = new Point(current.x() + direction.deltaX * 2, current.y() + direction.deltaY * 2);

                if (!builder.isInBounds(neighbor) || visited.contains(neighbor)) continue;

                priorityQueue.add(new Node(neighbor, RANDOM.nextInt()));
                parent[neighbor.y()][neighbor.x()] = current;
            }

            Point prev = parent[current.y()][current.x()];
            if (prev != null) {
                Point mid = new Point((current.x() + prev.x()) / 2, (current.y() + prev.y()) / 2);
                builder.setCell(mid, CellType.PASS);
            }

            builder.setCell(current, CellType.PASS);
            visited.add(current);
        }

        return builder.build();
    }
}
