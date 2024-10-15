/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;
import Domain.PedidoArticulo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author catalvman
 */
public class PedidoArticuloDAO {
    private static final String SQL_SELECT = "SELECT idPedido, idArticulo, cantidad FROM PedidoArticulo";
    private static final String SQL_INSERT = "INSERT INTO PedidoArticulo (idArticulo, numero, cantidad) VALUES (?,?,?)";
    private static final String SQL_UPDATE = "UPDATE PedidoArticulo SET idArticulo = ?, cantidad = ? WHERE idPedido = ?";
    private static final String SQL_DELETE = "DELETE FROM PedidoArticulo WHERE idPedido = ?";
    private static final String SQL_TOTAL_ARTICULOS_ANIO = "SELECT SUM(pa.cantidad) AS totalCantidad " +"FROM PedidoArticulo pa " + "JOIN Pedido p ON pa.idPedido = p.idPedido " + "WHERE YEAR(p.fecha) = ?";

    public List<PedidoArticulo> seleccionar() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        PedidoArticulo pedidoArt = null;
        List<PedidoArticulo> pedidosArt = new ArrayList<>();
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int idPedido = rs.getInt("idPedido");
                int idArticulo = rs.getInt("idArticulo");
                int cantidad = rs.getInt("cantidad");
  
                pedidoArt = new PedidoArticulo(idPedido, idArticulo, cantidad);
                pedidosArt.add(pedidoArt);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return pedidosArt;
    }

    public int insertar(PedidoArticulo pedidoArt) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setInt(1, pedidoArt.getIdPedido());
            stmt.setInt(2, pedidoArt.getIdArticulo());
            stmt.setInt(3, pedidoArt.getCantidad());
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

    public boolean actualizar(PedidoArticulo pedidoArt) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean rowUpdated = false;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setInt(1, pedidoArt.getIdPedido());
            stmt.setInt(2, pedidoArt.getIdArticulo());
            stmt.setInt(3, pedidoArt.getCantidad());
            rowUpdated = stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rowUpdated;
    }

    public int obtenerTotalArticulosPorAnyo(int anyo) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int totalCantidad = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_TOTAL_ARTICULOS_ANIO);
            stmt.setInt(1, anyo); 
            rs = stmt.executeQuery();

            if (rs.next()) {
                totalCantidad = rs.getInt("totalCantidad");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException("Error al obtener la cantidad de artículos para el año " + anyo, ex);
        } finally {

            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }

        return totalCantidad; 
    }
    
}


