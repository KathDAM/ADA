/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

/**
 *
 * @author catalvman
 */
public class Fabrica {
    //ATRIBUTOS
    private int idFabrica;
    private String telefono;
    private int totalArticulos;
    
    //CONSTRUCTOR
    public Fabrica(int idFabrica, String telefono, int totalArticulos) {
        this.idFabrica = idFabrica;
        this.telefono = telefono;
        this.totalArticulos = totalArticulos;
    }
    
    //GETTER Y SETTER
    public int getIdFabrica() {
        return idFabrica;
    }

    public void setIdFabrica(int idFabrica) {
        this.idFabrica = idFabrica;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getTotalArticulos() {
        return totalArticulos;
    }

    public void setTotalArticulos(int totalArticulos) {
        this.totalArticulos = totalArticulos;
    }

    @Override
    public String toString() {
        return "Fabrica{" + "idFabrica=" + idFabrica + ", telefono=" + telefono + ", totalArticulos=" + totalArticulos + '}';
    }
}
