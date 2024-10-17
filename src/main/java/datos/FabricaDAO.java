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
    private static final String SQL_SELECT_ALL = "SELECT * FROM Fabrica";
    private static final String SQL_SELECT_BY_ID = "SELECT idFabrica, nombre FROM Fabrica WHERE idFabrica = ?";
    private static final String SQL_INSERT = "INSERT INTO Fabrica (idFabrica, telefono) VALUES (?,?)";
    private static final String SQL_UPDATE = "UPDATE Fabrica SET telefono = ? WHERE idFabrica = ?";
    private static final String SQL_DELETE = "DELETE FROM Fabrica WHERE idFabrica = ?";
   
    public List<Fabrica> obtenerTodasLasFabricas() throws SQLException {
        List<Fabrica> fabricas = new ArrayList<>();
        
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL)) {
            
            while (rs.next()) {
                int idFabrica = rs.getInt("idFabrica");
                String telefono = rs.getString("telefono");
                
                Fabrica fabrica = new Fabrica(idFabrica, telefono);
                fabricas.add(fabrica);
            }
        }
        return fabricas;
    }

    public Fabrica obtenerFabricaPorId(int idFabrica) throws SQLException {
        Fabrica fabrica = null;
        
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            stmt.setInt(1, idFabrica);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String telefono = rs.getString("telefono");
                    fabrica = new Fabrica(idFabrica, telefono);
                }
            }
        }
        return fabrica;
    }
    
    public void insertar(Fabrica fabrica) throws SQLException {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            
            stmt.setInt(1, fabrica.getIdFabrica());
            stmt.setString(2, fabrica.getTelefono());
            
            stmt.executeUpdate();
        }
    }

    public void eliminar(int idFabrica) throws SQLException {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            
            stmt.setInt(1, idFabrica);
            
            stmt.executeUpdate();
        }
    }

    public void actualizar(Fabrica fabrica) throws SQLException {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            
            stmt.setString(1, fabrica.getTelefono());
            stmt.setInt(2, fabrica.getIdFabrica());
            
            stmt.executeUpdate();
        }
    }

    public boolean existeFabrica(int idFabrica) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Fabrica WHERE idFabrica = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFabrica);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; 
                }
            }
        }
        return false;
    }
    
}

