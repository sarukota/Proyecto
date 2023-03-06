/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesJava;

import java.sql.SQLException;
import pantallasSwing.PantallaBienvenida;
import pantallasSwing.PantallaClientes;
import pantallasSwing.PantallaPrincipal;
import pantallasSwing.PantallaVehiculos;

public class Main {
    
   /* public static PantallaVehiculos pv =  new PantallaVehiculos(); 
    public static PantallaClientes pc = new PantallaClientes();
    PantallaPrincipal pp = new PantallaPrincipal();*/
    
    public static void main(String args[]) {
               
        //Conecta con BD, crea la nueva BD y todas las tablas correspondientes
        
        Area miArea = new Area(); 
        
        ConexionBBDD connect = new ConexionBBDD(); //Se activa la conexion a la BD y se crean las tablas
        try {
            connect.crearTablas();
            System.out.println("Se ha conectado correctamente a la BD");
	} catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("No se ha podido conectar al servidor SQL");		
	}
        
        //PantallaBienvenida bienvenida = new PantallaBienvenida(); 
        //bienvenida.setVisible(true); //Muestra la pantalla de bienvenida y comienza el flujo de la aplicacion
        
        pv.setVisible(true);
        pc.setVisible(false);
        
    }
    
}
