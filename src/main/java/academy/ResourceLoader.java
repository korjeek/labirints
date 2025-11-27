package academy;

import academy.maze.styles.RenderConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Properties;

public class ResourceLoader {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public RenderConfig loadRenderStylesConfig() {
        try {
            var stream = ResourceLoader.class.getClassLoader().getResourceAsStream("RenderStyles.json");
            return MAPPER.readValue(stream, RenderConfig.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load render styles config", e);
        }
    }

    public Properties loadMazeConfig() {
        try {
            var stream = ResourceLoader.class.getClassLoader().getResourceAsStream("maze.properties");
            var props = new Properties();
            props.load(stream);
            return props;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load maze config", e);
        }
    }
}
