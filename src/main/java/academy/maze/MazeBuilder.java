package academy.maze;

import static academy.maze.MazeConfig.BORDER_WIDTH;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Point;

/**
 * Строитель лабиринта, предоставляющий удобный API для поэтапного создания лабиринта. Обеспечивает управление границами
 * и проверку валидности операций.
 */
public final class MazeBuilder {
    /** Двумерный массив ячеек лабиринта. Индексы: [y][x], где y - строка, x - столбец */
    private final CellType[][] cells;

    /** Полная ширина лабиринта включая границы */
    public final int width;

    /** Полная высота лабиринта включая границы */
    public final int height;

    /**
     * Создает строитель лабиринта с указанными размерами. Фактический размер лабиринта будет увеличен на
     * {@link MazeConfig#BORDER_WIDTH} * 2 для добавления границ со всех сторон.
     *
     * @param width внутренняя ширина лабиринта (без учета границ)
     * @param height внутренняя высота лабиринта (без учета границ)
     * @throws IllegalArgumentException если width или height <= 0
     */
    public MazeBuilder(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Maze width and height must be bigger than zero");
        }

        this.width = width + BORDER_WIDTH * 2;
        this.height = height + BORDER_WIDTH * 2;

        this.cells = new CellType[this.height][this.width];
        createEmptyGrid();
    }

    /** Инициализирует сетку лабиринта стенами. Вызывается автоматически при создании строителя. */
    private void createEmptyGrid() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[y][x] = CellType.WALL;
            }
        }
    }

    /**
     * Проверяет, находится ли точка внутри внутренней области лабиринта (исключая границы).
     *
     * @param point точка для проверки
     * @return true если точка находится внутри рабочей области лабиринта, false если точка находится на границе или вне
     *     лабиринта
     */
    public boolean isInBounds(Point point) {
        return point.x() > 0 && point.x() < width - BORDER_WIDTH && point.y() > 0 && point.y() < height - BORDER_WIDTH;
    }

    /**
     * Устанавливает тип ячейки в указанной точке. Операция выполняется только если точка находится внутри рабочей
     * области.
     *
     * @param point точка для установки типа ячейки
     * @param type тип ячейки
     * @return {@code true} если операция выполнена успешно, {@code false} если точка вне границ
     */
    public boolean setCell(Point point, CellType type) {
        if (isInBounds(point)) {
            cells[point.y()][point.x()] = type;
            return true;
        }

        return false;
    }

    /**
     * Создает объект лабиринта на основе текущего состояния строителя.
     *
     * @return новый объект {@link Maze} с текущей конфигурацией ячеек
     */
    public Maze build() {
        return new Maze(cells, height, width);
    }
}
