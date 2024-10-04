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
    private int numero;
    private int numDireccion;
    private int idCliente;
    
    //CONSTRUCTOR
    public Pedido(int idPedido, Timestamp fecha, int numero, int numDireccion, int idCliente) {
        this.idPedido = idPedido;
        this.fecha = fecha;
        this.numero = numero;
        this.numDireccion = numDireccion;
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

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getNumDireccion() {
        return numDireccion;
    }

    public void setNumDireccion(int numDireccion) {
        this.numDireccion = numDireccion;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public String toString() {
        return "Pedido{" + "idPedido=" + idPedido + ", fecha=" + fecha + ", numero=" + numero + ", numDireccion=" + numDireccion + ", idCliente=" + idCliente + '}';
    }
}
