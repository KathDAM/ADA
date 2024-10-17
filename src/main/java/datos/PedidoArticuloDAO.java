/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;
import Domain.PedidoArticulo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author catalvman
 */
public class PedidoArticuloDAO {
    private static final String SQL_SELECT_ALL = "SELECT * FROM PedidoArticulo";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM PedidoArticulo WHERE idPedido = ? AND idArticulo = ?";
    private static final String SQL_INSERT = "INSERT INTO PedidoArticulo (idArticulo, numero, cantidad) VALUES (?,?,?)";
    private static final String SQL_UPDATE = "UPDATE PedidoArticulo SET idArticulo = ?, cantidad = ? WHERE idPedido = ?";
    private static final String SQL_DELETE = "DELETE FROM PedidoArticulo WHERE idPedido = ?";
    
    public List<PedidoArticulo> obtenerTodosLosPedidosArticulos() throws SQLException {
        List<PedidoArticulo> pedidosArticulos = new ArrayList<>();
        
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL)) {
            
            while (rs.next()) {
                int idPedido = rs.getInt("idPedido");
                int idArticulo = rs.getInt("idArticulo");
                int cantidad = rs.getInt("cantidad");
                
                PedidoArticulo pedidoArticulo = new PedidoArticulo(idPedido, idArticulo, cantidad);
                pedidosArticulos.add(pedidoArticulo);
            }
        }
        return pedidosArticulos;
    }

    public PedidoArticulo obtenerPedidoArticuloPorId(int idPedido, int idArticulo) throws SQLException {
        PedidoArticulo pedidoArticulo = null;
        
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            stmt.setInt(1, idPedido);
            stmt.setInt(2, idArticulo);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int cantidad = rs.getInt("cantidad");
                    pedidoArticulo = new PedidoArticulo(idPedido, idArticulo, cantidad);
                }
            }
        }
        return pedidoArticulo;
    }

    public void insertar(PedidoArticulo pedidoArticulo) throws SQLException {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            
            stmt.setInt(1, pedidoArticulo.getIdPedido());
            stmt.setInt(2, pedidoArticulo.getIdArticulo());
            stmt.setInt(3, pedidoArticulo.getCantidad());
            
            stmt.executeUpdate();
        }
    }

    public void eliminar(int idPedido, int idArticulo) throws SQLException {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            
            stmt.setInt(1, idPedido);
            stmt.setInt(2, idArticulo);
            
            stmt.executeUpdate();
        }
    }

    public void actualizar(PedidoArticulo pedidoArticulo) throws SQLException {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            
            stmt.setInt(1, pedidoArticulo.getCantidad());
            stmt.setInt(2, pedidoArticulo.getIdPedido());
            stmt.setInt(3, pedidoArticulo.getIdArticulo());
            
            stmt.executeUpdate();
        }
    }

}


