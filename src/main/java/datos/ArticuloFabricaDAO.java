/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Domain.ArticuloFabrica;
/**
 *
 * @author catalvman
 */
public class ArticuloFabricaDAO {
     private static final String SQL_SELECT = "SELECT idArticulo, idFabrica, existencias FROM ArticuloFabrica";
    private static final String SQL_INSERT = "INSERT INTO ArticuloFabrica (idArticulo, idFabrica, existencias) VALUES (?,?,?)";
    private static final String SQL_UPDATE = "UPDATE ArticuloFabrica SET idFabrica = ?, existencias = ? WHERE idArticulo = ?";
    private static final String SQL_DELETE = "DELETE FROM ArticuloFabrica WHERE idArticulo = ?";

    public List<ArticuloFabrica> seleccionar() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArticuloFabrica articuloFab = null;
        List<ArticuloFabrica> articulosFab = new ArrayList<>();
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int idArticulo = rs.getInt("idArticulo");
                int idFabrica = rs.getInt("idFabrica");
                int existencias = rs.getInt("existencias");
  
                articuloFab = new ArticuloFabrica(idArticulo, idFabrica, existencias);
                articulosFab.add(articuloFab);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return articulosFab;
    }

    public int insertar(ArticuloFabrica articuloFab) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setInt(1, articuloFab.getIdArticulo());
            stmt.setInt(2, articuloFab.getIdFabrica());
            stmt.setInt(3, articuloFab.getExistencias());
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

    public boolean actualizar(ArticuloFabrica articuloFab) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean rowUpdated = false;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setInt(1, articuloFab.getIdArticulo());
            stmt.setInt(2, articuloFab.getIdFabrica());
            stmt.setInt(3, articuloFab.getExistencias());
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
