package Metodos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Domain.Pedido;
import datos.Conexion;

public class PedidosYTotalDescuentoPorCliente {
    private static final String SQL_SELECT_PEDIDOS = "SELECT * FROM Pedido WHERE idCliente = ?";
    private static final String SQL_TOTAL_DESCUENTO = "SELECT SUM((p.limiteCredito * c.descuento / 100)) AS totalDescuento FROM Pedido p JOIN Cliente c ON p.idCliente = c.idCliente WHERE c.idCliente = ?";

    public List<Pedido> obtenerPedidosYTotalDescuentoPorCliente(int idCliente) throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmtPedidos = conn.prepareStatement(SQL_SELECT_PEDIDOS);
             PreparedStatement stmtDescuento = conn.prepareStatement(SQL_TOTAL_DESCUENTO)) {
             
            stmtPedidos.setInt(1, idCliente);
            try (ResultSet rs = stmtPedidos.executeQuery()) {
                while (rs.next()) {
                    int idPedido = rs.getInt("idPedido");
                    Timestamp fecha = rs.getTimestamp("fecha");
                    int idArticulo = rs.getInt("idArticulo");
                    int idDireccion = rs.getInt("idDireccion");
                    Pedido pedido = new Pedido(idPedido, fecha, idArticulo, idDireccion, idCliente);
                    pedidos.add(pedido);
                }
            }

            stmtDescuento.setInt(1, idCliente);
            try (ResultSet rsDescuento = stmtDescuento.executeQuery()) {
                if (rsDescuento.next()) {
                    double totalDescuento = rsDescuento.getDouble("totalDescuento");
                    System.out.println("Total de descuentos aprovechados: " + totalDescuento + "€");
                }
            }
        }
        return pedidos;
    }
}
