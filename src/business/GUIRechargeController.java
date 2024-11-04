package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;

import data.JsonUtils;
import data.RechargeData;
import data.StudentData;
import domain.Recharge;
import domain.Student;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.ComboBox;

import javafx.scene.control.DatePicker;

public class GUIRechargeController {
	@FXML
	private ComboBox<String> CBStudent;
	@FXML
	private TextField TFAmount;
	@FXML
	private DatePicker DPDateRecharge;
	@FXML
	private Label LDate;
	@FXML
	private Label LNULL;
	@FXML
	private Button ButtonRecharge;
	@FXML
	private Button ButtonReturn;
	@FXML
	private ObservableList<Student> student;
	
	@FXML
	public void initialize() {
		// Crear una instancia de la clase JsonUtils para manejar operaciones JSON relacionadas con la clase Student.
		// Se le pasa el nombre del archivo JSON "Student.json" como argumento al constructor.
		JsonUtils<Student> jsonUtils = new JsonUtils<>("Student.json");
		try {
		    // Llamar al método getStudentCarnets() de la instancia jsonUtils para obtener una lista de carnets de estudiantes.
		    // Esta lista contiene los carnets como cadenas de texto (List<String>).
		    List<String> studentCarnets = jsonUtils.getStudentCarnets();
		    // Convertir la lista de carnets de estudiantes en una ObservableList, que es un tipo de lista especial
		    // utilizada en JavaFX para notificar cambios automáticos a la interfaz de usuario.
		    ObservableList<String> carnetsObservableList = FXCollections.observableArrayList(studentCarnets);
		    // Establecer la ObservableList como los ítems (elementos) de un ComboBox (CBCarnet) en la interfaz de usuario.
		    // Esto llena el ComboBox con los carnets de los estudiantes.
		    CBStudent.setItems(carnetsObservableList);
		} catch (IOException e) {
		    // Capturar cualquier excepción que ocurra al intentar obtener los carnets de los estudiantes desde el archivo JSON.
		    // Esto podría ocurrir si el archivo no se encuentra, está malformado, etc.
		    // Imprimir la traza de la excepción para ayudar a depurar el problema.
		    e.printStackTrace();

		    // Mostrar un mensaje en la consola indicando que hubo un error al cargar los carnets de los estudiantes.
		    // El mensaje incluye la descripción específica del error.
		    System.out.println("Error al cargar los carnets de los estudiantes: " + e.getMessage());
		}
		
	}
	
	

	// Event Listener on Button[#ButtonRecharge].onAction
	@FXML
	public void ButtonRechargeAction(ActionEvent event) {
		// Obtener el carnet del estudiante seleccionado en el ComboBox CBStudent.
		String selectedCarnet = CBStudent.getSelectionModel().getSelectedItem();

		// Si no hay ningún carnet seleccionado, notificar al usuario y salir del método.
		if (selectedCarnet == null) {
		    notifyAction("Por favor, seleccione un estudiante.");
		    return;
		}

		// Leer los datos de los estudiantes desde el archivo JSON.
		List<Student> students = StudentData.getStudentList();

		// Buscar al estudiante correspondiente al carnet seleccionado.
		Student selectedStudent = null;
		for (Student student : students) {
		    if (student.getCarnet().equals(selectedCarnet)) {
		        selectedStudent = student;
		        break;
		    }
		}

		// Si no se encuentra al estudiante, notificar al usuario y salir del método.
		if (selectedStudent == null) {
		    notifyAction("Estudiante no encontrado.");
		    return;
		}

		// Obtener el monto ingresado en el TextField TFAmount.
		String amountText = TFAmount.getText();

		// Validar que el campo de monto no esté vacío.
		if (amountText.isEmpty()) {
		    notifyAction("Por favor, ingrese un monto de recarga.");
		    return;
		}

		// Convertir el monto a un valor numérico.
		double amount;
		try {
		    amount = Double.parseDouble(amountText);
		} catch (NumberFormatException e) {
		    // Capturar y manejar el error si el monto ingresado no es un número válido.
		    notifyAction("Por favor, ingrese un monto válido.");
		    return;
		}

		// Sumar el monto ingresado al dinero disponible del estudiante.
		double newBalance = selectedStudent.getMoneyAvailable() + amount;

		// Validar que el nuevo saldo esté dentro del rango permitido (1000 - 10000 colones).
		if (newBalance < 1000 || newBalance > 10000) {
		    notifyAction("Monto inválido. La recarga debe dejar el saldo entre 1000 y 10000 colones.");
		    return;
		}

		// Actualizar el saldo del estudiante con el nuevo balance.
		selectedStudent.setMoneyAvailable(newBalance);
		System.out.println(selectedStudent.toString());

		// Obtener la fecha seleccionada en el DatePicker DPDataRecharge.
		LocalDate rechargeDate = DPDateRecharge.getValue();

		// Validar que se haya seleccionado una fecha.
		if (rechargeDate == null) {
		    notifyAction("Por favor, seleccione una fecha de recarga.");
		    return;
		}

		// Crear un nuevo objeto de recarga con los datos actuales.
		Recharge newRecharge = new Recharge(
		    selectedCarnet,     // Carnet del estudiante
		    amount,            // Monto de la recarga
		    rechargeDate       // Fecha de la recarga
		);

		// Obtener la lista de recargas existentes desde el archivo JSON.
		List<Recharge> recharges = RechargeData.getRechargeList();
		
		// Si la lista es nula, inicializarla como una lista vacía.
		if (recharges == null) {
		    recharges = new ArrayList<>();
		}

		// Agregar la nueva recarga a la lista.
		recharges.add(newRecharge);

		// Guardar la lista actualizada de recargas en el archivo JSON.
		try {
		    RechargeData.saveRechargeListToFile(recharges);
		} catch (IOException e) {
		    // Capturar y manejar errores relacionados con la lectura o escritura de archivos JSON.
		    notifyAction("Error al guardar la recarga: " + e.getMessage());
		    e.printStackTrace();
		    return;
		}
		
		
        // Actualizar la información del estudiante en el archivo JSON.
       if(StudentData.updateStudent(selectedStudent) == false) {
    	   notifyAction("No se actualizo el estudiante");  
       }
        

		// Notificar al usuario que la recarga se realizó con éxito.
		notifyAction("Recarga realizada con éxito.");
		
		
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
	
	
	@FXML
	private void notifyAction(String message) {
		LNULL.setText(message);
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.seconds(3), e-> LNULL.setText("")));
		timeline.setCycleCount(1);
		timeline.play();
		
	}
}
