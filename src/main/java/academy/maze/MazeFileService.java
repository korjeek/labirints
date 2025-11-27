package academy.maze;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Сервис для работы с файлами лабиринтов. Обеспечивает сохранение и загрузку текстового представления лабиринтов. */
public class MazeFileService {
    /**
     * Сохраняет текстовое представление лабиринта в файл.
     *
     * @param text текстовое представление лабиринта
     * @param filePath путь к файлу для сохранения
     * @throws RuntimeException если произошла ошибка при записи файла
     */
    public void saveMazeToFile(String text, String filePath) {
        try (PrintWriter writer = new PrintWriter(filePath, StandardCharsets.UTF_8)) {
            writer.print(text);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save maze to file: " + filePath + ". Reason: " + e.getMessage());
        }
    }

    /**
     * Загружает лабиринт из файла и возвращает его в виде списка строк. Каждая строка представляет одну строку
     * лабиринта.
     *
     * @param filePath путь к файлу с лабиринтом
     * @return список строк, представляющих лабиринт
     * @throws RuntimeException если произошла ошибка при чтении файла
     */
    public List<String> loadMazeFromFile(String filePath) {
        List<String> rows = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                rows.add(line);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading maze file: " + filePath + ". Reason: " + e.getMessage());
        }

        return rows;
    }
}
