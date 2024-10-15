/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import Domain.Articulo;
import static datos.Conexion.getConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author catalvman
 */
public class ArticuloDAO {
    private static final String SQL_SELECT = "SELECT idArticulo, descripcion FROM Articulo";
    private static final String SQL_INSERT = "INSERT INTO Articulo (idArticulo, descripcion) VALUES (?,?)";
    private static final String SQL_UPDATE = "UPDATE Articulo SET descripcion = ? WHERE idArticulo = ?";
    private static final String SQL_DELETE = "DELETE FROM Articulo WHERE idArticulo = ?";
   // private static final String SQL_SELECT_BY_YEAR = "SELECT PA.cantidad FROM Pedido P JOIN PedidoArticulo PA ON P.idPedido = PA.idPedido WHERE YEAR(P.fecha) = ?";

    public List<Articulo> seleccionar() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Articulo articulo = null;
        List<Articulo> articulos = new ArrayList<>();
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int idArticulo = rs.getInt("idArticulo");
                String descripcion = rs.getString("descripcion");
                articulo = new Articulo(idArticulo, descripcion);
                articulos.add(articulo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(conn);
            Conexion.close(rs);
            Conexion.close(stmt);
        }
        return articulos;
    }

    public int insertar(Articulo articulo) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setInt(1, articulo.getIdArticulo());
            stmt.setString(2, articulo.getDescripcion());
            registros = stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return registros;
    }

    public boolean eliminar(int idArticulo) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean rowDeleted = false;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, idArticulo);
            rowDeleted = stmt.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rowDeleted;
    }

    public boolean actualizar(Articulo articulo) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean rowUpdated = false;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setInt(1, articulo.getIdArticulo());
            stmt.setString(2, articulo.getDescripcion());
            rowUpdated = stmt.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rowUpdated;
    }

   /*  public int calcularTotalArticulosAnyo(int any) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int totalArticulos = 0;

        try {
            conn = Conexion.getConnection(); 
            stmt = conn.prepareStatement(SQL_SELECT_BY_YEAR); 
            stmt.setInt(1, any); 

            rs = stmt.executeQuery(); 

            while (rs.next()) {
                totalArticulos += rs.getInt("cantidad");
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return totalArticulos;
    }
*/
}
