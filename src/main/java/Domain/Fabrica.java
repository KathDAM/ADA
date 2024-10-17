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

    //CONSTRUCTOR
    public Fabrica(int idFabrica, String telefono) {
        this.idFabrica = idFabrica;
        this.telefono = telefono;
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

    @Override
    public String toString() {
        return "Fabrica[" + " ID Fabrica: " + idFabrica + ", telefono: " + telefono +" ]";
    }
}
