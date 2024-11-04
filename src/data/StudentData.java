package data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import domain.Student;

public class StudentData {
	public static ArrayList<Student> studentList = new ArrayList();
	private static final String fileName = "Student.json";
	private static JsonUtils<Student> jsonUtils = new JsonUtils<>(fileName);
	
	public static List<Student> getStudentList(){
		try {
			return jsonUtils.getElements(Student.class);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getStudentStringFormat(Student student) {
	    return "\nCarné: " + student.getCarnet() +
	           "\nEstudiante: " + student.getName().toUpperCase();
	}
	
    public static boolean saveStudent(Student student) {
        if (isDuplicateCarnet(student.getCarnet())) {
            System.out.println("Error: El carnet ya existe. No se puede registrar el estudiante.");
            return false; // No guardar si el carnet ya existe
        }

        try {
            jsonUtils.saveElement(student);
            return true;
        } catch(IOException e) {
            e.printStackTrace();
        }
        return false;
    }
	
	public static boolean deleteStudent(Student student) {
		try {
			boolean isDelete = jsonUtils.deleteElement(student);
			return isDelete;
		}catch(IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void saveStudentListToFile(List<Student> studentList) throws IOException{
		jsonUtils.saveElements(studentList);
	}
	
	public static boolean updateStudent(Student updateStudent) {
		try {
			List<Student> studentList = getStudentList();
			for(int i =0; i<studentList.size(); i++) {
				if(studentList.get(i).getCarnet().equals(updateStudent.getCarnet())) {
					studentList.set(i, updateStudent);
					saveStudentListToFile(studentList);
					return true;
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean isDuplicateCarnet(String carnet) {
	    List<Student> studentList = getStudentList();
	    if (studentList != null) { 
	        for (Student student : studentList) {
	            if (student.getCarnet().equals(carnet)) {
	                return true; // Encontrado carnet duplicado
	            }
	        }
	    }
	    return false; // No se encontró carnet duplicado
	}


}
