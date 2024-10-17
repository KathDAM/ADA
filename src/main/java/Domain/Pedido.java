/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;
import java.sql.Timestamp;

/**
 *
 * @author catalvman
 */
public class Pedido {
    //ATRIBUTOS
    private int idPedido;
    private Timestamp fecha;
    private int idArticulo;
    private int idDireccion;
    private int idCliente;
    
    //CONSTRUCTOR
    public Pedido(int idPedido, Timestamp fecha, int idArticulo, int idDireccion, int idCliente) {
        this.idPedido = idPedido;
        this.fecha = fecha;
        this.idArticulo = idArticulo;
        this.idDireccion = idDireccion;
        this.idCliente = idCliente;
    }
    
    //GETTER Y SETTER
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public String toString() {
        return "Pedido[" + " ID Pedido: " + idPedido + ", fecha: " + fecha + ", numero: " + idArticulo + ", numDireccion: " + idDireccion + ", idCliente: " + idCliente + " ]";
    }
}
