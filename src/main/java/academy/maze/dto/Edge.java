package academy.maze.dto;

/**
 * Ребро между двумя узлами лабиринта с промежуточной точкой (стеной).
 *
 * @param first первый узел
 * @param mid промежуточная точка
 * @param second соседний узел
 */
public record Edge(Point first, Point mid, Point second) {}
