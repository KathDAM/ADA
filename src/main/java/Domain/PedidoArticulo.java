/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

/**
 *
 * @author catalvman
 */
public class PedidoArticulo {
    //ATRIBUTOS
    private int idPedido;
    private int idArticulo;
    private int cantidad;
    
    //CONSTRUCTOR
    public PedidoArticulo(int idPedido, int idArticulo, int cantidad) {
        this.idPedido = idPedido;
        this.idArticulo = idArticulo;
        this.cantidad = cantidad;
    }
    
    //GETTER Y SETTER
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "PedidoArticulo{" + "idPedido=" + idPedido + ", idArticulo=" + idArticulo + ", cantidad=" + cantidad + '}';
    }  
}
