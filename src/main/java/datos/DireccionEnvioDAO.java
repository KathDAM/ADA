/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import Domain.DireccionEnvio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author catalvman
 */
public class DireccionEnvioDAO {
    private static final String SQL_SELECT_ALL = "SELECT idDireccion, numero, calle, comuna, ciudad, idCliente  FROM DireccionEnvio";
    private static final String SQL_SELECT_BY_ID = "SELECT idDireccion, numero, calle, comuna, ciudad, idCliente FROM DireccionEnvio WHERE numDireccion = ?";
    private static final String SQL_INSERT = "INSERT INTO DireccionEnvio (numero, calle, comuna, ciudad, idCliente) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE DireccionEnvio SET numero = ?, calle = ?, comuna = ?, ciudad = ?, idCliente = ? WHERE idDireccion = ?";
    private static final String SQL_DELETE = "DELETE FROM DireccionEnvio WHERE idCliente = ?";
   
           
    public List<DireccionEnvio> obtenerTodasLasDirecciones() throws SQLException {
        List<DireccionEnvio> direccionesEnvio = new ArrayList<>();
        
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL)) {
            
            while (rs.next()) {
                int idDireccion = rs.getInt("idDireccion");
                int numero = rs.getInt("numero");
                String calle = rs.getString("calle");
                String comuna = rs.getString("comuna");
                String ciudad = rs.getString("ciudad");
                int idCliente = rs.getInt("idCliente");
                
                DireccionEnvio direccionEnvio = new DireccionEnvio(idDireccion, numero, calle, comuna, ciudad, idCliente);
                direccionesEnvio.add(direccionEnvio);
            }
        }
        return direccionesEnvio;
    }
    
    public DireccionEnvio obtenerDireccionPorId(int idDireccion) throws SQLException {
        DireccionEnvio direccionEnvio = null;
        
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            stmt.setInt(1, idDireccion);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int numero = rs.getInt("numero");
                    String calle = rs.getString("calle");
                    String comuna = rs.getString("comuna");
                    String ciudad = rs.getString("ciudad");
                    int idCliente = rs.getInt("idCliente");
                    
                    direccionEnvio = new DireccionEnvio(idDireccion, numero, calle, comuna, ciudad, idCliente);
                }
            }
        }
        return direccionEnvio;
    }
    
    public void insertar(DireccionEnvio direccionEnvio) throws SQLException {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            
            stmt.setInt(1, direccionEnvio.getNumero());
            stmt.setString(2, direccionEnvio.getCalle());
            stmt.setString(3, direccionEnvio.getComuna());
            stmt.setString(4, direccionEnvio.getCiudad());
            stmt.setInt(5, direccionEnvio.getIdCliente());
            
            stmt.executeUpdate();
        }
    }
    
    public void eliminar(int idDireccion) throws SQLException {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            
            stmt.setInt(1, idDireccion);
            stmt.executeUpdate();
        }
    }
    
    public void actualizar(DireccionEnvio direccion) throws SQLException {
        ClienteDAO clienteDAO = new ClienteDAO();
        if (!clienteDAO.existeCliente(direccion.getIdCliente())) {
            throw new SQLException("El ID del cliente no existe.");
        }
        
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            
            stmt.setInt(1, direccion.getNumero());
            stmt.setString(2, direccion.getCalle());
            stmt.setString(3, direccion.getComuna());
            stmt.setString(4, direccion.getCiudad());
            stmt.setInt(5, direccion.getIdCliente());
            stmt.setInt(6, direccion.getIdDireccion());
            
            stmt.executeUpdate();
        }
    }

    public boolean existeDireccion(int idDireccion) throws SQLException {
        String sql = "SELECT COUNT(*) FROM DireccionEnvio WHERE idDireccion = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idDireccion);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; 
                }
            }
        }
        return false; 
    }
    
}
