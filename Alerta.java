/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesJava;

import java.util.Date;

/**
 *
 * @author sarac
 */
public class Alerta {
    
    private int numAlerta;
    private String descripción;
    private Date dia;

    public Alerta(){
        
    }
    
    public Alerta(int numAlerta, String descripción, Date dia) {
        this.numAlerta = numAlerta;
        this.descripción = descripción;
        this.dia = dia;
    }

    public int getNumAlerta() {
        return numAlerta;
    }

    public void setNumAlerta(int numAlerta) {
        this.numAlerta = numAlerta;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    @Override
    public String toString() {
        return "Alerta{" + "numAlerta=" + numAlerta + ", descripci\u00f3n=" + descripción + ", dia=" + dia + '}';
    }
    
    
    
    
           
    
}
