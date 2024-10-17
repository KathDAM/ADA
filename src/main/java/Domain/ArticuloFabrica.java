/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

/**
 *
 * @author catalvman
 */
public class ArticuloFabrica {
    //ATRIBUTOS
    private int idArticulo;
    private int idFabrica;
    private int existencias;
    private double precio;
    
    //CONSTRUCTOR
    public ArticuloFabrica(int idArticulo, int idFabrica, int existencias, double precio) {
        this.idArticulo = idArticulo;
        this.idFabrica = idFabrica;
        this.existencias = existencias;
        this.precio = precio;
    }
    
    //GETTER Y SETTER
    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public int getIdFabrica() {
        return idFabrica;
    }

    public void setIdFabrica(int idFabrica) {
        this.idFabrica = idFabrica;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "ArticuloFabrica [" + "ID Articulo: " + idArticulo + ", idFabrica: " + idFabrica + ", existencias: " + existencias + ", precio: " + precio + ']';
    }
}
