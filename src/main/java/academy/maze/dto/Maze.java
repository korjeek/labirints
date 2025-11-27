package academy.maze.dto;

/**
 * Лабиринт.
 *
 * @param cells Массив ячеек лабиринта.
 * @param height Высота лабиринта с границами
 * @param width ширина лабиринта с границами
 */
public record Maze(CellType[][] cells, int height, int width) {}
