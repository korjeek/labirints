package academy.maze.styles;

import academy.ResourceLoader;
import academy.maze.dto.CellType;
import java.util.HashMap;
import java.util.Map;

/**
 * Хранилище стилей отрисовки лабиринта. Обеспечивает преобразование между типами ячеек и символами отрисовки, а также
 * управление соединениями стен для различных стилей.
 *
 * <p>Поддерживает двунаправленное преобразование:
 * <li>{@link CellType} -> Character (для отрисовки)
 * <li>Character -> {@link CellType} (для парсинга)
 */
public class StylesStorage {
    /** Карта конфигураций стилей отрисовки. Ключ - название стиля, значение - конфигурация стиля */
    private static final Map<String, StylesConfig> CELLS_TO_CHARS;
    /**
     * Обратная карта для преобразования символов в типы ячеек. Ключ - название стиля, значение - карта символов к типам
     * ячеек
     */
    private static final Map<String, Map<Character, CellType>> CHARS_TO_CELLS = new HashMap<>();

    // Статическая инициализация - загрузка конфигураций и построение обратных карт
    static {
        var resourceLoader = new ResourceLoader();
        CELLS_TO_CHARS = resourceLoader.loadRenderStylesConfig().styles();
        buildCharsToCellsMap();
    }

    private void validateStyleExists(String styleName) {
        if (!CELLS_TO_CHARS.containsKey(styleName)) {
            throw new IllegalArgumentException(
                    "Unknown rendering style: '" + styleName + "'. Available styles: " + CELLS_TO_CHARS.keySet());
        }
    }

    /**
     * Возвращает символ для отрисовки указанного типа ячейки в заданном стиле.
     *
     * @param styleName название стиля отрисовки (например, "unicode", "ascii")
     * @param type тип ячейки лабиринта
     * @return символ для отображения ячейки
     */
    public Character getSymbol(String styleName, CellType type) {
        validateStyleExists(styleName);
        return CELLS_TO_CHARS.get(styleName).symbols().get(type);
    }

    /**
     * Возвращает символ соединения стен для заданного шаблона в указанном стиле. Используется для отрисовки соединений
     * между стенами в продвинутых стилях.
     *
     * @param styleName название стиля отрисовки
     * @param pattern шаблон соединений в формате битовой строки (например, "1010")
     * @return символ соединения стен
     */
    public Character getConnection(String styleName, String pattern) {
        validateStyleExists(styleName);
        return CELLS_TO_CHARS.get(styleName).connections().get(pattern);
    }

    /**
     * Определяет тип ячейки по символу в указанном стиле. Используется для обратного преобразования при парсинге
     * лабиринта.
     *
     * @param styleName название стиля отрисовки
     * @param character символ для распознавания
     * @return тип ячейки, соответствующий символу, или {@code CellType.WALL} по умолчанию
     */
    public CellType getCellType(String styleName, Character character) {
        validateStyleExists(styleName);
        return CHARS_TO_CELLS.get(styleName).getOrDefault(character, CellType.WALL);
    }

    /**
     * Строит обратные карты преобразования символов в типы ячеек для всех стилей. Вызывается один раз при инициализации
     * класса для оптимизации последующих запросов.
     */
    private static void buildCharsToCellsMap() {
        CELLS_TO_CHARS.forEach((styleName, stylesConfig) -> {
            Map<Character, CellType> reverseSymbols = new HashMap<>();

            if (stylesConfig.symbols() != null)
                stylesConfig.symbols().forEach((type, symbol) -> reverseSymbols.put(symbol, type));

            CHARS_TO_CELLS.put(styleName, reverseSymbols);
        });
    }
}
