package academy.maze.generator;

import academy.maze.dto.Point;
import java.util.Random;

/**
 * Абстрактный базовый класс для всех генераторов лабиринтов.
 *
 * <p>Предоставляет общую функциональность, используемую различными алгоритмами генерации. Наследующие классы должны
 * реализовать метод {@link #generate} с конкретным алгоритмом генерации лабиринта.
 */
public abstract class AbstractGenerator implements Generator {
    protected static final Random RANDOM = new Random();

    /**
     * Генерирует случайную стартовую точку с нечетными координатами.
     *
     * <p>Нечетные координаты важны для корректной работы алгоритмов. Формула гарантирует нечетность координат.
     *
     * @param width ширина лабиринта
     * @param height высота лабиринта
     * @return случайная точка с нечетными координатами внутри границ лабиринта
     */
    protected Point getRandomStartPoint(int width, int height) {
        return new Point(RANDOM.nextInt(width) / 2 * 2 + 1, RANDOM.nextInt(height) / 2 * 2 + 1);
    }
}
