package academy.maze.generator;

import static academy.maze.MazeConfig.BORDER_WIDTH;

import academy.maze.DSU;
import academy.maze.MazeBuilder;
import academy.maze.dto.CellType;
import academy.maze.dto.Edge;
import academy.maze.dto.Maze;
import academy.maze.dto.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Генератор лабиринта с использованием алгоритма Крускала.
 *
 * <p>Алгоритм Крускала создает лабиринт, рассматривая его как граф, где:
 *
 * <ul>
 *   <li>Узлы - ячейки с нечетными координатами
 *   <li>Ребра - потенциальные проходы между узлами через промежуточные стены
 * </ul>
 *
 * <p>Алгоритм работает следующим образом:
 *
 * <ol>
 *   <li>Создается список всех возможных стен между соседними узлами
 *   <li>Стены перемешиваются в случайном порядке
 *   <li>Для каждой стены проверяется, соединяют ли она две непересекающиеся компоненты связности
 *   <li>Если да - стена удаляется (превращается в проход) и компоненты объединяются
 *   <li>Процесс продолжается пока все узлы не окажутся в одной компоненте связности
 * </ol>
 *
 * @implSpec Алгоритм использует систему непересекающихся множеств {@link DSU} для эффективного отслеживания связности
 *     компонентов лабиринта
 * @implNote Сложность: O(N log N), где N - количество узлов лабиринта
 */
public class KruskalGenerator extends AbstractGenerator {
    @Override
    public Maze generate(int width, int height) {
        var builder = new MazeBuilder(width, height);
        var dsu = new DSU(builder.height * builder.width);

        List<Edge> edges = getEdges(builder.width, builder.height);
        Collections.shuffle(edges, new Random());

        for (Edge edge : edges) {
            int idx1 = edge.first().y() * width + edge.first().x();
            int idx2 = edge.second().y() * width + edge.second().x();

            if (!dsu.connected(idx1, idx2)) {
                builder.setCell(edge.first(), CellType.PASS);
                builder.setCell(edge.mid(), CellType.PASS);
                builder.setCell(edge.second(), CellType.PASS);
                dsu.union(idx1, idx2);
            }
        }

        return builder.build();
    }

    /**
     * Генерирует список всех возможных стен между узлами лабиринта.
     *
     * <p>Каждая стена представлена объектом {@link Edge}, содержащим:
     *
     * <ul>
     *   <li>{@code first()} - первый узел
     *   <li>{@code mid()} - промежуточная клетка (стена, которую можно удалить)
     *   <li>{@code second()} - соседний узел
     * </ul>
     *
     * @param width ширина лабиринта с учётом границы
     * @param height высота лабиринта с учётом границы
     * @return список всех возможных горизонтальных и вертикальных стен между узлами
     */
    private List<Edge> getEdges(int width, int height) {
        List<Edge> edges = new ArrayList<>();

        for (int y = BORDER_WIDTH; y < height - 2; y += 2) {
            for (int x = BORDER_WIDTH; x < width - 2; x += 2) {
                Point current = new Point(x, y);
                if (x < width) {
                    Point mid = new Point(x + 1, y);
                    Point neighbor = new Point(x + 2, y);
                    edges.add(new Edge(current, mid, neighbor));
                }
                if (y < height) {
                    Point neighbor = new Point(x, y + 2);
                    Point mid = new Point(x, y + 1);
                    edges.add(new Edge(current, mid, neighbor));
                }
            }
        }

        return edges;
    }
}
