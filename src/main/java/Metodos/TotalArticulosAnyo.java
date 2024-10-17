package Metodos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import datos.Conexion;

public class TotalArticulosAnyo {
    private static final String SQL_SELECT_BY_YEAR = "SELECT PA.cantidad FROM Pedido P JOIN PedidoArticulo PA ON P.idPedido = PA.idPedido WHERE YEAR(P.fecha) = ?";
    
    public int calcularTotalArticulosAnyo(int any) throws SQLException {
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

}
