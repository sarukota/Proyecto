/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesJava;

public class Servicio {
    
    private String nombre;
    private int precio;
    
    public Servicio(){
        
    }

    public Servicio(String nombre, int precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
    
    public String toSQL(){
        String insert = "INSERT INTO servicios (nombre,precio) VALUES (\""+getNombre()+"\","+getPrecio()+");";
        return insert;
    }

    @Override
    public String toString() {
        return "Servicio{" + "nombre=" + nombre + ", precio=" + precio + '}';
    }
          
}
