package academy.maze.styles;

import academy.maze.dto.CellType;
import java.util.Map;

public record StylesConfig(Map<CellType, Character> symbols, Map<String, Character> connections) {}
