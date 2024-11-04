package data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import domain.Users;

public class UsersData {

    // Método para insertar un nuevo usuario
    public static boolean insertUser(Users user) {
        try {
            Connection cn = DBConnection.getConnection();
            String query = "INSERT INTO tbuser (identificationUser, password, type, photoRoute, nameUser) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = cn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            // Encriptar la contraseña antes de establecerla en la consulta
            String encryptedPassword = encryptPassword(user.getPassword());
            if (encryptedPassword == null) {
                System.out.println("Error: No se pudo encriptar la contraseña.");
                return false;
            }
            // Establecer los parámetros de la consulta
            stmt.setString(1, user.getIdentificationUser());
            stmt.setString(2, encryptedPassword); // Usa la contraseña encriptada
            stmt.setInt(3, user.getType());
            stmt.setString(4, user.getPhotoRoute());
            stmt.setString(5, user.getNameUser());


            stmt.executeUpdate();

            // Obtener el ID generado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                user.setId(generatedId);
                System.out.println("Usuario agregado con ID: " + generatedId);
            }

            stmt.close();
            cn.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error insertUser: " + e.getMessage());
            return false;
        }
    }
    public static String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b)); // Convierte cada byte a hexadecimal
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error encryptPassword: " + e.getMessage());
            return null;
        }
    }

    // Método para actualizar un usuario
    public static void updateUser(Users user) {
        try {
            Connection cn = DBConnection.getConnection();
            String query = "{call spUpdateUser(?, ?, ?, ?, ?, ?)}";
            CallableStatement stmt = cn.prepareCall(query);

            stmt.setInt(1, user.getId());
            stmt.setString(2, user.getIdentificationUser());
            stmt.setString(3, user.getPassword());
            stmt.setInt(4, user.getType());
            stmt.setString(5, user.getPhotoRoute());
            stmt.setString(6, user.getNameUser());
            


            stmt.execute();
            stmt.close();
            cn.close();
            System.out.println("Usuario actualizado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error updateUser: " + e.getMessage());
        }
    }

    // Método para eliminar un usuario
    public static void deleteUser(int idUser) {
        try {
            Connection cn = DBConnection.getConnection();
            String query = "{call spDeleteUser(?)}";
            CallableStatement stmt = cn.prepareCall(query);

            stmt.setInt(1, idUser);

            stmt.execute();
            stmt.close();
            cn.close();
            System.out.println("Usuario eliminado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error deleteUser: " + e.getMessage());
        }
    }

    // Método para listar todos los usuarios
    public static List<Users> getUsers() {
        List<Users> users = new ArrayList<>();
        try {
            Connection cn = DBConnection.getConnection();
            String query = "{call spListUser()}";
            CallableStatement stmt = cn.prepareCall(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Users user = new Users();
                //user.setIdentificationUser(rs.getString("id"));
                user.setIdentificationUser(rs.getString("identificationUser"));
                user.setPassword(rs.getString("password"));
                user.setType(rs.getInt("type"));
                user.setPhotoRoute(rs.getString("photoRoute"));
                user.setNameUser(rs.getString("nameUser"));
                users.add(user);
            }

            rs.close();
            stmt.close();
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error getUsers: " + e.getMessage());
        }
        return users;
    }
    
 // Método para verificar si la contraseña ingresada coincide con la almacenada
    public static boolean checkPassword(String plainPassword, String encryptedPassword) {
        // Encripta la contraseña ingresada para comparar
        String encryptedInputPassword = encryptPassword(plainPassword);
        System.out.println(" encriptada "+ encryptedInputPassword);
        return encryptedInputPassword != null && encryptedInputPassword.equals(encryptedPassword);
    }

    public static Users validateUser(String identificationUser, String password) {
        Users user = null; // Inicializado como null para devolver null si el usuario no se encuentra
        try {
            Connection cn = DBConnection.getConnection();
            if (cn == null) {
                System.out.println("Error: No se pudo establecer la conexión con la base de datos.");
                return null;
            }
            String query = "{call spValidateLoginUser(?)}";
            CallableStatement stmt = cn.prepareCall(query);

            // Configuración de los parámetros para el procedimiento almacenado
            stmt.setString(1, identificationUser);

            // Ejecución de la consulta
            ResultSet rs = stmt.executeQuery();

            System.out.println("Ejecutando consulta: " + stmt.toString()); // Mensaje de depuración

            if (rs.next()) {
            	System.out.println("encontro usuario");
                user = new Users(); // Inicializa el usuario solo si encuentra resultados
                //comparar las dos comtrasenas, la contrasena encriptada y la que no 
                boolean match = checkPassword(password,rs.getString("password"));
                if(match) {
                	 System.out.println("hizo match");
                	 user.setIdentificationUser(rs.getString("id"));
                     user.setIdentificationUser(rs.getString("identificationUser"));
                     user.setPassword(rs.getString("password"));
                     user.setType(rs.getInt("type"));
                     user.setPhotoRoute(rs.getString("photoRoute"));
                     user.setNameUser(rs.getString("nameUser"));
                }else {
                	return null;
                }
            } else {
                System.out.println("No se encontró el usuario o la contraseña es incorrecta.");
                return null;
            }

            rs.close();
            stmt.close();
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error validateUser: " + e.getMessage());
            return null;
        }
        return user;
    }
	public static boolean isDuplicateCarnet(String carnet) {
	    List<Users> userList = getUsers();
	    if (userList != null) { 
	        for (Users user : userList) {
	            if (user.getIdentificationUser().equals(carnet)) {
	                return true; // Encontrado carnet duplicado
	            }
	        }
	    }
	    return false; // No se encontró carnet duplicado
	}
	public static boolean saveUser(Users user) {
	    String query = "INSERT INTO tbuser (carnet, name) VALUES (?, ?)";

	    try (
	        Connection conn = DBConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(query)
	    ) {
	        stmt.setString(1, user.getIdentificationUser());
	        stmt.setString(2, user.getNameUser());
	    

	    } catch (SQLException e) {
	    	System.out.println(e.toString());
	        e.printStackTrace();
	    }

	    return false;  // Retorna false si ocurrió algún error
	}

	public static void saveStudent(Users user) {
	    String query = "INSERT INTO tbuser (carnet, name) VALUES (?, ?)";

	    try (
	        Connection conn = DBConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(query)
	    ) {
	        stmt.setString(1, user.getIdentificationUser());
	        stmt.setString(2, user.getNameUser());
	    

	    } catch (SQLException e) {
	    	System.out.println(e.toString());
	        e.printStackTrace();
	    }
	}

}
