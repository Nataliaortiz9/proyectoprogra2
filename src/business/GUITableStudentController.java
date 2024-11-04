package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

import javax.swing.JOptionPane;

import data.StudentData;
import domain.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;

public class GUITableStudentController {
	@FXML
	private TableView<Student> TableRegisterStudent;
	@FXML
	private TableColumn<Student, String> ColumnCarnet;
	@FXML
	private TableColumn<Student, String> ColumnName;
	@FXML
	private Button ButtonEditStudent;
	@FXML
	private Button ButtonDeleteStudent;
	@FXML
	private Button ButtonReturn;
	@FXML
	private ObservableList<Student> students;
	
	@FXML
	public void initialize() {
		students = FXCollections.observableArrayList();
		ColumnCarnet.setCellValueFactory(new PropertyValueFactory<>("carnet"));
		ColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableRegisterStudent.setItems(students);
		loadStudentList();

	}
	
	// Event Listener on Button[#ButtonEditStudent].onAction
	@FXML
	public void ButtonEditStudentAction(ActionEvent event) {
		handleEditAction();
	}
	// Event Listener on Button[#ButtonEditStudent].onAction
	@FXML
	public void ButtonReturnAction(ActionEvent event) {
		closeWindows();
	}
	@FXML
	public void ButtonDeleteStudentAction(ActionEvent event) {
		Student selectStudent = TableRegisterStudent.getSelectionModel().getSelectedItem();
		if(selectStudent != null) {
			int eliminate = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que deseas eliminar a este estudiante?","", JOptionPane.YES_NO_OPTION);
			if(eliminate == JOptionPane.YES_OPTION) {
				boolean success = StudentData.deleteStudent(selectStudent);
				if(success) {
					students.remove(selectStudent);
					TableRegisterStudent.refresh();
					JOptionPane.showMessageDialog(null, "Estudiante eliminado exitosamente.");
				}else {
					JOptionPane.showMessageDialog(null, "No se pudo eliminar al estudiante.");
				}
			}
		}else {
			JOptionPane.showMessageDialog(null, "No se ha seleccionado ningun estudiante.");
		}
	}
	
	
	private void handleEditAction() {
		Student selectStudent = TableRegisterStudent.getSelectionModel().getSelectedItem();
		if(selectStudent != null) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIRegisterStudent.fxml"));
				Parent root = loader.load();
				GUIRegisterStudentController registerStudentController = loader.getController();
				registerStudentController.setStudent(selectStudent);
				
				Stage stage = new Stage();
	            stage.setScene(new Scene(root));
	            stage.show();
	            Stage temp = (Stage) ButtonEditStudent.getScene().getWindow();
	            temp.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}else {
			JOptionPane.showMessageDialog(null, "No se ha seleccionado ningun estudiante");
		}
	}
	public void closeWindows() {
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIMenuAdmin.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            
            Stage temp = (Stage) ButtonReturn.getScene().getWindow();
            temp.close();
        } catch(IOException e) {
            e.printStackTrace();
        }	
	}
	public void loadStudentList() {
		if (this.students == null) {
	        this.students = FXCollections.observableArrayList();
        }
		students.clear();
		students.addAll(StudentData.getStudentList());
		TableRegisterStudent.setItems(students);
	}
	public void refreshTableStudent() {
		if(students == null) {
		   students = FXCollections.observableArrayList();
		}
		students.clear();
		students.addAll(StudentData.getStudentList());
		TableRegisterStudent.refresh();
	}
}
