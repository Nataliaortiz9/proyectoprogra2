package data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Orders;

public class OrdersData {

    // Método para insertar una nueva orden
    public static void insertOrder(Orders order) {
        try {
            Connection cn = DBConnection.getConnection();
            String query = "INSERT INTO orders (nameProduct, quantity, total, state, IDStudent) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = cn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            // Establecer los parámetros de la consulta
            stmt.setString(1, order.getNameProduct());
            stmt.setInt(2, order.getQuantity());
            stmt.setInt(3, order.getTotal());
            stmt.setString(4, order.getState());
            stmt.setString(5, order.getIDStudent());

            stmt.executeUpdate();

            // Obtener el ID generado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                order.setIdOrder(generatedId);
                System.out.println("Orden agregada con ID: " + generatedId);
            }

            stmt.close();
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error insertOrder: " + e.getMessage());
        }
    }

    // Método para actualizar una orden
    public static void updateOrder(Orders order) {
        try {
            Connection cn = DBConnection.getConnection();
            String query = "{call spUpdateOrder(?, ?, ?, ?, ?, ?)}";
            CallableStatement stmt = cn.prepareCall(query);

            // Establecer los parámetros del procedimiento almacenado
            stmt.setInt(1, order.getIdOrder());
            stmt.setString(2, order.getNameProduct());
            stmt.setInt(3, order.getQuantity());
            stmt.setInt(4, order.getTotal());
            stmt.setString(5, order.getState());
            stmt.setString(6, order.getIDStudent());

            stmt.execute();
            stmt.close();
            cn.close();
            System.out.println("Orden actualizada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error updateOrder: " + e.getMessage());
        }
    }

    // Método para eliminar una orden
    public static void deleteOrder(int idOrder) {
        try {
            Connection cn = DBConnection.getConnection();
            String query = "{call spDeleteOrder(?)}";
            CallableStatement stmt = cn.prepareCall(query);

            // Establecer el parámetro del procedimiento almacenado
            stmt.setInt(1, idOrder);

            stmt.execute();
            stmt.close();
            cn.close();
            System.out.println("Orden eliminada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error deleteOrder: " + e.getMessage());
        }
    }

    // Método para listar todas las órdenes
    public static List<Orders> getOrders() {
        List<Orders> orders = new ArrayList<>();
        try {
            Connection cn = DBConnection.getConnection();
            String query = "{call spListOrders()}";
            CallableStatement stmt = cn.prepareCall(query);
            ResultSet rs = stmt.executeQuery();

            // Recorrer el resultado y llenar la lista de órdenes
            while (rs.next()) {
                Orders order = new Orders();
                order.setIdOrder(rs.getInt("idOrder"));
                order.setNameProduct(rs.getString("nameProduct"));
                order.setQuantity(rs.getInt("quantity"));
                order.setTotal(rs.getInt("total"));
                order.setState(rs.getString("state"));
                order.setIDStudent(rs.getString("IDStudent"));
                orders.add(order);
            }

            rs.close();
            stmt.close();
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error getOrders: " + e.getMessage());
        }
        return orders;
    }
}
