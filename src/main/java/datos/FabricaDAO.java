/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;
import Domain.Fabrica;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author catalvman
 */
public class FabricaDAO {
    private static final String SQL_SELECT = "SELECT idFabrica, telefono, totalArticulos FROM Fabrica";
    private static final String SQL_INSERT = "INSERT INTO Fabrica (idFabrica, telefono, totalArticulos) VALUES (?,?,?)";
    private static final String SQL_UPDATE = "UPDATE Pedido SET telefono = ?, totalArticulos = ? WHERE idFabrica = ?";
    private static final String SQL_DELETE = "DELETE FROM Fabrica WHERE idFabrica = ?";

    public List<Fabrica> seleccionar() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Fabrica fabrica = null;
        List<Fabrica> fabricas = new ArrayList<>();
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int idFabrica = rs.getInt("idFabrica");
                String telefono = rs.getString("telefono");
                int totalArticulos = rs.getInt("totalArticulos");

                fabrica = new Fabrica(idFabrica, telefono, totalArticulos);
                fabricas.add(fabrica);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return fabricas;
    }

    public int insertar(Fabrica fabrica) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setInt(1, fabrica.getIdFabrica());
            stmt.setString(2, fabrica.getTelefono());
            stmt.setInt(3, fabrica.getTotalArticulos());
            registros = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return registros;
    }

    public boolean eliminar(int idFabrica) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean rowDeleted = false;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, idFabrica);
            rowDeleted = stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rowDeleted;
    }

    public boolean actualizar(Fabrica fabrica) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean rowUpdated = false;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setInt(1, fabrica.getIdFabrica());
            stmt.setString(2, fabrica.getTelefono());
            stmt.setInt(3, fabrica.getTotalArticulos());
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

