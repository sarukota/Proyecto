/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesJava;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import jdk.internal.util.ArraysSupport;

//Meter todos los not null y las FK

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
		
	//Creamos todas las tablas de la BD
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
        
        String crearTablaParcelas="CREATE TABLE IF NOT EXISTS Parcelas ("
                + "num_parcela INT PRIMARY KEY NOT NULL,"
                + "disponibilidad BOOLEAN NOT NULL);";        
        sentencia.executeUpdate(crearTablaParcelas);
        System.out.println("tabla parcela creada");
              
        String crearTablaAlertas="CREATE TABLE IF NOT EXISTS Alertas ("
                + "titulo VARCHAR (40) PRIMARY KEY NOT NULL,"
                + "descripcion VARCHAR (60),"
                + "dia_alerta DATE NOT NULL);";
        sentencia.executeUpdate(crearTablaAlertas);
        System.out.println("tabla alertas creada");
                
        String crearTablaServicios="CREATE TABLE IF NOT EXISTS Servicios ("
                + "nombre VARCHAR (20) PRIMARY KEY NOT NULL,"
                + "precio INT NOT NULL);";
        sentencia.executeUpdate(crearTablaServicios);
        System.out.println("tabla servicios creada");
        
        String crearTablaVehiculos="CREATE TABLE IF NOT EXISTS Vehiculos ("
                + "matricula VARCHAR (10) PRIMARY KEY NOT NULL,"
                + "marca VARCHAR (15),"
                + "modelo VARCHAR (15),"
                + "num_ocupantes INT NOT NULL,"
                + "num_parcela INT NOT NULL,"
                + "check_in DATE NOT NULL,"
                + "check_out DATE NOT NULL,"
                + "titulo_alerta VARCHAR (40) NOT NULL,"
                + "FOREIGN KEY(titulo_alerta)references alertas(titulo),"
                + "FOREIGN KEY(num_parcela)references parcelas(num_parcela));"; 
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
                + "mail VARCHAR (30),"
                + "matricula VARCHAR (10) NOT NULL,"
                + "FOREIGN KEY(matricula)references vehiculos(matricula));";
        sentencia.executeUpdate(crearTablaClientes);
        System.out.println("tabla clientes creada");
            
        String crearTablaServVehiculo="CREATE TABLE IF NOT EXISTS ServiciosXvehiculo ("
                + "nombre_servicio VARCHAR (20) NOT NULL,"
                + "matricula VARCHAR (10) NOT NULL,"
                + "FOREIGN KEY(nombre_servicio)references servicios(nombre),"
                + "FOREIGN KEY(matricula)references vehiculos(matricula));";
        sentencia.executeUpdate(crearTablaServVehiculo);
        System.out.println("tabla serviciosXvehiculo creada");
        
        sentencia.close();
	connect.close();
			
    }
    
    //Método para devolver las filas de una tabla:
    public int contarFilas (String consulta) throws SQLException{  
        try (Connection connect = DriverManager.getConnection(BBDD,USER,PASSWORD); Statement sentencia = connect.createStatement()) {
            sentencia.executeUpdate("USE furgoGestion;");
            try (ResultSet resultado = sentencia.executeQuery(consulta)) {
                while (resultado.next()){
                    System.out.println("filas tabla ants de cerrar: "+resultado.getString(1));
                    return Integer.parseInt(resultado.getString(1));
                }
            }
            sentencia.close();
            connect.close();
        }
        return 0;
    }
     
    //método para realizar updates (insert, delete) de datos en las tablas mediante un string
    public void UpdateBd(String update)throws SQLException{
        try (Connection connect = DriverManager.getConnection(BBDD,USER,PASSWORD); Statement sentencia = connect.createStatement()) { 
            sentencia.executeUpdate("USE furgoGestion;");
            sentencia.executeUpdate(update);
            sentencia.close();
            connect.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Ha ocurrido un problema al actualizar los datos");     
        }
    }
    
    //Método para realizar consultas en las tablas de la BD y colocarlas en una Jtable
    public void selectFromTabla(String select,DefaultTableModel dtm)throws SQLException{
        try (Connection connect = DriverManager.getConnection(BBDD,USER,PASSWORD); Statement sentencia = connect.createStatement()) {
            sentencia.executeUpdate("USE furgoGestion;");
            try (ResultSet resultado = sentencia.executeQuery(select)) {
                ResultSetMetaData rsmd = resultado.getMetaData();
                while (resultado.next()){
                    Object [] vector = new Object[rsmd.getColumnCount()];
                    for (int i = 0; i < rsmd.getColumnCount(); i++) {
                        vector[i] = resultado.getString(i+1);
                    }
                    dtm.addRow(vector);
                    }
                resultado.close();
                }
        sentencia.close();
        connect.close();
        }
    }
    
    //Método para realizar consultas en las tablas de la BD y colocarlas en TextFields
    public void selectFromTabla(String select,JTextField[] tf)throws SQLException{
        try (Connection connect = DriverManager.getConnection(BBDD,USER,PASSWORD); Statement sentencia = connect.createStatement()) {
            sentencia.executeUpdate("USE furgoGestion;");
            try (ResultSet resultado = sentencia.executeQuery(select)) {
                ResultSetMetaData rsmd = resultado.getMetaData();
                while (resultado.next()){
                    System.out.println("Se crea resultset con los resultados");
                    Object [] vector = new Object[rsmd.getColumnCount()];
                    for (int i = 0; i < rsmd.getColumnCount(); i++) {
                        vector[i] = resultado.getString(i+1);
                        tf[i].setText(vector[i].toString());
                    }
                    System.out.println(Arrays.toString(vector));
                    }
                resultado.close();
                }
        sentencia.close();
        connect.close();
        }
    }  
    
    //Método que devuelve un array de string de un número de registros de una tabla
    public String[]selectFromTabla(String select, int numRegistros)throws SQLException{
        try (Connection connect = DriverManager.getConnection(BBDD,USER,PASSWORD); Statement sentencia = connect.createStatement()) {
            sentencia.executeUpdate("USE furgoGestion;");
            try (ResultSet resultado = sentencia.executeQuery(select)) {
                ResultSetMetaData rsmd = resultado.getMetaData();
                String[] datosTabla = new String[numRegistros];
                int contador=0;
                while (resultado.next()){ //mientras haya registros en la tabla
                    datosTabla[contador] = resultado.getString(1);
                    contador++;
                }    
                System.out.println("los datos seleccionados de la tablas son: "+Arrays.toString(datosTabla));
                resultado.close();
                sentencia.close();
                connect.close();
                return datosTabla;    
                          
            }
        }
    }
        
    //Método para convertir una fecha en formato java.util.Date a un String válido para "date" en MySQL
    public String fechaSQL (java.util.Date fechaJava){
        DateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
        String fechaSQL = dateFormat.format(fechaJava);
        return fechaSQL;
    }
    
    /*//Eliminamos el esquema si existe
		String ifExists = "DROP SCHEMA  furgoGestion;";
		sentencia.UpdateBd(ifExists);*/

}