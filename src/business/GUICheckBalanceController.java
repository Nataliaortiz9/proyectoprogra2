package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import data.RechargeData;
import data.StudentData;
import domain.Recharge;
import domain.Student;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class GUICheckBalanceController {
	@FXML
	private TextField TFCheckBalance;
	@FXML
	private Button ButtonCheckBalance;
	@FXML
	private TableView TableCheckBalance;
	@FXML
	private TableColumn ColumnCarnet;
	@FXML
	private TableColumn ColumnStudent;
	@FXML
	private TableColumn ColumnDateRecharge;
	@FXML
	private TableColumn ColumnBalance;
	@FXML
	private Button ButtonReturn;
	@FXML
	private Button ButtonAddNewStudent;
	@FXML
	private Label LNULL;

	// Event Listener on Button[#ButtonCheckBalance].onAction
	
	public void initialize() {
		
		 // Configurar las columnas de la tabla.
        ColumnCarnet.setCellValueFactory(new PropertyValueFactory<>("carnet"));
        ColumnStudent.setCellValueFactory(new PropertyValueFactory<>("name"));
        ColumnDateRecharge.setCellValueFactory(new PropertyValueFactory<>("rechargeDate"));
        ColumnBalance.setCellValueFactory(new PropertyValueFactory<>("amount"));
	}
	
	
	@FXML
	public void ButtonCheckBalanceAction(ActionEvent event) throws IOException {
	    // Obtener el carnet ingresado en el TextField TFCheckBalance.
	    String carnet = TFCheckBalance.getText().trim();

	    // Validar que el campo de carnet no esté vacío.
	    if (carnet.isEmpty()) {
	        notifyAction("Por favor, ingrese un carnet de estudiante.");
	        return;
	    }

	    // Obtener la lista de estudiantes desde el archivo JSON.
		List<Student> students = StudentData.getStudentList();
		

		// Buscar al estudiante correspondiente al carnet ingresado.
		Student selectedStudent = null;
		for (Student student : students) {
		    if (student.getCarnet().equals(carnet)) {
		        selectedStudent = student;
		        break;
		    }
		}

		// Si el estudiante no existe, notificar al usuario y salir del método.
		if (selectedStudent == null) {
		    notifyAction("Estudiante no encontrado.");
		    return;
		}
		
		

		// Obtener la lista de recargas desde el archivo JSON.
		List<Recharge> recharges = RechargeData.getRechargeList();

		// Filtrar las recargas correspondientes al estudiante.
		List<Recharge> studentRecharges = new ArrayList<>();
		for (Recharge recharge : recharges) {
		    if (recharge.getCarnet().equals(carnet)) {
		    	recharge.setName(selectedStudent.getName());
		        studentRecharges.add(recharge);
		    }
		}

		
		
		// Limpiar la tabla existente y agregar los datos de recarga.
		TableCheckBalance.getItems().clear();
		TableCheckBalance.getItems().addAll(studentRecharges);

		// Si no hay recargas para el estudiante, notificar al usuario.
		if (studentRecharges.isEmpty()) {
		    notifyAction("No hay recargas registradas para este estudiante.");
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
	// Event Listener on Button[#ButtonAddNewStudent].onAction
	@FXML
	public void ButtonAddNewStudent(ActionEvent event) {
		try {
	        // Cargar el archivo FXML
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIRegisterStudent.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.setTitle("Registro de estudiantes");
	        stage.show();
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("Error al abrir GUIRegisterStudent.fxml: " + e.getMessage());
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
