package academy.maze.generator;

import academy.maze.MazeBuilder;
import academy.maze.dto.CellType;
import academy.maze.dto.Direction;
import academy.maze.dto.Maze;
import academy.maze.dto.Point;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;

/**
 * Генератор лабиринта с использованием алгоритма поиска в глубину (DFS).
 *
 * <p>Алгоритм создает идеальный лабиринт (без циклов и изолированных областей) путем рекурсивного исследования
 * пространства с возвратом (backtracking).
 *
 * <p>Алгоритм работает следующим образом:
 *
 * <ol>
 *   <li>Выбирается случайная стартовая точка
 *   <li>Точка помечается как посещенная и добавляется в стек
 *   <li>Пока стек не пуст:
 *       <ul>
 *         <li>Извлекается текущая точка из стека
 *         <li>В случайном порядке проверяются соседние клетки (через одну)
 *         <li>Если соседняя клетка валидна и не посещена:
 *             <ul>
 *               <li>Пробивается проход через промежуточную стену
 *               <li>Сосед добавляется в стек и помечается посещенным
 *             </ul>
 *       </ul>
 * </ol>
 *
 * <p>Результат - лабиринт с длинными коридорами и малым количеством тупиков.
 *
 * @implNote Сложность: O(width × height). Каждая клетка обрабатывается один раз.
 */
public class DFSGenerator extends AbstractGenerator {
    @Override
    public Maze generate(int width, int height) {
        Deque<Point> stack = new ArrayDeque<>();
        var visited = new HashSet<Point>();

        var builder = new MazeBuilder(width, height);
        Point start = getRandomStartPoint(width, height);

        builder.setCell(start, CellType.PASS);
        stack.push(start);
        visited.add(start);

        while (!stack.isEmpty()) {
            Point current = stack.pop();

            for (Direction direction : Direction.getShuffledDirections()) {
                var neighbor = new Point(current.x() + direction.deltaX * 2, current.y() + direction.deltaY * 2);

                if (!builder.setCell(neighbor, CellType.PASS) || visited.contains(neighbor)) continue;

                var wall = new Point(current.x() + direction.deltaX, current.y() + direction.deltaY);
                builder.setCell(wall, CellType.PASS);
                visited.add(neighbor);
                stack.push(neighbor);
            }
        }

        return builder.build();
    }
}
