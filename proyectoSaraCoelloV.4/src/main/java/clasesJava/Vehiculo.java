/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesJava;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class Vehiculo {
    
    private String matricula;
    private String marca;
    private String modelo;
    private int numOcupantes;
    private int numParcela;
    private Date checkIn;
    private Date checkOut;
 	
    public Vehiculo() {
		
    }

    public Vehiculo(String matricula, String marca, String modelo, int numOcupantes, int numParcela, Date checkIn, Date checkOut) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.numOcupantes = numOcupantes;
        this.numParcela = numParcela;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
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

    public int getNumParcela() {
        return numParcela;
    }

    public void setNumParcela(int numParcela) {
        this.numParcela = numParcela;
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
    
    public String toSQL(){
        ConexionBBDD conexion = new ConexionBBDD();
        String insertVehiculo = "INSERT INTO vehiculos (matricula, marca, modelo, num_ocupantes, num_parcela, check_in, check_out)"
                + "VALUES ('"+getMatricula()+"','"+getMarca()+"','"+getModelo()+"',"+getNumOcupantes()+","
                + getNumParcela()+",'"+conexion.fechaSQL(getCheckIn())+"','"+conexion.fechaSQL(getCheckOut())+"');";
        return insertVehiculo;
    }

    @Override
    public String toString() {
        return "Vehiculo{" + "matricula=" + matricula + ", marca=" + marca + ", modelo=" + modelo + ", numOcupantes=" + numOcupantes + ", numParcela=" + numParcela + ", checkIn=" + checkIn + ", checkOut=" + checkOut + "}";
    }
   
}
