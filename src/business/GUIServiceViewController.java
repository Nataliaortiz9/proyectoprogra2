package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import data.JsonUtils;
import data.MealData;
import domain.Meal;
import domain.Student;

public class GUIServiceViewController {

    @FXML
    private ComboBox<String> CBStudent;
    @FXML
    private ComboBox<String> CBDayReservation;
    @FXML
    private RadioButton RBLunch;
    @FXML
    private ToggleGroup food;
    @FXML
    private RadioButton RBBreakfast;
    @FXML
    private TableView<Meal> TableServiceView;
    @FXML
    private TableColumn<Meal, String> ColumnFood;
    @FXML
    private TableColumn<Meal, String> ColumnPrice;
    @FXML
    private TableColumn<Meal, String> ColumnRequest;
    @FXML
    private Button ButtonReturn;
    @FXML
    private Button ButtonAddNewFood;
    @FXML
    private Button ButtonRequest;
    @FXML
    private Label LNULL;
	@FXML
	private ObservableList<Meal> meal;
	private Student student = new Student();
	private MealData mealData = new MealData();
    @FXML
    
    public void initialize() {
    	meal = FXCollections.observableArrayList();
    	ColumnFood.setCellValueFactory(new PropertyValueFactory<>("name"));
    	ColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    	CBDayReservation.getItems().addAll("Lunes","Martes","Miercoles","Jueves","Viernes");
    	CBDayReservation.getSelectionModel().selectFirst();
        JsonUtils<Student> jsonUtils = new JsonUtils<>("Student.json");
        try {
            List<String> studentNames = jsonUtils.getStudentNames();
            ObservableList<String> namesObservableList = FXCollections.observableArrayList(studentNames);
            CBStudent.setItems(namesObservableList);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar los nombres de los estudiantes: " + e.getMessage());
        }
       // Agregar listeners para el ComboBox y RadioButton
        CBDayReservation.valueProperty().addListener((observable, oldValue, newValue) -> {
            loadDataBasedOnSelection();
        });

        RBBreakfast.selectedProperty().addListener((observable, oldValue, newValue) -> {
            loadDataBasedOnSelection();
        });
    }
    @FXML
    public void loadDataBasedOnSelection() {
        String selectedDay = CBDayReservation.getValue();
        boolean isBreakfastSelected = RBBreakfast.isSelected();
        String fileName = mealData.getFileNameForSelection(selectedDay, isBreakfastSelected);

        try {
        	 List<Meal> foodItems = JsonUtils.readFoodDataFromJson(fileName);
        	System.out.println(foodItems);
            ObservableList<Meal> observableFoodItems = FXCollections.observableArrayList(foodItems);
            TableServiceView.setItems(observableFoodItems);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar los datos de comida: " + e.getMessage());
        }
    }

    // Event Listener on Button[#ButtonReturn].onAction
    @FXML
    public void ButtonRequestAction(ActionEvent event) {
    	handleSelectAction();
    }
    
    private void handleSelectAction() {
        Meal mealSelect = TableServiceView.getSelectionModel().getSelectedItem();
        JsonUtils<Student> jsonUtils = new JsonUtils<>("student.json");
        
        if (mealSelect == null) {
            notifyAction("Por favor, selecciona una comida.");
            return;
        }

        try {
            // Obtener lista de estudiantes desde el archivo JSON
            List<Student> students = jsonUtils.readStudentsFromJson();
            // Obtener el estudiante seleccionado desde el ComboBox
            String selectedStudentName = CBStudent.getValue();
            Student selectedStudent = students.stream()
                                              .filter(student -> student.getName().equals(selectedStudentName))
                                              .findFirst()
                                              .orElse(null);

            if (selectedStudent != null) {
                double studentBalance = selectedStudent.getMoneyAvailable();
                double mealPrice = mealSelect.getPrice();

                if (studentBalance >= mealPrice) {
                    double newBalance = studentBalance - mealPrice;
                    selectedStudent.setMoneyAvailable(newBalance);
                    jsonUtils.saveElements(students); // Guarda los cambios en el archivo JSON

                    JOptionPane.showMessageDialog(null,"Pedido realizado con éxito. Nuevo saldo: " + newBalance);
                } else {
                	JOptionPane.showMessageDialog(null,"Saldo insuficiente para realizar el pedido.");
                }
            } else {
                notifyAction("Estudiante no encontrado.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            notifyAction("Error al procesar la solicitud.");
        }
    }

    
    
    // Event Listener on Button[#ButtonReturn].onAction
    @FXML
    public void ButtonReturnAction(ActionEvent event) {
    	try {
	        // Cargar el archivo FXML
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIPrincipal.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.setTitle("Registro de estudiantes");
	        stage.show();
	        Stage currentStage = (Stage) ButtonReturn.getScene().getWindow();
	        currentStage.close(); 
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("Error al abrir GUIPrincipal.fxml: " + e.getMessage());
	    }
    }

    // Event Listener on Button[#ButtonAddNewFood].onAction
    @FXML
    public void ButtonAddNewFoodAction(ActionEvent event) {
    	try {
	        // Cargar el archivo FXML
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIFormRegisterFood.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.setTitle("Registro de estudiantes");
	        stage.show();
	        Stage currentStage = (Stage) ButtonAddNewFood.getScene().getWindow();
	        currentStage.close(); 
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("Error al abrir GUIFormRegisterFood.fxml: " + e.getMessage());
	    }
    }
	@FXML
	private void notifyAction(String message) {
		LNULL.setText(message);
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.seconds(3), e-> LNULL.setText("")));
		timeline.setCycleCount(1);
		timeline.play();
		
	}
}

