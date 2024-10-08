/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import Domain.Cliente;
import static datos.Conexion.close;
import static datos.Conexion.getConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author catalvman
 */
public class ClienteDAO {
    private static final String SQL_SELECT = "SELECT idCliente, saldo, limiteCredito, descuento FROM Cliente";
    private static final String SQL_INSERT = "INSERT INTO Cliente (idCliente, saldo, limiteCredito, descuento) VALUES (?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE Cliente SET saldo = ?, limiteCredito = ?, descuento = ? WHERE idCliente = ?";
    private static final String SQL_DELETE = "DELETE FROM Cliente WHERE idCliente = ?";

    public List<Cliente> seleccionar() throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cliente cliente = null;
        List<Cliente> clientes = new ArrayList<>();
        try{
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while(rs.next()){
                int idCliente = rs.getInt("idCliente");
                double saldo = rs.getDouble("saldo");
                double limiteCredito = rs.getDouble("limiteCredito");
                double descuento = rs.getDouble("descuento");
                cliente = new Cliente(idCliente,saldo,limiteCredito,descuento);
                clientes.add(cliente);
            }
        } catch(SQLException ex){
            ex.printStackTrace(System.out);
        }
        finally{
            Conexion.close(conn);
            Conexion.close(rs);
            Conexion.close(stmt);  
        }
        return clientes;
    }
    
    public int insertar(Cliente cliente){
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
            try{
                conn = Conexion.getConnection();
                stmt = conn.prepareStatement(SQL_INSERT);
                stmt.setInt(1, cliente.getIdCliente());
                stmt.setDouble(2, cliente.getSaldo());
                stmt.setDouble(3, cliente.getLimiteCredito());
                stmt.setDouble(4, cliente.getDescuento());
                registros = stmt.executeUpdate();
                
            }  catch(SQLException ex){
                ex.printStackTrace(System.out);
            }
        finally{
            try{
                close(stmt);
            }catch(SQLException ex){
            }
            try{
                close(conn);
            }catch(SQLException ex){
                ex.printStackTrace(System.out);
            }
            }
            return registros;
    }
    
    public boolean eliminar(int idCliente) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean rowDeleted = false;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, idCliente);
            rowDeleted = stmt.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rowDeleted;
    }

    
    public boolean actualizar(Cliente cliente) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean rowUpdated = false;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setInt(1, cliente.getIdCliente());
            stmt.setDouble(2, cliente.getSaldo());
            stmt.setDouble(3, cliente.getLimiteCredito());
            stmt.setDouble(4, cliente.getDescuento());
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
