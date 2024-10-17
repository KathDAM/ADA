package Metodos;

import java.sql.*;

import datos.Conexion;

public class FabricasQueNoTienenPedidos {
    private static final String SQL_DELETE_FABRICAS_SIN_PEDIDOS = "DELETE FROM Fabrica WHERE idFabrica NOT IN (SELECT DISTINCT AF.idFabrica FROM ArticuloFabrica AF JOIN PedidoArticulo PA ON AF.idArticulo = PA.idArticulo)";

    public boolean eliminarFabricasQueNoTienenPedidos() throws SQLException {
        boolean eliminado = false;
     
        int rowsAffected = 0;
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE_FABRICAS_SIN_PEDIDOS)) {
            rowsAffected = stmt.executeUpdate(); 
            if(rowsAffected == 0){
                eliminado = false;
            }else{
                eliminado = true;
            }
            
        } catch (SQLException e) {
            System.out.println("Error al eliminar fábricas: " + e.getMessage());
        }
        return eliminado;
    }
}
