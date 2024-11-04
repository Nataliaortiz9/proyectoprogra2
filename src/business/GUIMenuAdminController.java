package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;
import domain.Orders;
import domain.Users;

public class GUIMenuAdminController {
	@FXML
	private TableView<Orders> TableOrder;
	@FXML
	private TableColumn<Orders, String> ColumnID;
	@FXML
	private TableColumn<Orders, String> ColumnNameProduct;
	@FXML
	private TableColumn<Orders, String> ColumnQuantity;
	@FXML
	private TableColumn<Orders, String> ColumnTotal;
	@FXML
	private TableColumn<Orders, String> ColumnState;
	@FXML
	private TableColumn<Orders, String> ColumnIDStudent;
	@FXML
	private Button ButtonAddPlate;
	@FXML
	private Button ButtonUpdatePlate;
	@FXML
	private Button ButtonDeletePlate;
	@FXML
	private Button ButtonRegisterStudent;
	@FXML
	private Button ButtonReadStudent;
	@FXML
	private Button ButtonUpdateStudent;
	@FXML
	private Button ButtonDeleteStudent;
	@FXML
	private TextField TFNamePersonal;
	@FXML
	private Button ButtonEditProfile;
	@FXML
	private Button ButtonConfirmation;
	@FXML
	private Label LNULL;
	@FXML
	private ImageView ImageProfile;
	   // Variable para almacenar el usuario autenticado
    private Users currentUser;
    public void initialize() {//URL location, ResourceBundle resources
    	loadUserName();
       /* try {
            // Cargar la imagen de perfil por defecto
            Image image = new Image(getClass().getResource("/PhotoProfile/PhotoMujer3.png").toExternalForm());
            ImageProfile.setImage(image);

            // Llamar al método para cargar y mostrar el nombre del usuario
            loadUserName();

        } catch (NullPointerException e) {
            System.err.println("La imagen no se encontró. Verifica la ruta del archivo.");
        }*/
    }

    // Método para cargar y mostrar el nombre del usuario en TFNamePersonal
    public void loadUserName() {
        if (currentUser != null) {
            // Establece el nombre en el TextField TFNamePersonal
            TFNamePersonal.setText(currentUser.getNameUser());
        } else {
            System.out.println("Usuario no encontrado. No se ha iniciado sesión.");
        }
    }

    // Método para configurar el usuario actual
    public void setCurrentUser(Users user) {
        this.currentUser = user;
    }
	// Event Listener on Button[#ButtonAddPlate].onAction
	@FXML
	public void ButtonAddPlateAction(ActionEvent event) {
    	try {
	        // Cargar el archivo FXML
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIFormRegisterFood.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.setTitle("Registro de estudiantes");
	        stage.show();
	        Stage currentStage = (Stage) ButtonRegisterStudent.getScene().getWindow();
	        currentStage.close(); 
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("Error al abrir GUIFormRegisterFood.fxml: " + e.getMessage());
	    }
	}
	// Event Listener on Button[#ButtonUpdatePlate].onAction
	@FXML
	public void ButtonUpdatePlate(ActionEvent event) {
    	try {
	        // Cargar el archivo FXML
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUITableFood.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.setTitle("Registro de estudiantes");
	        stage.show();
	        Stage currentStage = (Stage) ButtonRegisterStudent.getScene().getWindow();
	        currentStage.close(); 
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("Error al abrir GUITableFood.fxml: " + e.getMessage());
	    }
	}
	// Event Listener on Button[#ButtonDeletePlate].onAction
	@FXML
	public void ButtonDeletePlate(ActionEvent event) {
    	try {
	        // Cargar el archivo FXML
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUITableFood.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.setTitle("Registro de estudiantes");
	        stage.show();
	        Stage currentStage = (Stage) ButtonRegisterStudent.getScene().getWindow();
	        currentStage.close(); 
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("Error al abrir GUITableFood.fxml: " + e.getMessage());
	    }
	}
	// Event Listener on Button[#ButtonRegisterStudent].onAction
	@FXML
	public void ButtonRegisterStudentAction(ActionEvent event) {
    	try {
	        // Cargar el archivo FXML
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIRegisterStudent.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.setTitle("Registro de estudiantes");
	        stage.show();
	        Stage currentStage = (Stage) ButtonRegisterStudent.getScene().getWindow();
	        currentStage.close(); 
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("Error al abrir GUIRegisterStudent.fxml: " + e.getMessage());
	    }
	}
	// Event Listener on Button[#ButtonReadStudent].onAction
	@FXML
	public void ButtonReadStudentAction(ActionEvent event) {
    	try {
	        // Cargar el archivo FXML
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUITableStudent.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.setTitle("Lista de estudiantes");
	        stage.show();
	        Stage currentStage = (Stage) ButtonReadStudent.getScene().getWindow();
	        currentStage.close(); 
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("Error al abrir GUIPrincipal.fxml: " + e.getMessage());
	    }
	}
	// Event Listener on Button[#ButtonUpdateStudent].onAction
	@FXML
	public void ButtonUpdateStudent(ActionEvent event) {
    	try {
	        // Cargar el archivo FXML
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIRegisterStudent.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.setTitle("Registro de estudiantes");
	        stage.show();
	        Stage currentStage = (Stage) ButtonRegisterStudent.getScene().getWindow();
	        currentStage.close(); 
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("Error al abrir GUIPrincipal.fxml: " + e.getMessage());
	    }
	}
	// Event Listener on Button[#ButtonDeleteStudent].onAction
	@FXML
	public void ButtonDeleteStudentAction(ActionEvent event) {
    	try {
	        // Cargar el archivo FXML
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUITableStudent.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.setTitle("Lista de estudiantes");
	        stage.show();
	        Stage currentStage = (Stage) ButtonReadStudent.getScene().getWindow();
	        currentStage.close(); 
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("Error al abrir GUIPrincipal.fxml: " + e.getMessage());
	    }
	}
	// Event Listener on Button[#ButtonEditProfile].onAction
	@FXML
	public void ButtonEditProfileAction(ActionEvent event) {
    	try {
	        // Cargar el archivo FXML
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIEditUser.fxml"));
	        Parent root = loader.load();
	        GUIEditUserController controller = loader.getController();
	        controller.setCurrentUser(this.currentUser);
	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.setTitle("Editar Perfil");
	        stage.show();
	        Stage currentStage = (Stage) ButtonRegisterStudent.getScene().getWindow();
	        currentStage.close(); 
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("Error al abrir GUIEditUser.fxml: " + e.getMessage());
	    }
	}
	// Event Listener on Button[#ButtonConfirmation].onAction
	@FXML
	public void ButtonConfirmationAction(ActionEvent event) {
		// TODO Autogenerated
	}
}
