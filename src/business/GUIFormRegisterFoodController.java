package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import data.JsonUtils;
import domain.DailyMenu;
import domain.Food;
import domain.Meal;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.ComboBox;

import javafx.scene.control.RadioButton;

public class GUIFormRegisterFoodController {
	@FXML
	private RadioButton RBBreakfast;
	@FXML
	private ToggleGroup food;
	@FXML
	private RadioButton RBLunch;
	@FXML
	private ComboBox<String> CBDayServiceApplies;
	@FXML
	private TextField TFNameService;
	@FXML
	private TextField TFPriceService;
	@FXML
	private Button ButtonReturn;
	@FXML
	private Button ButtonSave;
	@FXML
	private Label LNULL;
	@FXML
	private Button ButtonUpdateFood;
	@FXML
	public void initialize() {
		CBDayServiceApplies.getItems().addAll("Lunes","Martes","Miercoles","Jueves","Viernes");
		CBDayServiceApplies.getSelectionModel().selectFirst();
	}
	@FXML
	public void ButtonSaveAction(ActionEvent event) {
	    String dayService = CBDayServiceApplies.getValue();
	    String nameService = TFNameService.getText();
	    String priceText = TFPriceService.getText();
	    boolean isBreakfast = RBBreakfast.isSelected();
	    String foodType = isBreakfast ? "Desayuno" : "Almuerzo";
	    
	    // Validar si los campos están completos
	    if (nameService.isEmpty() || priceText.isEmpty()) {
	        LNULL.setText("Por favor complete todos los campos.");
	        return;
	    }

	    // Intentar convertir el precio a un número
	    double price;
	    try {
	        price = Double.parseDouble(priceText);
	    } catch (NumberFormatException e) {
	        LNULL.setText("Por favor ingrese un precio válido.");
	        return;
	    }

	    // Obtener el nombre del archivo JSON correcto
	    DailyMenu dailyMenu = new DailyMenu(dayService);
	    String fileName = dailyMenu.getFileNameForSelection(isBreakfast);
	    
	    // Verificar que el archivo JSON no esté vacío
	    if (fileName.isEmpty()) {
	        LNULL.setText("Día o tipo de comida no válido.");
	        return;
	    }

	    // Mostrar el diálogo de confirmación
	    Object[] options = {"Registrar", "Cancelar"};
	    int confirmOption = JOptionPane.showOptionDialog(
	        null,
	        "¿Está seguro de agregar un nuevo servicio para el día " + dayService + " al horario " + foodType + "?",
	        "Confirmación de Registro",
	        JOptionPane.DEFAULT_OPTION,
	        JOptionPane.PLAIN_MESSAGE,
	        null,
	        options,
	        options[0]
	    );

		if (confirmOption == 0) {
			try {
				JsonUtils<DailyMenu> jsonUtils = new JsonUtils<>(fileName);
				DailyMenu menu = jsonUtils.getElement(DailyMenu.class);

				List<Meal> meals = menu.getMeals();
				meals.add(new Meal(nameService, price));
				menu.setMeals(meals);

				jsonUtils.saveDailyMenu(menu);

				// Leer el archivo JSON nuevamente para asegurar que los datos estén actualizados
				DailyMenu updatedMenu = jsonUtils.getElement(DailyMenu.class);
				System.out.println("Alimentos actualizados: " + updatedMenu);

				LNULL.setText("Nuevo servicio agregado exitosamente.");
			} catch (IOException e) {
				LNULL.setText("Error al guardar el nuevo servicio.");
				e.printStackTrace();
			}
		} else {
			LNULL.setText("Se canceló el registro de servicio.");
		}
	}

	// Event Listener on Button[#ButtonReturn].onAction
	@FXML
	public void ButtonUpdateFoodAction(ActionEvent event) {
		
	}
	// Event Listener on Button[#ButtonReturn].onAction
	@FXML
	public void ButtonReturnAction(ActionEvent event) {
    	try {
	        // Cargar el archivo FXML
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIMenuAdmin.fxml"));
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
	        System.out.println("Error al abrir GUIMenuAdmin.fxml: " + e.getMessage());
	    }
	}
	// Event Listener on Button[#ButtonReturn].onAction
	@FXML
	public void ButtonSeeAlimentAction(ActionEvent event) {
    	try {
	        // Cargar el archivo FXML
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUITableFood.fxml"));
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
	        System.out.println("Error al abrir GUITableFood.fxml: " + e.getMessage());
	    }
	}
	public void setFood(Food food) {
	    TFNameService.setText(food.getNameFood());
	    TFPriceService.setText(String.valueOf(food.getPrice()));
	    if (food.getSchedule().equals("Desayuno")) {
	        RBBreakfast.setSelected(true);
	    } else {
	        RBLunch.setSelected(true);
	    }
	    CBDayServiceApplies.setValue(food.getDayService());
	}
}
