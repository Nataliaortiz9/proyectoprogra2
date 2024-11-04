package data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import domain.DailyMenu;
import domain.Meal;
import domain.Student;

public class JsonUtils<T> {
	
	private final String filePath;
	private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());//despues de register es para utilizar el formato de local date
	
	//constructor
	public JsonUtils(String route) {
		this.filePath = route;
	}

	public void saveElement(T t) throws IOException {
		List<T> temp = getElements((Class<T>) t.getClass()); //parseo   obtengo la lista
		temp.add(t);
		mapper.writeValue(new File(filePath), temp);//sobre que archivo voy a escribir, y que voy a escribir    
	}
	
	public void saveDailyMenu(T t) throws IOException {
		mapper.writeValue(new File(filePath), t);//sobre que archivo voy a escribir, y que voy a escribir    
	}

	public void saveElements(List<T> elements) throws IOException {
	    mapper.writeValue(new File(filePath), elements);
	}

	//revienta algo y se lo manda al otro, el puede devolverlo, es una cadena de errores throws
	public List<T> getElements(Class<T> temp) throws IOException { //de que elemento voy a recuperar archivos
		File file = new File(filePath); //java.io
		if(!file.exists()) {//cuando no encuentre devuelva en blanco, sino existe
			return new ArrayList<>();
		}
		//construya una nueva lista de lo que yo le envie por parametro
		//file de json y luego contruyalo, cuando ya esta el factory, contruya una coleccion de tipo en este caso list, y temp es el java class
		return mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, temp));
	}
	
	public T getElement(Class<T> temp) throws IOException {
	    File file = new File(filePath);
	    if (!file.exists()) {
	        return null; // Retorna null si el archivo no existe
	    }

	    // Lee el archivo JSON y convierte el contenido a un objeto DayMeal
	    return mapper.readValue(file, temp);
	}
	
	
	public boolean deleteElement(T t) throws IOException{
		//una lista del json
		List<T> temp = getElements((Class<T>) t.getClass());
		
		if (t.getClass().getName().equals("domain.Student")) {
			//recore la lista de objetos del json
			for (T t2 : temp) {
				//convierte el t a tipo student
				Student student = (Student) t2;
				//compara el carnet del objeto que se le paso con el objeto original
				if(student.getCarnet().equals(((Student) t).getCarnet())) {
					temp.remove(t2);
					//actualiza el json con el objeto eliminado
					mapper.writeValue(new File(filePath), temp);
					//si se logro eliminar
					return true;
				}
			}
		} else {
			boolean removed = temp.remove(t);
			if (removed) {
				mapper.writeValue(new File(filePath), temp);
				return true;
			}
		}

		return false;
		
	}
	
    public List<String> getStudentNames() throws IOException {
        List<Map<String, Object>> studentMaps = mapper.readValue(new File(filePath), new TypeReference<List<Map<String, Object>>>() {});
        List<String> names = new ArrayList<>();
        for (Map<String, Object> studentMap : studentMaps) {
            String name = (String) studentMap.get("name");
            names.add(name);
        }
        return names;
    }
    
    
    public List<String> getStudentCarnets() throws IOException {
        // Leer el archivo JSON y convertirlo en una lista de mapas, donde cada mapa representa un estudiante.
        List<Map<String, Object>> studentMaps = mapper.readValue(new File(filePath), new TypeReference<List<Map<String, Object>>>() {});
        // Crear una lista para almacenar los carnets de los estudiantes.
        List<String> carnets = new ArrayList<>();
        // Iterar a través de la lista de mapas (estudiantes).
        for (Map<String, Object> studentMap : studentMaps) {
            // Obtener el carnet del estudiante desde el mapa.
            String carnet = (String) studentMap.get("carnet");
            // Agregar el carnet a la lista de carnets.
            carnets.add(carnet);
        } // Retornar la lista de carnets.
        return carnets;
    }

	public static List<Meal> readFoodDataFromJson(String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Meal> meals = new ArrayList<>();
        
        // Leer el archivo JSON como JsonNode
        JsonNode rootNode = objectMapper.readTree(new File(fileName));
        
        // Extraer la lista de comidas
        JsonNode mealsNode = rootNode.path("meals");
        
        if (mealsNode.isArray()) {
            for (JsonNode mealNode : mealsNode) {
                Meal meal = objectMapper.treeToValue(mealNode, Meal.class);
                meals.add(meal);
            }
        } else {
            System.out.println("No se encontraron comidas en el archivo JSON."); // Mensaje de depuración
        }
        
       // System.out.println("Comidas leídas del JSON: " + meals); // Imprimir lista de comidas
        return meals;
    }

    public List<Student> readStudentsFromJson() throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();  // Retorna una lista vacía si el archivo no existe
        }
        // Lee la lista de estudiantes desde el archivo JSON
        return mapper.readValue(file, new TypeReference<List<Student>>() {});
    }
    
    public List<Double> getStudentBalances() throws IOException {
        List<Map<String, Object>> studentMaps = mapper.readValue(new File(filePath), new TypeReference<List<Map<String, Object>>>() {});
        List<Double> balances = new ArrayList<>();
        for (Map<String, Object> studentMap : studentMaps) {
            Double balance = (Double) studentMap.get("moneyAvailable");
            balances.add(balance);
        }
        return balances;
    }

    public void saveMeal(Meal newMeal, String fileName) throws IOException {
        File file = new File(fileName);
        JsonNode rootNode;
        
        // Leer el archivo JSON existente
        if (file.exists()) {
            rootNode = mapper.readTree(file);
        } else {
            rootNode = mapper.createObjectNode();
        }

        // Obtener la lista de comidas
        JsonNode mealsNode = rootNode.path("meals");
        if (!mealsNode.isArray()) {
            ((ObjectNode) rootNode).set("meals", mapper.createArrayNode());
        }

        // Añadir el nuevo alimento
        ArrayNode mealsArray = (ArrayNode) rootNode.path("meals");
        ObjectNode mealNode = mapper.valueToTree(newMeal);
        mealsArray.add(mealNode);

        // Guardar el archivo JSON con la estructura actualizada
        mapper.writeValue(file, rootNode);
    }
    // Método para guardar una comida
    public void saveMeal(Meal meal) throws IOException {
        List<Meal> meals = getMeals(); // Obtener la lista de comidas existentes
        meals.add(meal); // Añadir la nueva comida
        saveMeals(meals); // Guardar la lista actualizada
    }

    // Método para obtener la lista de comidas
    private List<Meal> getMeals() throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        return mapper.readValue(file, new TypeReference<List<Meal>>() {});
    }

    // Método para guardar la lista de comidas
    private void saveMeals(List<Meal> meals) throws IOException {
        mapper.writeValue(new File(filePath), meals);
 	}


}
