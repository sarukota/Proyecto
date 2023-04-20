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
            String crearDb ="CREATE DATABASE IF NOT EXISTS furgoGestion;";
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
            System.out.println("tabla area creada");
            
            //Tabla parcelas
            String crearTablaParcelas="CREATE TABLE IF NOT EXISTS Parcelas ("
                    + "num_parcela INT PRIMARY KEY NOT NULL,"
                    + "disponibilidad BOOLEAN NOT NULL);";
            sentencia.executeUpdate(crearTablaParcelas);
            System.out.println("tabla parcela creada");
            
            //Tabla alertas
            String crearTablaAlertas="CREATE TABLE IF NOT EXISTS Alertas ("
                    + "titulo VARCHAR (40) PRIMARY KEY NOT NULL,"
                    + "descripcion VARCHAR (60),"
                    + "dia_alerta DATE NOT NULL);";
            sentencia.executeUpdate(crearTablaAlertas);
            System.out.println("tabla alertas creada");
            
            //Tabla servicios
            String crearTablaServicios="CREATE TABLE IF NOT EXISTS Servicios ("
                    + "nombre VARCHAR (20) PRIMARY KEY NOT NULL,"
                    + "precio INT NOT NULL);";
            sentencia.executeUpdate(crearTablaServicios);
            System.out.println("tabla servicios creada");
            
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
            System.out.println("tabla vehiculos creada");
            
            String crearTablaAlertasVehiculos= "CREATE TABLE IF NOT EXISTS AlertasVehiculos ("
                    + "id int PRIMARY KEY NOT NULL AUTO_INCREMENT,"
                    + "titulo VARCHAR (40) NOT NULL,"
                    + "matricula VARCHAR (10) NOT NULL,"
                    + "FOREIGN KEY(titulo)references alertas(titulo),"
                    + "FOREIGN KEY(matricula)references vehiculos(matricula));";
            sentencia.executeUpdate(crearTablaAlertasVehiculos);
            System.out.println("tabla AlertasVehiculos creada");
            
                    
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
            System.out.println("tabla clientes creada");
                        
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
            JOptionPane.showMessageDialog(null,"Ha ocurrido un problema al actualizar los datos");     
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
            JOptionPane.showMessageDialog(null,"Ha ocurrido un problema al actualizar los datos");
            try{
                connect.rollback();
            }catch(SQLException ex2) {
                ex2.printStackTrace();
            }
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
            JOptionPane.showMessageDialog(null,"No se ha podido cargar el nuevo dato");
            try{                
                connect.rollback();
            }catch(SQLException ex2) {
                ex2.printStackTrace();
            }
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
        sentencia.close();
        connect.close();
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
    }
    
    //Método que devuelve un solo dato de una celda de la BD introduciendo la consulta deseada
    public String selectDato(String select) throws SQLException{
        try (Connection connect = DriverManager.getConnection(BBDD,USER,PASSWORD); Statement sentencia = connect.createStatement()) {
            sentencia.executeUpdate("USE furgoGestion;");
            try (ResultSet resultado = sentencia.executeQuery(select)) {
                while (resultado.next()){
                    return(resultado.getString(1));
                }
            }
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
        }
    }

        
    //Método para convertir una fecha en formato java.util.Date a un String válido para "date" en MySQL
    public String fechaSQL (java.util.Date fechaJava){
        DateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
        String fechaSQL = dateFormat.format(fechaJava);
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
        return filas;
    }
    
    /*
    //Metodo que devuelve un array de objetos de contarFilas y columnas con los datos de un resultSet.
    public Object[][] arrayConsulta(String consulta) throws SQLException{
        Object[][] results;
        try (Connection connect = DriverManager.getConnection(BBDD,USER,PASSWORD); Statement sentencia = connect.createStatement()) {
            sentencia.executeUpdate("USE furgoGestion;");
            try (
                ResultSet resultado = sentencia.executeQuery(consulta)) {
                ResultSetMetaData metaData = resultado.getMetaData();
                int columnas = metaData.getColumnCount();
                // Se llama al método contarFilas para conocer el numero de contarFilas de la consulta
                int filas = contarFilas(consulta);
                results = new Object[filas][columnas];
                // Se rellena el array de resultados
                int i = 0;
                while (resultado.next()) {
                    for (int j = 0; j < columnas; j++) {
                        results[i][j] = resultado.getObject(j + 1);
                    }
                    i++;
                }
            }
        }
        return results;
            
    }*/
    
    //método para saber si una consulta tiene datos, si los tiene devuelve true.
    public boolean tieneDatos (String consulta) throws SQLException{
        Connection connect = DriverManager.getConnection(BBDD,USER,PASSWORD); 
        Statement sentencia = connect.createStatement();
        sentencia.executeUpdate("USE furgoGestion;");
        ResultSet resultado = sentencia.executeQuery(consulta);
        return resultado.next();
    }
    
    /*
    //metodo para imprimir un array bidimensional
    public void printTabla(Object array[][]){
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j]+ ", ");
            }
            System.out.println();               
        } 
    }*/
    
}
            

