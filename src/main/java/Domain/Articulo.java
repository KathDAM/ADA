/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

/**
 *
 * @author catalvman
 */
public class Articulo {
    //ATRIBUTOS
    private int idArticulo;
    private String descripcion;

    //CONSTRUCTOR
    public Articulo(int idArticulo, String descripcion) {
        this.idArticulo = idArticulo;
        this.descripcion = descripcion;
    }

    //GETTER Y SETTER
    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Articulo{" + "idArticulo=" + idArticulo + ", descripcion=" + descripcion + '}';
    }
}
