package academy.maze;

import static academy.maze.MazeConfig.BORDER_WIDTH;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Point;
import academy.maze.styles.StylesStorage;
import java.util.List;

/**
 * Парсер для преобразования текстового представления лабиринта в объект {@link Maze}. Обрабатывает строковое
 * представление лабиринта, преобразуя символы в соответствующие типы ячеек.
 */
public class MazeParser {
    /**
     * Преобразует текстовое представление лабиринта в объект {@link Maze}. Ожидает, что лабиринт имеет границы шириной
     * {@link MazeConfig#BORDER_WIDTH} с каждой стороны.
     *
     * @param rows список строк, представляющих лабиринт
     * @return объект {@link Maze}, созданный на основе текстового представления
     * @throws IllegalArgumentException если:
     *     <ul>
     *       <li>список строк пуст
     *       <li>строки имеют разную длину
     *       <li>лабиринт имеет несовместимые размеры с учетом границ
     *     </ul>
     */
    public Maze parse(List<String> rows) {
        if (rows == null || rows.isEmpty()) {
            throw new IllegalArgumentException("Maze rows cannot be null or empty");
        }

        var styles = new StylesStorage();
        int height = rows.size();
        int width = rows.getFirst().length();
        var builder = new MazeBuilder(width - 2 * BORDER_WIDTH, height - 2 * BORDER_WIDTH);

        for (int y = 0; y < height; y++) {
            if (rows.get(y).length() != width) {
                throw new IllegalArgumentException("All maze rows must have the same length.");
            }
            for (int x = 0; x < width; x++) {
                var point = new Point(x, y);
                CellType type = styles.getCellType("unicode", rows.get(y).charAt(x));
                builder.setCell(point, type);
            }
        }

        return builder.build();
    }
}
