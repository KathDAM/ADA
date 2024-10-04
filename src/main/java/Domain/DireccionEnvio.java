/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

/**
 *
 * @author catalvman
 */
public class DireccionEnvio {
    // ATRIBUTOS
    private int numDireccion;
    private int numero;
    private String calle;
    private String comuna;
    private String ciudad;
    private int idCliente;

    // CONSTRUCTOR
    public DireccionEnvio(int numDireccion, int numero, String calle, String comuna, String ciudad, int idCliente) {
        this.numDireccion = numDireccion;
        this.numero = numero;
        this.calle = calle;
        this.comuna = comuna;
        this.ciudad = ciudad;
        this.idCliente = idCliente;
    }

    // GETTER Y SETTER
    public int getNumDireccion() {
        return numDireccion;
    }

    public void setNumDireccion(int numDireccion) {
        this.numDireccion = numDireccion;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}
