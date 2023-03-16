/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesJava;

import java.util.Date;

public class Alerta {
    
    private String titulo;
    private String descripción;
    private Date dia;

    public Alerta(){
        
    }
    
    public Alerta(String titulo, String descripción, Date dia) {
        this.titulo = titulo;
        this.descripción = descripción;
        this.dia = dia;
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

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }
    
    public String toSQL(){
        ConexionBBDD conexion = new ConexionBBDD();
        String insertAlerta ="INSERT INTO alertas (titulo,descripcion,dia_alerta) VALUES("
                + "'"+getTitulo()+"','"+getDescripción()+"','"+conexion.fechaSQL(getDia())+"');";
        return insertAlerta;
    }

    @Override
    public String toString() {
        return "Alerta{" + "titulo=" + titulo + ", descripci\u00f3n=" + descripción + ", dia=" + dia + '}';
    }
    
    

  
    
    
    
    
           
    
}
