package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import data.StudentData;
import data.UsersData;
import domain.Student;
import domain.Users;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;



public class GUIRegisterStudentController {
	@FXML
	private TextField TFCarnet;
	@FXML
	private TextField TFName;
	@FXML
	private TextField TFType;
	@FXML
	private TextField TFPhotoRoute;
	@FXML
	private Button buttonRegisterStudent;
	@FXML
	private Label LNULL;
	@FXML
	private Button ButtonEditStudent;
	@FXML
	private Button ButtonSeeStudent;
	@FXML
	private Button ButtonReturn;
	@FXML
	private TextField TFPassword;
	@FXML 
	private Button ButtonSearchPhoto;
	@FXML 
    private boolean isEditing = false; 
    private Student currentStudent; 
    private Users currentUser;
    
 // Método para configurar el usuario actual (llámalo después de autenticación)
    public void setCurrentUser(Users user) {
        this.currentUser = user;
    }
    
	public void initialize() {
		
	}
	
	public void setStudent(Student student) {
		if(student != null) {
			TFCarnet.setEditable(false);
			TFName.setText(student.getName());
			TFCarnet.setText(student.getCarnet());
			currentStudent = student;
			isEditing = true;
		}
	}
	// Event Listener on Button[#buttonRegisterStudent].onAction
	@FXML
	public boolean buttonRegisterStudentAction(ActionEvent event) {
        String messageError = validateForm();
        if (!messageError.isEmpty()) {
            notifyAction(messageError);
            return false;
        }
        
        Users user = new Users();
        user.setIdentificationUser(TFCarnet.getText());
        user.setNameUser(TFName.getText());
        user.setPassword(TFPassword.getText());
        user.setType(0);
        user.setPhotoRoute(TFPhotoRoute.getText());
        if (UsersData.isDuplicateCarnet(user.getIdentificationUser()) && (currentUser == null || !currentUser.getIdentificationUser().equals(user.getIdentificationUser()))) {
            notifyAction("El ID ya está en uso. Por favor, elija otro.");
            return false;
        }

        Object[] options = {"Cancelar", "Registrar"};
        int confirmOption = JOptionPane.showOptionDialog(
            null, "¿Está seguro de guardar?", "Confirmación de Registro",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, options, options[0]
        );

        if (confirmOption == 1) {
            if (UsersData.insertUser(user)) {
                notifyAction("Usuario registrado correctamente");
                clearForm();
            } else {
                notifyAction("Error en el registro de Usuarios");
            }
        } else {
            notifyAction("Se canceló el registro de usuario");
            clearForm();
        }
        return true;
    }
	// Event Listener on Button[#btnEditImage].onAction
	@FXML
	public void ButtonSearchPhotoAction(ActionEvent event) {
	    FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle("Seleccionar Imagen");

	    // Filtrar para mostrar solo archivos de imagen
	    FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Archivos de Imagen", "*.png", "*.jpg", "*.jpeg");
	    fileChooser.getExtensionFilters().add(imageFilter);

	    // Mostrar el explorador de archivos y obtener el archivo seleccionado
	    File file = fileChooser.showOpenDialog(new Stage());

	    if (file != null) {
	        // Establecer la ruta del archivo seleccionado en el TextField
	        TFPhotoRoute.setText(file.getAbsolutePath());
	    }
	}

	// Event Listener on Button[#ButtonEditStudent].onAction
	@FXML
	public void ButtonEditStudentAction(ActionEvent event)throws IOException {
		if(currentStudent !=null) {
			String messageError = validateForm();
			if(!messageError.isEmpty()) {
				notifyAction(messageError);
				return;
			}
			
			//actualizamos a estudiante
			currentStudent.setName(TFName.getText());
	        //Guardamos la informacion del estudiante actualizada
	        if(StudentData.updateStudent(currentStudent)) {
	        	notifyAction("El estudiante se ha actualizado correctamente");
	        	clearForm();
	        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUITableStudent.fxml"));
                Parent root = loader.load();
                GUITableStudentController controller = loader.getController();
                controller.refreshTableStudent();
	        }else {
	        	notifyAction("Error al actualizar los datos del estudiante");
	        }
	       
		}else {
			 JOptionPane.showMessageDialog(null, "No se ha seleccionado ningun estudiante para editar.");
		}
	}
	// Event Listener on Button[#ButtonSeeStudent].onAction
	@FXML
	public void ButtonSeeStudentAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUITableStudent.fxml"));
			Parent root = loader.load();
			GUITableStudentController controller = loader.getController();
			controller.loadStudentList();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			//buttonsee
			stage.setOnCloseRequest(e->controller.closeWindows());
			Stage temp = (Stage) this.buttonRegisterStudent.getScene().getWindow();
			temp.close();
			
		}catch(IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	private String validateForm() {
		String messageError = "";
		if(TFCarnet.getText().isEmpty()) {
			messageError += "\nEl carné es requerido.";
		}else if(TFCarnet.getText().length()>10) {
			messageError += "\nEl carné no puede tener más de 10 letras.";
		}if(TFName.getText().isEmpty()) {
			messageError += "\nEl nombre es requerido.";
		}
		return messageError;
	}
	private void clearForm() {
		TFCarnet.setText("");
		TFName.setText("");
	}
	
	@FXML
	private void notifyAction(String message) {
		LNULL.setText(message);
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.seconds(3), e-> LNULL.setText("")));
		timeline.setCycleCount(1);
		timeline.play();
		
	}
	
	// Event Listener on Button[#buttonRegisterStudent].onAction
	@FXML
	public void ButtonReturnAction(ActionEvent event) {
    	try {
	        // Cargar el archivo FXML
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIMenuAdmin.fxml"));
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
	
}
