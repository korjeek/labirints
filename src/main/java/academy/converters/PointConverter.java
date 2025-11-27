package academy.converters;

import academy.maze.dto.Point;
import picocli.CommandLine.ITypeConverter;
import picocli.CommandLine.TypeConversionException;

/**
 * Конвертер для преобразования строкового представления точки в объект Point. Используется в CLI для парсинга координат
 * из аргументов командной строки.
 */
public class PointConverter implements ITypeConverter<Point> {
    /**
     * Преобразует строку в формате "x,y" в объект Point.
     *
     * @param value строковое представление точки в формате "x,y"
     * @return объект {@link Point} с соответствующими координатами
     * @throws TypeConversionException если:
     *     <ul>
     *       <li>строка не соответствует формату "x,y"
     *       <li>x или y не являются целыми числами
     *     </ul>
     */
    @Override
    public Point convert(String value) {
        try {
            String[] parts = value.split(",");
            if (parts.length != 2) {
                throw new TypeConversionException(
                        String.format("Invalid point format: %s, expected format: x,y", value));
            }
            int x = Integer.parseInt(parts[0].trim());
            int y = Integer.parseInt(parts[1].trim());

            return new Point(x, y);
        } catch (NumberFormatException e) {
            throw new TypeConversionException("Invalid number in point: " + value);
        }
    }
}
