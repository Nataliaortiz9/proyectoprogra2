package data;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import domain.Meal;

public class BreakfastData {
	// Define la clase pública `BreakfastData` que manejará la lógica relacionada con los desayunos.

    public static ArrayList<Meal> breakfastList = new ArrayList<>();
    // Declara una lista estática de `Meal` para almacenar los desayunos.

    private static final String fileName = "Breakfast.json";
    // Define una constante `fileName` que contiene el nombre del archivo JSON donde se guardarán los desayunos.

    private static JsonUtils<Meal> jsonUtils = new JsonUtils<>(fileName);
    // Crea una instancia estática de `JsonUtils`, parametrizada con `Meal`, utilizando `fileName` para leer y escribir en el archivo JSON.

    public static List<Meal> getBreakfastList(){
        // Define un método público y estático que devuelve una lista de desayunos (`Meal`).

        try {
            return jsonUtils.getElements(Meal.class);
            // Intenta obtener la lista de desayunos desde el archivo JSON utilizando el método `getElements` de `jsonUtils`.

        } catch(IOException e) {
            e.printStackTrace();
            // Si ocurre una excepción de entrada/salida, imprime la traza de la pila de errores.
        }
        return null;
        // Si se produce una excepción, devuelve `null`.
    }

    public static String getMealStringFormat(Meal meal) {
        // Define un método público y estático que recibe un objeto `Meal` y devuelve una cadena formateada con la información de la comida.

        return "\nComida: " + meal.getName() +
               // Devuelve una cadena que incluye el nombre de la comida.
               "\nPrecio: " + meal.getPrice();
               // Incluye el precio de la comida.
    }

    public static boolean saveMeal(Meal meal) {
        // Define un método público y estático que guarda un objeto `Meal` en el archivo JSON y devuelve un booleano.

        System.out.println(meal);
        // Imprime en la consola la información de la comida que se está guardando.

        try {
            jsonUtils.saveElement(meal);
            // Intenta guardar la comida en el archivo JSON usando el método `saveElement` de `jsonUtils`.

            return true;
            // Si la operación es exitosa, devuelve `true`.

        } catch(IOException e) {
            e.printStackTrace();
            // Si ocurre una excepción de entrada/salida, imprime la traza de la pila de errores.
        }
        return true;
        // Siempre devuelve `true`, incluso si se produce una excepción (posible error lógico).
    }

    public static boolean deleteMeal(Meal meal) {
        // Define un método público y estático que elimina un objeto `Meal` del archivo JSON y devuelve un booleano indicando si la eliminación fue exitosa.

        try {
            boolean isDelete = jsonUtils.deleteElement(meal);
            // Intenta eliminar la comida del archivo JSON utilizando el método `deleteElement` de `jsonUtils`.

            return isDelete;
            // Devuelve `true` si la eliminación fue exitosa, `false` en caso contrario.

        } catch(IOException e) {
            e.printStackTrace();
            // Si ocurre una excepción de entrada/salida, imprime la traza de la pila de errores.
        }
        return false;
        // Si ocurre una excepción, devuelve `false`.
    }

    public static void saveBreakfastListToFile(List<Meal> breakfastList) throws IOException {
        // Define un método público y estático que guarda toda la lista de desayunos en el archivo JSON. Lanza una excepción `IOException` si ocurre un error.

        jsonUtils.saveElements(breakfastList);
        // Utiliza el método `saveElements` de `jsonUtils` para guardar toda la lista de desayunos en el archivo JSON.
    }

    public static boolean updateMeal(Meal updateMeal) {
        // Define un método público y estático que actualiza un desayuno existente en el archivo JSON y devuelve un booleano.

        try {
            List<Meal> breakfastList = getBreakfastList();
            // Obtiene la lista actual de desayunos desde el archivo JSON.

            for(int i = 0; i < breakfastList.size(); i++) {
                if(breakfastList.get(i).getName().equals(updateMeal.getName())) {
                    // Busca la comida que tiene el mismo nombre que la comida que se desea actualizar.

                    breakfastList.set(i, updateMeal);
                    // Si lo encuentra, reemplaza la comida antigua con la nueva en la lista.

                    saveBreakfastListToFile(breakfastList);
                    // Guarda la lista actualizada en el archivo JSON.

                    return true;
                    // Devuelve `true` si la actualización fue exitosa.
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
            // Si ocurre una excepción de entrada/salida, imprime la traza de la pila de errores.
        }
        return false;
        // Si no se encuentra la comida o se produce un error, devuelve `false`.
    }

}
