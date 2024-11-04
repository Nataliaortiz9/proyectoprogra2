package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private final static String database = "proyectoprogramacion";
	private final static String user = "root";
	private final static String pass = "";
	private final static int port = 3306;
	private final static String host = "localhost";
	private final static String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
	private static Connection con;//java.sql
	
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try { 
			con = DriverManager.getConnection(url, user, pass);
			System.out.println("Se establecio correctamente la conexion");
		}catch(SQLException e) {
			System.out.println("Error de conexion " + e.getMessage());
		}
		return con;
	}
}
