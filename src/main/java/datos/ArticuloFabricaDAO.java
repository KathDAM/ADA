/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Domain.ArticuloFabrica;
/**
 *
 * @author catalvman
 */
public class ArticuloFabricaDAO {
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM ArticuloFabrica WHERE idArticulo = ? AND idFabrica = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM ArticuloFabrica";
    private static final String SQL_INSERT = "INSERT INTO ArticuloFabrica (idArticulo, idFabrica, existencias) VALUES (?,?,?)";
    private static final String SQL_UPDATE = "UPDATE ArticuloFabrica SET idFabrica = ?, existencias = ? WHERE idArticulo = ?";
    private static final String SQL_DELETE = "DELETE FROM ArticuloFabrica WHERE idArticulo = ?";

    public List<ArticuloFabrica> obtenerTodosLosArticulosFabrica() throws SQLException {
        List<ArticuloFabrica> articulosFabrica = new ArrayList<>();
        
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL)) {
            
            while (rs.next()) {
                int idArticulo = rs.getInt("idArticulo");
                int idFabrica = rs.getInt("idFabrica");
                int existencias = rs.getInt("existencias");
                double precio = rs.getDouble("precio");
                ArticuloFabrica articuloFabrica = new ArticuloFabrica(idArticulo, idFabrica, existencias, precio);
                articulosFabrica.add(articuloFabrica);
            }
        }
        return articulosFabrica;
    }


    public ArticuloFabrica obtenerArticuloFabricaPorId(int idArticulo, int idFabrica) throws SQLException {
        ArticuloFabrica articuloFabrica = null;
        
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            stmt.setInt(1, idArticulo);
            stmt.setInt(2, idFabrica);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int existencias = rs.getInt("existencias");
                    double precio = rs.getDouble("precio");
                    articuloFabrica = new ArticuloFabrica(idArticulo, idFabrica, existencias, precio);
                }
            }
        }
        return articuloFabrica;
    }

    public void insertar(int idArticulo, int idFabrica, int existencias, double precio) throws SQLException {
        try (Connection conn = Conexion.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            
            stmt.setInt(1, idArticulo);
            stmt.setInt(2, idFabrica);
            stmt.setInt(3, existencias);
            stmt.setDouble(4, precio);
            
            stmt.executeUpdate();
        } 
    }

    public void eliminar(int idArticulo, int idFabrica) throws SQLException {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            
            stmt.setInt(1, idArticulo);
            stmt.setInt(2, idFabrica);
            
            stmt.executeUpdate();
        }
    }

    public void actualizar(int idArticulo, int idFabrica, int existencias, double precio) throws SQLException {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            
            stmt.setInt(1, existencias);
            stmt.setDouble(2, precio);
            stmt.setInt(3, idArticulo);
            stmt.setInt(4, idFabrica);
            
            stmt.executeUpdate();
        }
    }
}

