/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

/**
 *
 * @author catalvman
 */
public class Cliente {
    //ATRIBUTOS
    private int idCliente;
    private double saldo;
    private double limiteCredito;
    private double descuento;
    
    //CONSTRUCTOR
    public Cliente(int idCliente, double saldo, double limiteCredito, double descuento) {
        this.idCliente = idCliente;
        this.saldo = saldo;
        this.limiteCredito = limiteCredito;
        this.descuento = descuento;
    }

    public Cliente(int idCliente, double descuento) {
        this.idCliente = idCliente;
        this.descuento = descuento;
    }


    //GETTER Y SETTER
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    @Override
    public String toString() {
        return "Cliente{" + "idCliente=" + idCliente + ", saldo=" + saldo + ", limiteCredito=" + limiteCredito + ", descuento=" + descuento + '}';
    }
}
