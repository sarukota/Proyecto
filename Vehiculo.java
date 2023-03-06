/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesJava;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import javax.swing.JOptionPane;

public class Vehiculo {
    private String matricula;
    private String marca;
    private String modelo;
    private int numOcupantes;
    private Date checkIn;
    private Date checkOut;
    private Parcela parcela;
    private Set <Cliente> clientes;
	
    public Vehiculo() {
		
    }

    public Vehiculo(String matricula, String marca, String modelo, int numOcupantes, Date checkIn, Date checkOut, Parcela parcela, Set<Cliente> clientes) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.numOcupantes = numOcupantes;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.parcela = parcela;
        this.clientes = clientes;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getNumOcupantes() {
        return numOcupantes;
    }

    public void setNumOcupantes(int numOcupantes) {
        this.numOcupantes = numOcupantes;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public Parcela getParcela() {
        return parcela;
    }

    public void setParcela(Parcela parcela) {
        this.parcela = parcela;
    }

    public Set<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(Set<Cliente> clientes) {
        this.clientes = clientes;
    }
    
    private void calculoFactura(Area miArea){
        DateFormat fechaCorta = new SimpleDateFormat ("dd-MM-aaaa");
        Date fechaEmision = new Date(System.currentTimeMillis());
        String factura = fechaCorta.format(fechaEmision) +"\n"
                + "Entrada: "+fechaCorta.format(getCheckIn())+"      Salida: "+fechaCorta.format(getCheckOut())+"\n"
                + "Vehículo: "+getMatricula()+"\n";
               //+ "Servicios: "+Arrays.toString(miArea.getServicios())+"";
        JOptionPane.showMessageDialog(null, factura);
    } 
    
    public String toSQL(){
        ConexionBBDD conexion = new ConexionBBDD();
        String insertVehiculo = "INSERT INTO vehiculos (matricula, marca, modelo, num_ocupantes, check_in, check_out)"
                + "VALUES ('"+getMatricula()+"','"+getMarca()+"','"+getModelo()+"',"+getNumOcupantes()+",'"
                + conexion.fechaSQL(getCheckIn())+"','"+conexion.fechaSQL(getCheckOut())+"');";
        return insertVehiculo;
    }

    @Override
    public String toString() {
        return "Vehiculo{" + "matricula=" + matricula + ", marca=" + marca + ", modelo=" + modelo + ", numOcupantes=" + numOcupantes + ", checkIn=" + checkIn + ", checkOut=" + checkOut + ", parcela=" + parcela + ", clientes=" + clientes + '}';
    }
    
    
    
}
