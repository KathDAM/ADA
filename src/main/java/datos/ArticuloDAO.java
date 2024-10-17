/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import Domain.Articulo;
import java.sql.*;
import java.util.*;

/**
 *
 * @author catalvman
 */
public class ArticuloDAO {
    private static final String SQL_SELECT_ALL = "SELECT * FROM Articulo";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM Articulo WHERE idArticulo = ?";
    private static final String SQL_INSERT = "INSERT INTO Articulo (idArticulo, descripcion) VALUES (?,?)";
    private static final String SQL_UPDATE = "UPDATE Articulo SET descripcion = ? WHERE idArticulo = ?";
    private static final String SQL_DELETE = "DELETE FROM Articulo WHERE idArticulo = ?";
    
    public List<Articulo> obtenerTodosLosArticulos() throws SQLException {
        List<Articulo> articulos = new ArrayList<>();
        
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL)) {
            
            while (rs.next()) {
                int idArticulo = rs.getInt("idArticulo");
                String descripcion = rs.getString("descripcion");
                Articulo articulo = new Articulo(idArticulo, descripcion);
                articulos.add(articulo);
            }
        }
        return articulos;
    }

    public Articulo obtenerArticuloPorId(int idArticulo) throws SQLException {
        Articulo articulo = null;

        try (Connection conn = Conexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            stmt.setInt(1, idArticulo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String descripcion = rs.getString("descripcion");
                    articulo = new Articulo(idArticulo, descripcion);
                }
            }
        }
        return articulo;
    }

    public int insertar(Articulo articulo) throws SQLException {
        int registros = 0;
        
        try(Connection conn = Conexion.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)){
           
            stmt.setInt(1, articulo.getIdArticulo());
            stmt.setString(2, articulo.getDescripcion());
            registros = stmt.executeUpdate();
        } 
        return registros;
    }

    public boolean eliminar(int idArticulo) throws SQLException {
        boolean rowDeleted = false;

        try (Connection conn = Conexion.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            stmt.setInt(1, idArticulo);
            rowDeleted = stmt.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean actualizar(Articulo articulo) throws SQLException {
        boolean rowUpdated = false;
    
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
             
            stmt.setString(1, articulo.getDescripcion()); 
            stmt.setInt(2, articulo.getIdArticulo()); 
            rowUpdated = stmt.executeUpdate() > 0;
    
        }
        return rowUpdated;
    }

    public boolean existeArticulo(int idArticulo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Articulo WHERE idArticulo = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idArticulo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; 
                }
            }
        }
        return false; 
    }
    
}
