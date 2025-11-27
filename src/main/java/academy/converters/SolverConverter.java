package academy.converters;

import academy.maze.solver.AStarSolver;
import academy.maze.solver.BFSSolver;
import academy.maze.solver.DijkstraSolver;
import academy.maze.solver.Solver;
import picocli.CommandLine.ITypeConverter;
import picocli.CommandLine.TypeConversionException;

/**
 * Конвертер для преобразования строковых идентификаторов алгоритмов в объекты решателей лабиринтов. Используется в CLI
 * для поддержки выбора алгоритма решения через аргументы командной строки.
 *
 * <p>Поддерживаемые алгоритмы:
 * <li>{@code astar} - алгоритм A* (A-Star) с эвристическим поиском
 * <li>{@code dijkstra} - алгоритм Дейкстры для поиска кратчайшего пути
 * <li>{@code bfs} - поиск в ширину (Breadth-First Search)
 */
public class SolverConverter implements ITypeConverter<Solver> {
    /**
     * Преобразует строковый идентификатор алгоритма в соответствующий объект решателя. Сравнение выполняется без учета
     * регистра.
     *
     * @param value строковый идентификатор алгоритма
     * @return объект решателя лабиринта
     * @throws TypeConversionException если передан неизвестный идентификатор алгоритма
     */
    @Override
    public Solver convert(String value) throws Exception {
        return switch (value.toLowerCase()) {
            case "astar" -> new AStarSolver();
            case "dijkstra" -> new DijkstraSolver();
            case "bfs" -> new BFSSolver();
            default -> throw new TypeConversionException("Unknown algorithm: " + value);
        };
    }
}
