/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesJava;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JTextField;

/**
 *
 * @author sarac
 */
public class ConexionBBDD { //Ver en Eclipse M06 Acceso a datos VT05 el ejemplo
        
        private final String BBDD;
	private final String USER;
	private final String PASSWORD;
	
	//Constructor por defecto
	
	public ConexionBBDD() {
		BBDD = "jdbc:mysql://localhost:3308"; //Puerto por defecto en BD
		USER = "root"; //Usuario de PHPMyADMIN
		PASSWORD = ""; //Contraseña de PHPMyADMIN
	}
	
	public ConexionBBDD(String BBDD, String USER, String PASSWORD) {
		this.BBDD = BBDD;
		this.USER = USER;
		this.PASSWORD = PASSWORD;
	}
	
	public void crearTablas()throws SQLException{
		Connection connect = DriverManager.getConnection(BBDD,USER,PASSWORD);
		Statement sentencia = connect.createStatement();
               
		//Creamos la nueva BD si no existe
		String crearDb ="CREATE DATABASE IF NOT EXISTS furgoGestion;";
		sentencia.executeUpdate(crearDb);
		
		//Usamos la nueva BD
		String use = "USE furgoGestion;";
		sentencia.executeUpdate(use);
		
		//Creamos las tablas vehiculos, clientes, parcelas y alertas
                String crearTablaVehiculos="CREATE TABLE IF NOT EXISTS Vehiculos ("
                        + "matricula VARCHAR (10) PRIMARY KEY,"
                        + "marca VARCHAR (15) NOT NULL,"
                        + "modelo VARCHAR (15),"
                        + "num_ocupantes INT NOT NULL,"
                        + "check_in DATE NOT NULL,"
                        + "check_out DATE);";
                sentencia.executeUpdate(crearTablaVehiculos);
                System.out.println("tabla vehiculos creada");
                
                String crearTablaClientes="CREATE TABLE IF NOT EXISTS Clientes ("
                        + "dni VARCHAR (10) PRIMARY KEY,"
                        + "nombre VARCHAR (15) NOT NULL,"
                        + "apellido1 VARCHAR (15) NOT NULL,"
                        + "apellido2 VARCHAR (15),"
                        + "fecha_nac DATE,"
                        + "nacionalidad VARCHAR (20),"
                        + "provincia VARCHAR (20),"
                        + "telefono INT NOT NULL,"
                        + "mail VARCHAR (30));";
                sentencia.executeUpdate(crearTablaClientes);
                System.out.println("tabla clientes creada");
  
                String crearTablaParcelas="CREATE TABLE IF NOT EXISTS Parcelas ("
                        + "num_parcela INT PRIMARY KEY,"
                        + "disponibilidad BOOLEAN NOT NULL,"
                        + "luz BOOLEAN NOT NULL);";
                sentencia.executeUpdate(crearTablaParcelas);
                System.out.println("tabla parcela creada");
              
                String crearTablaAlertas="CREATE TABLE Alertas ("
                        + "num_alerta INT AUTO_INCREMENT PRIMARY KEY,"
                        + "descripcion VARCHAR (50) NOT NULL,"
                        + "dia_alerta DATE NOT NULL);";
                sentencia.executeUpdate(crearTablaAlertas);
                System.out.println("tabla ALERTAS creada");
		
		 /*//Eliminamos el esquema si existe
		String ifExists = "DROP SCHEMA  furgoGestion;";
		sentencia.executeUpdate(ifExists);*/
       
                /*
		//Hacemos consultas en la BD
		ResultSet result=sentencia.executeQuery("SELECT * FROM Alumnos;");
		List<Alumno> alumnoList = new ArrayList<>();
		while (result.next()) {
			Alumno alumno = new Alumno ((long) result.getInt("id_alumno"),result.getString(2));
			alumnoList.add(alumno); //Guardamos los alumnos en una lista
		}
		
		System.out.println(alumnoList); //Imprimimos la lista por consola
		result.close();*/
		sentencia.close();
		connect.close();
		
	
        }
        
        public void insertInVehiculo()throws SQLException{
            Connection connect = DriverManager.getConnection(BBDD,USER,PASSWORD);
            Statement sentencia = connect.createStatement();
            
            /*//Insertamos valores en la tabla, para escapar las comillas de los valores de los campos en lenguaje SQL tenemos que poner una contrabarra
		String insert="INSERT INTO Alumnos(nombre) VALUES(\"Álvaro\"),(\"Pedro\"),(\"María\"),(\"Paula\");";
		sentencia.executeUpdate(insert); */
            
            sentencia.close();
            connect.close();
            
        }
}
