/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import Domain.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author catalvman
 */
public class ClienteDAO {
    private static final String SQL_SELECT_ALL = "SELECT * FROM Cliente";
    private static final String SQL_SELECT_BY_ID = "SELECT idCliente, descuento FROM Cliente WHERE idCliente = ?";
    private static final String SQL_INSERT = "INSERT INTO Cliente (idCliente, saldo, limiteCredito, descuento) VALUES (?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE Cliente SET saldo = ?, limiteCredito = ?, descuento = ? WHERE idCliente = ?";
    private static final String SQL_DELETE = "DELETE FROM Cliente WHERE idCliente = ?";
   
    public List<Cliente> obtenerTodosLosClientes() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL)) {
            
            while (rs.next()) {
                int idCliente = rs.getInt("idCliente");
                double saldo = rs.getDouble("saldo");
                double limiteCredito = rs.getDouble("limiteCredito");
                double descuento = rs.getDouble("descuento");
                
                Cliente cliente = new Cliente(idCliente, saldo, limiteCredito, descuento);
                clientes.add(cliente);
            }
        }
        return clientes;
    }
    
    public Cliente obtenerClientePorId(int idCliente) throws SQLException {
        Cliente cliente = null;
        
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            stmt.setInt(1, idCliente);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    double saldo = rs.getDouble("saldo");
                    double limiteCredito = rs.getDouble("limiteCredito");
                    double descuento = rs.getDouble("descuento");
                    
                    cliente = new Cliente(idCliente, saldo, limiteCredito, descuento);
                }
            }
        }
        return cliente;
    }

    public void insertar(Cliente cliente) throws SQLException {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            
            stmt.setInt(1, cliente.getIdCliente());
            stmt.setDouble(2, cliente.getSaldo());
            stmt.setDouble(3, cliente.getLimiteCredito());
            stmt.setDouble(4, cliente.getDescuento());
            
            stmt.executeUpdate();
        }
    }
    
    public void eliminar(int idCliente) throws SQLException {
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            
            stmt.setInt(1, idCliente);
            
            stmt.executeUpdate();
        }
    }

    
    public void actualizar(Cliente cliente) throws SQLException {
        try (Connection conn = Conexion.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
       
            stmt.setDouble(1, cliente.getSaldo());
            stmt.setDouble(2, cliente.getLimiteCredito());
            stmt.setDouble(3, cliente.getDescuento());
            stmt.setInt(4, cliente.getIdCliente());
            
            stmt.executeUpdate();
        }
    }

    public boolean existeCliente(int idCliente) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Cliente WHERE idCliente = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; 
                }
            }
        }
        return false; 
    }
    
}
