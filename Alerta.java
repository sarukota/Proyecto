/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesJava;

import java.util.Date;

public class Alerta {
    
    private String titulo;
    private String descripción;
    private Date diaAviso;

    public Alerta(){
        
    }
    
    public Alerta(String titulo, String descripción, Date diaAviso) {
        this.titulo = titulo;
        this.descripción = descripción;
        this.diaAviso = diaAviso;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    public Date getDiaAviso() {
        return diaAviso;
    }

    public void setDiaAviso(Date dia) {
        this.diaAviso = dia;
    }
    
    public String toSQL(){
        ConexionBBDD conexion = new ConexionBBDD();
        String insertAlerta ="INSERT INTO alertas (titulo,descripcion,dia_alerta) VALUES("
                + "'"+getTitulo()+"','"+getDescripción()+"','"+conexion.fechaSQL(getDiaAviso())+"');";
        return insertAlerta;
    }

    @Override
    public String toString() {
        return "Alerta{" + "titulo=" + titulo + ", descripci\u00f3n=" + descripción + ", diaAviso=" + diaAviso + '}';
    }
    
    

  
    
    
    
    
           
    
}
