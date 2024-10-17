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
    private static final String SQL_SELECT_ALL = "SELECT * FROM Pedido";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM Pedido WHERE idPedido = ?";
    private static final String SQL_INSERT = "INSERT INTO Pedido (idPedido, fecha, idArticulo, idDireccion, idCliente) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE Pedido SET fecha = ?, idArticulo = ?, idDireccion = ?, idCliente = ? WHERE idPedido = ?";
    private static final String SQL_DELETE = "DELETE FROM Pedido WHERE idPedido = ?";
    private static final String SQL_TOTAL_ARTICULOS_POR_ANYO = "SELECT SUM(pa.cantidad) AS totalArticulos FROM PedidoArticulo pa JOIN Pedido p ON pa.idPedido = p.idPedido WHERE YEAR(p.fecha) = ?";
   
    public List<Pedido> obtenerTodosLosPedidos() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL)) {
            
            while (rs.next()) {
                int idPedido = rs.getInt("idPedido");
                Timestamp fecha = rs.getTimestamp("fecha");
                int idArticulo = rs.getInt("idArticulo");
                int idDireccion = rs.getInt("idDireccion");
                int idCliente = rs.getInt("idCliente");
                
                Pedido pedido = new Pedido(idPedido, fecha, idArticulo, idDireccion, idCliente);
                pedidos.add(pedido);
            }
        }
        return pedidos;
    }

    public Pedido obtenerPedidoPorId(int idPedido) throws SQLException {
        Pedido pedido = null;
        
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            stmt.setInt(1, idPedido);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Timestamp fecha = rs.getTimestamp("fecha");
                    int idArticulo = rs.getInt("idArticulo");
                    int idDireccion = rs.getInt("idDireccion");
                    int idCliente = rs.getInt("idCliente");
                    
                    pedido = new Pedido(idPedido, fecha, idArticulo, idDireccion, idCliente);
                }
            }
        }
        return pedido;
    }

    public void insertar(Pedido pedido) throws SQLException {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            
                stmt.setTimestamp(1, pedido.getFecha());
                stmt.setInt(2, pedido.getIdArticulo()); 
                stmt.setInt(3, pedido.getIdDireccion());
                stmt.setInt(4, pedido.getIdCliente());
            
            stmt.executeUpdate();
        }
    }

    public void eliminar(int idPedido) throws SQLException {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            
            stmt.setInt(1, idPedido);
            
            stmt.executeUpdate();
        }
    }

    public void actualizar(Pedido pedido) throws SQLException {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            
                stmt.setTimestamp(1, pedido.getFecha());
                stmt.setInt(2, pedido.getIdArticulo()); 
                stmt.setInt(3, pedido.getIdDireccion());
                stmt.setInt(4, pedido.getIdCliente());
            
            stmt.executeUpdate();
        }
    }

    public int calcularTotalArticulosPorAnyo(int anyo) throws SQLException {
        int totalArticulos = 0;
        
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_TOTAL_ARTICULOS_POR_ANYO)) {
            stmt.setInt(1, anyo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    totalArticulos = rs.getInt("totalArticulos");
                }
            }
        }
        
        return totalArticulos;
    }

    public boolean existePedido(int idPedido) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Pedido WHERE idPedido = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; 
                }
            }
        }
        return false; 
    }
    
     
}    


