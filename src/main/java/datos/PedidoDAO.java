/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

/**
 *
 * @author catalvman
 */
import Domain.Pedido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {
    private static final String SQL_SELECT = "SELECT idPedido, fecha, numero, numDireccion, idCliente FROM Pedido";
    private static final String SQL_INSERT = "INSERT INTO Pedido (fecha, numero, numDireccion, idCliente) VALUES (?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE Pedido SET fecha = ?, numero = ?, numDireccion = ?, idCliente = ? WHERE idPedido = ?";
    private static final String SQL_DELETE = "DELETE FROM Pedido WHERE idPedido = ?";

    public List<Pedido> seleccionar() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Pedido pedido = null;
        List<Pedido> pedidos = new ArrayList<>();
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int idPedido = rs.getInt("idPedido");
                Timestamp fecha = rs.getTimestamp("fecha");
                int numero = rs.getInt("numero");
                int numDireccion = rs.getInt("numDireccion");
                int idCliente = rs.getInt("idCliente");

                pedido = new Pedido(idPedido, fecha, numero, numDireccion, idCliente);
                pedidos.add(pedido);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return pedidos;
    }

    public int insertar(Pedido pedido) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setTimestamp(1, pedido.getFecha());
            stmt.setInt(2, pedido.getNumero());
            stmt.setInt(3, pedido.getNumDireccion());
            stmt.setInt(4, pedido.getIdCliente());
            registros = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return registros;
    }

    public boolean eliminar(int idPedido) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean rowDeleted = false;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, idPedido);
            rowDeleted = stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rowDeleted;
    }

    public boolean actualizar(Pedido pedido) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean rowUpdated = false;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setTimestamp(1, pedido.getFecha());
            stmt.setInt(2, pedido.getNumero());
            stmt.setInt(3, pedido.getNumDireccion());
            stmt.setInt(4, pedido.getIdCliente());
            stmt.setInt(5, pedido.getIdPedido());
            rowUpdated = stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rowUpdated;
    }
}

