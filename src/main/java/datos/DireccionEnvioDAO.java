/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import Domain.DireccionEnvio;
import static datos.Conexion.close;
import static datos.Conexion.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author catalvman
 */
public class DireccionEnvioDAO {
    private static final String SQL_SELECT = "SELECT numDireccion, numero, calle, comuna, ciudad, idCliente  FROM DireccionEnvio";
    private static final String SQL_INSERT = "INSERT INTO DireccionEnvio (numDireccion, numero, calle, comuna, ciudad,idCliente ) VALUES (?,?,?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE DireccionEnvio SET numDireccion = ?, numero = ?, calle = ? , comuna = ?, ciudad = ?, idCliente = ? WHERE idCliente = ?";
    private static final String SQL_DELETE = "DELETE FROM DireccionEnvio WHERE idCliente = ?";
    private static final String SQL_SELECT_BY_ID = "SELECT numDireccion, numero, calle, comuna, ciudad, idCliente FROM DireccionEnvio WHERE numDireccion = ?";
           
    public List<DireccionEnvio> seleccionar() throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        DireccionEnvio direccionEnvio = null;
        List<DireccionEnvio> direccionEnvios = new ArrayList<>();
        try{
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while(rs.next()){
                int numDireccion = rs.getInt("numDireccion");
                int numero = rs.getInt("numero");
                String calle = rs.getString("calle");
                String comuna = rs.getString("comuna");
                String ciudad = rs.getString("ciudad");
                int idCliente = rs.getInt("idCliente");
                direccionEnvio = new DireccionEnvio(numDireccion,numero,calle,comuna,ciudad,idCliente);
                direccionEnvios.add(direccionEnvio);
            }
        } catch(SQLException ex){
            ex.printStackTrace(System.out);
        }
        finally{
            Conexion.close(conn);
            Conexion.close(rs);
            Conexion.close(stmt);  
        }
        return direccionEnvios;
    }
    
    public DireccionEnvio obtenerDireccionPorId(int numDireccion) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        DireccionEnvio direccionEnvio = null;
    
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);
            stmt.setInt(1, numDireccion); 
            rs = stmt.executeQuery();
    
            if (rs.next()) {
                int numero = rs.getInt("numero");
                String calle = rs.getString("calle");
                String comuna = rs.getString("comuna");
                String ciudad = rs.getString("ciudad");
                int idCliente = rs.getInt("idCliente");
    
                direccionEnvio = new DireccionEnvio(numDireccion, numero, calle, comuna, ciudad, idCliente);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return direccionEnvio; 
    }
    
    public int insertar(DireccionEnvio direccionEnvio){
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
            try{
                conn = Conexion.getConnection();
                stmt = conn.prepareStatement(SQL_INSERT);
                stmt.setInt(1, direccionEnvio.getNumDireccion());
                stmt.setInt(2, direccionEnvio.getNumero());
                stmt.setString(3, direccionEnvio.getCalle());
                stmt.setString(4, direccionEnvio.getComuna());
                stmt.setString(5, direccionEnvio.getCiudad());
                stmt.setInt(6, direccionEnvio.getIdCliente());
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
            stmt.setInt(6, idCliente);
            rowDeleted = stmt.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rowDeleted;
    }

    
    public boolean actualizar(DireccionEnvio direccionEnvio) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean rowUpdated = false;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setInt(1, direccionEnvio.getNumDireccion());
            stmt.setInt(2, direccionEnvio.getNumero());
            stmt.setString(3, direccionEnvio.getCalle());
            stmt.setString(4, direccionEnvio.getComuna());
            stmt.setString(5, direccionEnvio.getCiudad());
            stmt.setInt(6, direccionEnvio.getIdCliente());
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

