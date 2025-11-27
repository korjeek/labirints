package academy.maze.dto;

/**
 * Тип ячейки в лабиринте. {@code WALL} - стена, {@code PASS} - свободная ячейка, {@code PATH} - путь, {@code ENTER} -
 * вход {@code EXIT} - выход
 */
public enum CellType {
    WALL,
    PASS,
    PATH,
    ENTER,
    EXIT
}
