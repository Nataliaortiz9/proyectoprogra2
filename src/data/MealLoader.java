package data;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.DailyMenu;
import java.io.File;
import java.io.IOException;

public class MealLoader {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static DailyMenu loadMenuFromFile(String filePath) {
        try {
            return objectMapper.readValue(new File(filePath), DailyMenu.class);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo JSON: " + e.getMessage());
            return null;
        }
    }
}
