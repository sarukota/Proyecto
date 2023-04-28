/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clasesJava;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class ConexionBBDD {
        
    private final String BBDD;
    private final String USER;
    private final String PASSWORD;
	
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
        try (Connection connect = DriverManager.getConnection(BBDD,USER,PASSWORD)) {
            Statement sentencia = connect.createStatement();
            
            //Creamos la nueva BD si no existe
            String crearDb ="CREATE DATABASE IF NOT EXISTS furgoGestion CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;";
            sentencia.executeUpdate(crearDb);
            //Usamos la nueva BD
            String use = "USE furgoGestion;";
            sentencia.executeUpdate(use);
            //Creamos todas las tablas de la BD
            //Tabla area
            String crearTablaArea="CREATE TABLE IF NOT EXISTS Area ("
                    + "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,"
                    + "direccion VARCHAR (40),"
                    + "telefono INT,"
                    + "mail VARCHAR (30),"
                    + "web VARCHAR (90),"
                    + "num_parcelas INT NOT NULL,"
                    + "precio_noche INT NOT NULL,"
                    + "ruta_mapa VARCHAR (90),"
                    + "ruta_file VARCHAR (90));";
            sentencia.executeUpdate(crearTablaArea);
            
            //Tabla parcelas
            String crearTablaParcelas="CREATE TABLE IF NOT EXISTS Parcelas ("
                    + "num_parcela INT PRIMARY KEY NOT NULL,"
                    + "disponibilidad BOOLEAN NOT NULL);";
            sentencia.executeUpdate(crearTablaParcelas);
            
            //Tabla alertas
            String crearTablaAlertas="CREATE TABLE IF NOT EXISTS Alertas ("
                    + "titulo VARCHAR (40) PRIMARY KEY NOT NULL,"
                    + "descripcion VARCHAR (60),"
                    + "dia_alerta DATE NOT NULL);";
            sentencia.executeUpdate(crearTablaAlertas);
            
            //Tabla servicios
            String crearTablaServicios="CREATE TABLE IF NOT EXISTS Servicios ("
                    + "nombre VARCHAR (20) PRIMARY KEY NOT NULL,"
                    + "precio INT NOT NULL);";
            sentencia.executeUpdate(crearTablaServicios);
            
            //Tabla vehiculos
            String crearTablaVehiculos="CREATE TABLE IF NOT EXISTS Vehiculos ("
                    + "matricula VARCHAR (10) PRIMARY KEY NOT NULL,"
                    + "marca VARCHAR (15),"
                    + "modelo VARCHAR (15),"
                    + "num_ocupantes INT NOT NULL,"
                    + "num_parcela INT,"
                    + "check_in DATE NOT NULL,"
                    + "check_out DATE NOT NULL,"
                    + "FOREIGN KEY(num_parcela)references parcelas(num_parcela));";
            sentencia.executeUpdate(crearTablaVehiculos);
            
            String crearTablaAlertasVehiculos= "CREATE TABLE IF NOT EXISTS AlertasVehiculos ("
                    + "id int PRIMARY KEY NOT NULL AUTO_INCREMENT,"
                    + "titulo VARCHAR (40) NOT NULL,"
                    + "matricula VARCHAR (10) NOT NULL,"
                    + "FOREIGN KEY(titulo)references alertas(titulo)ON UPDATE CASCADE ON DELETE CASCADE,"
                    + "FOREIGN KEY(matricula)references vehiculos(matricula)ON UPDATE CASCADE ON DELETE CASCADE);";
            sentencia.executeUpdate(crearTablaAlertasVehiculos);           
                    
            //Tabla clientes
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
                    + "FOREIGN KEY(matricula)references vehiculos(matricula) ON UPDATE CASCADE ON DELETE CASCADE);";
            sentencia.executeUpdate(crearTablaClientes);
                        
            sentencia.close();
        }
			
    }
     
    //método para realizar updates (insert, delete) de datos en las tablas mediante un string
    public void updateBd(String update)throws SQLException{
        try (Connection connect = DriverManager.getConnection(BBDD,USER,PASSWORD); Statement sentencia = connect.createStatement()) { 
            sentencia.executeUpdate("USE furgoGestion;");
            sentencia.executeUpdate(update);
        }catch(Exception e){
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
    }
    
    //método para realizar updates (insert, delete) de datos en las tablas mediante un string y unos valores
    public void updateBd(String sentenciaSQL, String id) throws SQLException{
        Connection connect = DriverManager.getConnection(BBDD,USER,PASSWORD); Statement sentencia = connect.createStatement();
        sentencia.executeUpdate("USE furgoGestion;");
        connect.setAutoCommit(false);
        try (PreparedStatement ps = connect.prepareStatement(sentenciaSQL)) {
            ps.setObject(1, id);
            ps.executeUpdate();
            connect.commit();
        }catch (SQLException ex) {
            ex.printStackTrace();
            try{
                connect.rollback();
            }catch(SQLException ex2) {
                ex2.printStackTrace();
            }
            throw new SQLException(ex.getMessage());
        }finally {
            try {
                connect.setAutoCommit(true);
            }catch (SQLException ex3) {
                ex3.printStackTrace();
            }
        }      
    }
    
    //método para realizar updates (insert, delete) de datos en las tablas mediante un string y unos valores
    public void updateBd(String sentenciaSQL, String valor, String id) throws SQLException{
        Connection connect = DriverManager.getConnection(BBDD,USER,PASSWORD); Statement sentencia = connect.createStatement();
        sentencia.executeUpdate("USE furgoGestion;");
        connect.setAutoCommit(false);
        try (PreparedStatement ps = connect.prepareStatement(sentenciaSQL)) {
            ps.setObject(1, valor);
            ps.setObject(2, id);
            ps.executeUpdate();
            connect.commit();
        }catch (SQLException ex) {           
            ex.printStackTrace();
            try{                
                connect.rollback();
            }catch(SQLException ex2) {
                ex2.printStackTrace();
                throw new SQLException(ex2.getMessage());
            }
            throw new SQLException(ex.getMessage());
        }finally {
            try {
                connect.setAutoCommit(true);
            }catch (SQLException ex3) {
                ex3.printStackTrace();
            }
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
            catch (Exception ex) {
                ex.printStackTrace();
                throw new SQLException(ex.getMessage());
            }
        sentencia.close();
        connect.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SQLException(ex.getMessage());
        }
    }
    
    //Método para realizar consultas en las tablas de la BD y colocarlas en un array de TextFields
    public void selectFromTabla(String select,JTextField[] tf)throws SQLException{
        try (Connection connect = DriverManager.getConnection(BBDD,USER,PASSWORD); Statement sentencia = connect.createStatement()){ 
        sentencia.executeUpdate("USE furgoGestion;");
            try (ResultSet resultado = sentencia.executeQuery(select)) {
                ResultSetMetaData rsmd = resultado.getMetaData();
                while (resultado.next()){
                    Object [] vector = new Object[rsmd.getColumnCount()];
                    for (int i = 0; i < rsmd.getColumnCount(); i++) {
                        vector[i] = resultado.getString(i+1);
                        tf[i].setText(vector[i].toString());
                    }
                    System.out.println(Arrays.toString(vector));
                }   }       
        sentencia.close();
        connect.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SQLException(ex.getMessage());
        }
    }
    
    //Método que devuelve un solo dato tipo String de una celda de la BD introduciendo la consulta deseada
    public String selectDato(String select) throws SQLException{
        try (Connection connect = DriverManager.getConnection(BBDD,USER,PASSWORD); Statement sentencia = connect.createStatement()) {
            sentencia.executeUpdate("USE furgoGestion;");
            try (ResultSet resultado = sentencia.executeQuery(select)) {
                while (resultado.next()){
                    return(resultado.getString(1));
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SQLException(ex.getMessage());
        }
        return null;
    }
    
    //Método que devuelve un array de string con los datos de una consulta
    public String[]selectFromTabla(String select)throws SQLException{
        try (Connection connect = DriverManager.getConnection(BBDD,USER,PASSWORD); Statement sentencia = connect.createStatement()) {
            sentencia.executeUpdate("USE furgoGestion;");
            try (ResultSet resultado = sentencia.executeQuery(select)) {
                int numRegistros = contarFilas(select);
                String[] datosTabla = new String[numRegistros];
                int contador=0;
                while (resultado.next()){
                    datosTabla[contador] = resultado.getString(1);
                    contador++;
                }    
                resultado.close();
                sentencia.close();
                connect.close();
                return datosTabla;               
            }
            catch (Exception ex) {
                ex.printStackTrace();
                throw new SQLException(ex.getMessage());
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SQLException(ex.getMessage());
        }
    }

        
    //Método para convertir una fecha en formato java.util.Date a un String válido para "date" en MySQL
    public String fechaSQL (java.util.Date fechaJava){
        
        String fechaSQL = null;
        
        if (fechaJava != null) { // Para que no de fallo si es null
            DateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
            fechaSQL = dateFormat.format(fechaJava);
        }
        return fechaSQL;
    }
    
    //Devuelve el numero de contarFilas de una consulta
    public int contarFilas(String consulta) throws SQLException{  
        int filas = 0;
        try (Connection connect = DriverManager.getConnection(BBDD,USER,PASSWORD); Statement sentencia = connect.createStatement()) {
            sentencia.executeUpdate("USE furgoGestion;");
            try (ResultSet resultado = sentencia.executeQuery(consulta)) {
                while (resultado.next()){
                    filas++;
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SQLException(ex.getMessage());
        }
        return filas;
    }

    //método para saber si una consulta tiene datos, si los tiene devuelve true.
    public boolean tieneDatos (String consulta) throws SQLException{
        Connection connect = DriverManager.getConnection(BBDD,USER,PASSWORD); 
        Statement sentencia = connect.createStatement();
        sentencia.executeUpdate("USE furgoGestion;");
        ResultSet resultado;
        try {
            resultado = sentencia.executeQuery(consulta);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SQLException(ex.getMessage());
        }
        return resultado.next();
    }
 
}
            

