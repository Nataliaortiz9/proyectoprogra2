package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;

public class GUIPrincipalController {
	@FXML
	private Button ButtonStudent;
	@FXML
	private Button ButtonService;
	@FXML
	private Button ButtonConsultRecharge;
	@FXML
	private Button ButtonRecharge;

	// Event Listener on Button[#ButtonStudent].onAction
	@FXML
	public void ButtonStudentAction(ActionEvent event) {
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

	// Event Listener on Button[#ButtonService].onAction
	@FXML
	public void ButtonServiceAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIServiceView.fxml"));
		    Parent root = loader.load();
		    Scene scene = new Scene(root);
		    Stage stage = new Stage();
		    stage.setScene(scene);
		    stage.setTitle("Solicitar nuevo servicio");
		    stage.show();     
		} catch (IOException e) {
		    e.printStackTrace();
		    System.out.println("Error al abrir GUIServiceView.fxml: " + e.getMessage());
		}
	}
	// Event Listener on Button[#ButtonAddFood].onAction
	@FXML
	public void ButtonRechargeAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIRecharge.fxml"));
		    Parent root = loader.load();
		    Scene scene = new Scene(root);
		    Stage stage = new Stage();
		    stage.setScene(scene);
		    stage.setTitle("Solicitud de servicio");
		    stage.show();     
		} catch (IOException e) {
		    e.printStackTrace();
		    System.out.println("Error al abrir GUIFormRegisterFood.fxml: " + e.getMessage());
		}
	}
	// Event Listener on Button[#ButtonRecharge].onAction
	@FXML
	public void ButtonConsultRechargeAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUICheckBalance.fxml"));
		    Parent root = loader.load();
		    Scene scene = new Scene(root);
		    Stage stage = new Stage();
		    stage.setScene(scene);
		    stage.setTitle("Dinero Disponible");
		    stage.show();     
		} catch (IOException e) {
		    e.printStackTrace();
		    System.out.println("Error al abrir GUICheckBalance.fxml: " + e.getMessage());
		}
	}
}
