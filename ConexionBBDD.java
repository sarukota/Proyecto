/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesJava;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;



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

    public String getBBDD() {
        return BBDD;
    }

    public String getUSER() {
        return USER;
    }

    public String getPASSWORD() {
        return PASSWORD;
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
		
	//Creamos las tablas vehiculos, clientes, parcelas, alertas, servicios y area
        String crearTablaVehiculos="CREATE TABLE IF NOT EXISTS Vehiculos ("
                + "matricula VARCHAR (10) PRIMARY KEY NOT NULL,"
                + "marca VARCHAR (15) NOT NULL,"
                + "modelo VARCHAR (15),"
                + "num_ocupantes INT NOT NULL,"
                + "check_in DATE NOT NULL,"
                + "check_out DATE);";
        sentencia.executeUpdate(crearTablaVehiculos);
        System.out.println("tabla vehiculos creada");
                
        String crearTablaClientes="CREATE TABLE IF NOT EXISTS Clientes ("
                + "dni VARCHAR (10) PRIMARY KEY NOT NULL,"
                + "nombre VARCHAR (15) NOT NULL,"
                + "apellido1 VARCHAR (15) NOT NULL,"
                + "apellido2 VARCHAR (15),"
                + "fecha_nac DATE,"
                + "nacionalidad VARCHAR (20),"
                + "telefono INT NOT NULL,"
                + "mail VARCHAR (30));";
        sentencia.executeUpdate(crearTablaClientes);
        System.out.println("tabla clientes creada");
            
        String crearTablaParcelas="CREATE TABLE IF NOT EXISTS Parcelas ("
                + "num_parcela INT PRIMARY KEY NOT NULL,"
                + "disponibilidad BOOLEAN NOT NULL);";        
        sentencia.executeUpdate(crearTablaParcelas);
        System.out.println("tabla parcela creada");
              
        String crearTablaAlertas="CREATE TABLE IF NOT EXISTS Alertas ("
                + "titulo VARCHAR (20) PRIMARY KEY NOT NULL,"
                + "descripcion VARCHAR (60),"
                + "dia_alerta DATE NOT NULL);";
        sentencia.executeUpdate(crearTablaAlertas);
        System.out.println("tabla alertas creada");
                
        String crearTablaServicios="CREATE TABLE IF NOT EXISTS Servicios ("
                + "nombre VARCHAR (20) PRIMARY KEY NOT NULL,"
                + "precio INT NOT NULL);";
        sentencia.executeUpdate(crearTablaServicios);
        System.out.println("tabla servicios creada");
                
        String crearTablaArea="CREATE TABLE IF NOT EXISTS Area ("
                + "direccion VARCHAR (40) PRIMARY KEY NOT NULL,"
                + "telefono INT,"
                + "mail VARCHAR (30),"
                + "web VARCHAR (90),"
                + "num_parcelas INT NOT NULL,"
                + "precio_noche INT NOT NULL,"
                + "ruta_mapa VARCHAR (90),"
                + "ruta_file VARCHAR (90));";
        sentencia.executeUpdate(crearTablaArea);
        System.out.println("tabla area creada");
        
        sentencia.close();
	connect.close();
			
    }
     
    //método para insertar datos en las tablas mediante un string
    public void insertInTabla(String insert)throws SQLException{
        
            Connection connect = DriverManager.getConnection(BBDD,USER,PASSWORD);
            Statement sentencia = connect.createStatement();
            sentencia.executeUpdate("USE furgoGestion;");
            sentencia.executeUpdate(insert);
            System.out.println("se han insertado los datos correctamente");
            sentencia.close();
            connect.close();       
                
    }
    
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
}
