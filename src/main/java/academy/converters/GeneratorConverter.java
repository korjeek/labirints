package academy.converters;

import academy.maze.generator.DFSGenerator;
import academy.maze.generator.Generator;
import academy.maze.generator.KruskalGenerator;
import academy.maze.generator.PrimGenerator;
import picocli.CommandLine.ITypeConverter;
import picocli.CommandLine.TypeConversionException;

/**
 * Конвертер для преобразования строковых идентификаторов алгоритмов в объекты генераторов лабиринтов. Используется в
 * CLI для поддержки выбора алгоритма генерации через аргументы командной строки.
 *
 * <p>Поддерживаемые алгоритмы:
 * <li>{@code dfs} - генератор на основе поиска в глубину
 * <li>{@code prim} - генератор на основе алгоритма Прима
 * <li>{@code kruskal} - генератор на основе алгоритма Краскала
 */
public class GeneratorConverter implements ITypeConverter<Generator> {
    /**
     * Преобразует строковый идентификатор алгоритма в соответствующий объект генератора. Сравнение выполняется без
     * учета регистра.
     *
     * @param value строковый идентификатор алгоритма
     * @return объект генератора лабиринта
     * @throws TypeConversionException если передан неизвестный идентификатор алгоритма
     */
    @Override
    public Generator convert(String value) throws Exception {
        return switch (value.toLowerCase()) {
            case "dfs" -> new DFSGenerator();
            case "prim" -> new PrimGenerator();
            case "kruskal" -> new KruskalGenerator();
            default -> throw new TypeConversionException("Unknown algorithm: " + value);
        };
    }
}
