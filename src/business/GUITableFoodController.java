package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import domain.Food;
import domain.Meal;

import java.io.IOException;
import java.util.List;

import data.JsonUtils;
import data.MealLoader;
import domain.DailyMenu;

public class GUITableFoodController {
	@FXML
	private TableView<Food>TableFood;
	@FXML
	private TableColumn<Food, String> ColumnShedule;
	@FXML
	private TableColumn<Food, String> ColumnDayService;
	@FXML
	private TableColumn<Food, String> ColumnNameFood;
	@FXML
	private TableColumn<Food, Integer> ColumnPrice;
	@FXML
	private Button ButtonDeleteFood;
	@FXML
	private Button ButtonUpdateFood;
	@FXML
	private Button ButtonReturn;
	
    private ObservableList<Food> foodData = FXCollections.observableArrayList();

    public void initialize() {
        ColumnShedule.setCellValueFactory(new PropertyValueFactory<>("schedule"));
        ColumnDayService.setCellValueFactory(new PropertyValueFactory<>("dayService"));
        ColumnNameFood.setCellValueFactory(new PropertyValueFactory<>("nameFood"));
        ColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableFood.setItems(foodData);
        loadAllMeals();  
    }
    private void loadAllMeals() {
        String[] files = {
            "Monday_lunch.json", "Monday_breakfast.json",
            "Tuesday_lunch.json", "Tuesday_breakfast.json",
            "Wednesday_lunch.json", "Wednesday_breakfast.json",
            "Thursday_lunch.json", "Thursday_breakfast.json",
            "Friday_lunch.json", "Friday_breakfast.json",
        };

        for (String file : files) {
            DailyMenu menu = MealLoader.loadMenuFromFile(file); 
            if (menu != null) {
                String dayService = menu.getDay();
                String schedule = file.contains("lunch") ? "Almuerzo" : "Desayuno";

                for (Meal meal : menu.getMeals()) {
                    Food item = new Food(schedule, dayService, meal.getName(), meal.getPrice());
                    foodData.add(item); 
                }
            }
        }
    }
	// Event Listener on Button[#ButtonDeleteFood].onAction
    @FXML
    public void ButtonDeleteFoodAction(ActionEvent event) {
        // Obtener el alimento seleccionado de la tabla
        Food selectedFood = TableFood.getSelectionModel().getSelectedItem();
        if (selectedFood != null) {
            // Mostrar alerta de confirmación
            Alert confirmAlert = new Alert(AlertType.CONFIRMATION, "¿Estás seguro de que deseas eliminar este alimento?");
            confirmAlert.setHeaderText(null);
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Eliminar el alimento de la lista observable de la tabla
                    foodData.remove(selectedFood);
                    TableFood.refresh();

                    // Eliminar el alimento del archivo JSON
                    try {
                        // Obtener el archivo JSON según el día y tipo de comida
                        String fileName = selectedFood.getFileNameForSelection();
                        JsonUtils<DailyMenu> jsonUtils = new JsonUtils<>(fileName);
                        DailyMenu menu = jsonUtils.getElement(DailyMenu.class);

                        // Eliminar el alimento de la lista de alimentos
                        List<Meal> meals = menu.getMeals();
                        meals.removeIf(meal -> meal.getName().equals(selectedFood.getNameFood()));
                        menu.setMeals(meals);

                        // Guardar los cambios en el archivo JSON
                        jsonUtils.saveDailyMenu(menu);

                        // Mostrar mensaje de éxito
                        showAlert("Alimento eliminado exitosamente.");
                    } catch (IOException e) {
                        // Mostrar mensaje de error si ocurre un problema al actualizar el archivo JSON
                        showAlert("Error al eliminar el alimento del archivo.");
                        e.printStackTrace();
                    }
                }
            });
        } else {
            showAlert("No se ha seleccionado ningún alimento para eliminar.");
        }
    }


	// Event Listener on Button[#ButtonUpdateFood].onAction
	@FXML
	public void ButtonUpdateFoodAction(ActionEvent event) {
        Food selectedFood = TableFood.getSelectionModel().getSelectedItem();
        if (selectedFood != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIFormRegisterFood.fxml"));
                Parent root = loader.load();
                
                GUIFormRegisterFoodController editController = loader.getController();
                editController.setFood(selectedFood);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                
                Stage currentStage = (Stage) ButtonUpdateFood.getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
        	showAlert("No se ha seleccionado ningún alimento para editar.");
        }
	}
	// Event Listener on Button[#ButtonReturn].onAction
	@FXML
	public void ButtonReturnAction(ActionEvent event) {
    	try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIFormRegisterFood.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.setTitle("Registros");
	        stage.show();
	        Stage currentStage = (Stage) ButtonReturn.getScene().getWindow();
	        currentStage.close(); 
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("Error al abrir GUIFormRegisterFood.fxml: " + e.getMessage());
	    }
	}
	
    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
