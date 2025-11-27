package academy.maze.dto;

/**
 * Узел для алгоритмов поиска пути с приоритетом. Используется в приоритетной очереди для обработки точек в порядке их
 * веса.
 *
 * @param point точка в лабиринте
 * @param weight вес/приоритет точки для обработки в алгоритмах (A*, Дейкстра)
 */
public record Node(Point point, long weight) implements Comparable<Node> {
    @Override
    public int compareTo(Node node) {
        return Long.compare(weight, node.weight);
    }
}
