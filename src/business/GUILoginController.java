package business;
import java.io.IOException;

import javax.swing.JOptionPane;

import data.UsersData;
import domain.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GUILoginController {
    @FXML
    private TextField TFIdentification;
    @FXML
    private Button ButtonLogin;
    @FXML
    private PasswordField TFPassword;
    @FXML
    private TextField TFIP;

    @FXML
    public void ButtonLoginAction(ActionEvent event) {
        String identificationUser = TFIdentification.getText();
        String password = TFPassword.getText();
        Users user = UsersData.validateUser(identificationUser, password);
        String ip = TFIP.getText();

        if (user != null) {
            if (user.getType() == 1) {
            	   if (!ip.isEmpty()) {
                       JOptionPane.showMessageDialog(null, "Este espacio (Direccion IP) va en blanco, solo los estudiantes lo llenan.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                       TFIP.clear(); 
                       return;
                   }
                System.out.println("Bienvenido, Personal!");
                openPersonalInterface(user);
            } else if (user.getType() == 0) {
            	  if (ip.isEmpty()) {
                      JOptionPane.showMessageDialog(null, "Por favor, ingrese su IP.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                      return;
                  }
                System.out.println("Bienvenido, Estudiante!");
                openStudentInterface(user);
            }
        } else {
    
            JOptionPane.showMessageDialog(null, "Credenciales no válidas o acceso denegado.", "Error de Login", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openPersonalInterface(Users user) {
        try {
            // Cargar el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIMenuAdmin.fxml"));
            Parent root = loader.load();

            // Obtener el controlador y pasarle el usuario
            GUIMenuAdminController controller = loader.getController();
            controller.setCurrentUser(user); // Necesitas crear este método en GUIMenuAdminController
            controller.loadUserName();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Registro de estudiantes");
            stage.show();

            // Cerrar la ventana actual
            Stage currentStage = (Stage) ButtonLogin.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al abrir GUIPrincipal.fxml: " + e.getMessage());
        }
        System.out.println("Interfaz de personal abierta.");
    }

    private void openStudentInterface(Users user) {
       	try {
	        // Cargar el archivo FXML
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIPrincipal.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.setTitle("Registro de estudiantes");
	        stage.show();
	        Stage currentStage = (Stage) ButtonLogin.getScene().getWindow();
	        currentStage.close(); 
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("Error al abrir GUIPrincipal.fxml: " + e.getMessage());
	    }
        System.out.println("Interfaz de estudiante abierta.");
    }
}
