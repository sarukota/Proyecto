/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesJava;

public class AlertaVehiculo {
    
    private String matricula;
    private String titulo;
    
    public AlertaVehiculo(){
        
    }

    public AlertaVehiculo(String matricula, String titulo) {
        this.matricula = matricula;
        this.titulo = titulo;
    }
    

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
 
    public String toSQL(){
        ConexionBBDD conexion = new ConexionBBDD();
        String insertVehiculo = "INSERT INTO AlertasVehiculos (matricula, titulo) VALUES ('"+getMatricula()+
                "','"+getTitulo()+"');";
        return insertVehiculo;
    }
    
}
