/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package pantallasSwing;

import clasesJava.ConexionBBDD;
import clasesJava.Interfaz;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.*;


/* Este panel selecciona los datos de la base de datos y permite modificarlos y eliminarlos*/

public class PnlGestion extends javax.swing.JPanel{
    
    ConexionBBDD conexion;
    Object buscarPor;
    Calendar calendario = Calendar.getInstance();
    Date fechaHoy = calendario.getTime();
    DefaultTableModel dtmResult;
    String nombreTbl; 

    public PnlGestion() {
        initComponents();
        conexion = new ConexionBBDD();
        setSize(1300,700); //Da el tamaño a la ventana
        setLocation(0,0);
        configInicial();

    }
    
    //Da un valor inicial al comboBox filtro y crea un ActionListener para el comboBox Seleccion
    public void configInicial(){
        cbFiltro.removeAllItems();
        cbFiltro.addItem("Todos");
        cbFiltro.addItem("Antiguos");
        cbFiltro.addItem("En area");
        
        cbSeleccion.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0){
                llenarCbFiltro();
            }
        });
    }
    
    //metodo que rellena el ComboBox Filtro según los valores del ComboBox Seleccion
    public void llenarCbFiltro(){
        if (cbSeleccion.getSelectedItem() == "Por vehiculo" | cbSeleccion.getSelectedItem() == "Por cliente") {
            cbFiltro.removeAllItems();
            cbFiltro.addItem("Todos");
            cbFiltro.addItem("Antiguos");
            cbFiltro.addItem("En area");
              
        }
        else if (cbSeleccion.getSelectedItem() == "Por parcela"){
            cbFiltro.removeAllItems();
            cbFiltro.addItem("Todas");
            cbFiltro.addItem("Ocupadas");
            cbFiltro.addItem("Disponibles");
  
        }
    }
    
    //Devuelve un DefaultTableModel con las cabeceras pasadas por parámetro
    public DefaultTableModel SetCabeceras (String[] cabecera){
        dtmResult = new DefaultTableModel();
        dtmResult.setColumnIdentifiers(cabecera);
        tblResult.setModel(dtmResult);
        dtmResult.setRowCount(0);
        return dtmResult;
    }
    
    //metodo para definir el dtmResult y mostrar los datos de la BD en la Jtable según los datos seleccionados de los comboBox
    public void llenarTablaBuscar() throws SQLException{
        buscarPor = cbSeleccion.getSelectedItem(); //guardo el tipo de busqueda que está seleccionada en el comboBox
        tblResult.setRowHeight(25);
        if (buscarPor == "Por vehiculo"){
            nombreTbl= "vehiculos";
            String [] cabecera = {"Matricula","Marca","Modelo","Num_Ocupantes","Num_Parcela","Check_in","Check_out",};
            dtmResult = SetCabeceras(cabecera);
            try {
                if (cbFiltro.getSelectedItem() == "Todos") conexion.selectFromTabla("SELECT matricula, marca, modelo, num_ocupantes, num_parcela, check_in, check_out FROM vehiculos;",dtmResult);
                else if (cbFiltro.getSelectedItem() == "En area") conexion.selectFromTabla("SELECT matricula, marca, modelo, num_ocupantes, num_parcela, check_in, check_out FROM vehiculos WHERE check_out >= '"+conexion.fechaSQL(fechaHoy)+"';",dtmResult);
                else if (cbFiltro.getSelectedItem() == "Antiguos") conexion.selectFromTabla("SELECT matricula, marca, modelo, num_ocupantes, num_parcela, check_in, check_out FROM vehiculos WHERE check_out < '"+conexion.fechaSQL(fechaHoy)+"';",dtmResult);
            } catch (SQLException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }    
        }else if (buscarPor == "Por cliente"){
            nombreTbl= "clientes";
            String [] cabecera = {"DNI","Nombre","Apellido 1","Apellido2","Fecha_nac","Nacionalidad","Telefono","Mail","Matricula",};
            dtmResult = SetCabeceras(cabecera);
            String consultaJoin = "SELECT clientes.dni, clientes.nombre, clientes.apellido1, clientes.apellido2,clientes.fecha_nac,clientes.nacionalidad,clientes.telefono,clientes.mail,clientes.matricula FROM clientes INNER JOIN vehiculos ON clientes.matricula = vehiculos.matricula WHERE vehiculos.check_out";
            try {
                if(cbFiltro.getSelectedItem() == "Todos")conexion.selectFromTabla("SELECT dni, nombre, apellido1, apellido2, fecha_nac, nacionalidad, telefono, mail, matricula FROM clientes;",dtmResult);
                else if (cbFiltro.getSelectedItem() == "En area")conexion.selectFromTabla(consultaJoin+" >= '"+conexion.fechaSQL(fechaHoy)+"';",dtmResult);
                else if (cbFiltro.getSelectedItem() == "Antiguos")conexion.selectFromTabla(consultaJoin+" < '"+conexion.fechaSQL(fechaHoy)+"';",dtmResult);
            } catch (SQLException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            String [] cabecera = {"Nº parcela","Disponible",};
            dtmResult = SetCabeceras(cabecera);
            try {
                if (cbFiltro.getSelectedItem() == "Todas") conexion.selectFromTabla("SELECT num_parcela, CASE WHEN disponibilidad = 0 THEN 'NO' ELSE 'SI' END as disponibilidad FROM parcelas",dtmResult);//devuelve todas las parcelas
                else if (cbFiltro.getSelectedItem() == "Ocupadas") conexion.selectFromTabla("SELECT num_parcela, disponibilidad FROM parcelas where disponibilidad = false;",dtmResult);
                else conexion.selectFromTabla("SELECT num_parcela, disponibilidad FROM parcelas where disponibilidad = true;",dtmResult);
            } catch (SQLException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        editarDatos();
    }
    
    //Método que añade un escuchador a la tabla para modificar los datos de la BD según los datos cambiados en la Jtable
    public void editarDatos(){
        tblResult.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int fila = e.getFirstRow();
                int columna = e.getColumn();
                String id = dtmResult.getValueAt(fila, 0).toString();
                if (e.getType() == TableModelEvent.UPDATE) {
                    String valor = dtmResult.getValueAt(fila, columna).toString();
                    System.out.println("id: "+id);
                    String nombreColumna = dtmResult.getColumnName(columna);//No es necesario cambiar nombre de cabeceras??
                    String sentenciaSQL = "UPDATE "+nombreTbl+" SET "+ nombreColumna + "=? WHERE "+dtmResult.getColumnName(0)+ "=?;";
                    try {
                        conexion.updateBd(sentenciaSQL,valor,id);
                    } catch (SQLException ex) {
                        Logger.getLogger(PnlGestion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }    
            }
        });
    }

    //Método que elimina una fila tanto de la Jtable como de la BD
    public void eliminarFila(){
        if (tblResult.getSelectedRow() < 0) JOptionPane.showMessageDialog(null,"Selecciona una fila"); //Si no hay fila seleccionada
        else{
            int filaSeleccionada = tblResult.getSelectedRow();
            String id = tblResult.getValueAt(filaSeleccionada, 0).toString();
            Object[] opciones = { "ACEPTAR", "CANCELAR" };
            int opcion = JOptionPane.showOptionDialog(null, "Se eliminarán los datos", "Confirmación",JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opciones, opciones[0]);
            if (opcion == 0){ //ACEPTAR
                String sentenciaSQL = "DELETE FROM "+nombreTbl+" WHERE "+dtmResult.getColumnName(0)+ "=?;";
                    try {
                        if (buscarPor == "Por vehiculo"){
                            int parcela = Integer.parseInt(conexion.selectDato("SELECT num_parcela FROM vehiculos WHERE matricula = '"+id+"';"));
                            conexion.updateBd(sentenciaSQL,id);
                            conexion.updateBd("DELETE FROM alertasVehiculos WHERE matricula = '"+id+"';");
                            conexion.updateBd("DELETE FROM alertas WHERE titulo = 'Salida del vehículo "+id+"';");
                            conexion.updateBd("UPDATE parcelas SET disponibilidad = true WHERE num_parcela = "+parcela+";");
                        }
                        else conexion.updateBd(sentenciaSQL,id);
                        llenarTablaBuscar();
                    } catch (SQLException ex) {
                        Logger.getLogger(PnlGestion.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
        }            
    }  
    
    //Método que elimina todos los datos de la tabla seleccionada en el comboBox
    public void eliminarTodo(String sentencia){
        Object[] opciones = { "ACEPTAR", "CANCELAR" };
        int opcion = JOptionPane.showOptionDialog(null, "Se eliminarán todos los datos de la tabla", "Confirmación",JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opciones, opciones[0]);
        if (opcion == 0){ //ACEPTAR
                try {
                    conexion.updateBd(sentencia);
                    llenarTablaBuscar();
                } catch (SQLException ex) {
                    Logger.getLogger(PnlGestion.class.getName()).log(Level.SEVERE, null, ex);
                }
        }              
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        tblResult = new javax.swing.JTable();
        cbSeleccion = new javax.swing.JComboBox<>();
        btnBuscar = new javax.swing.JButton();
        cbFiltro = new javax.swing.JComboBox<>();
        btnBorrarFila = new javax.swing.JButton();
        btnBorrarTodo = new javax.swing.JButton();

        setBackground(new java.awt.Color(0, 153, 204));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tblResult);

        add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 890, 480));

        cbSeleccion.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        cbSeleccion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Por vehiculo", "Por cliente", "Por parcela" }));
        cbSeleccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSeleccionActionPerformed(evt);
            }
        });
        add(cbSeleccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 120, 30));

        btnBuscar.setBackground(new java.awt.Color(0, 0, 0));
        btnBuscar.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, -1, 30));

        cbFiltro.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        add(cbFiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 160, 30));

        btnBorrarFila.setBackground(new java.awt.Color(255, 0, 51));
        btnBorrarFila.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        btnBorrarFila.setForeground(new java.awt.Color(255, 255, 255));
        btnBorrarFila.setText("Eliminar fila");
        btnBorrarFila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarFilaActionPerformed(evt);
            }
        });
        add(btnBorrarFila, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 40, -1, 30));

        btnBorrarTodo.setBackground(new java.awt.Color(255, 0, 51));
        btnBorrarTodo.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        btnBorrarTodo.setForeground(new java.awt.Color(255, 255, 255));
        btnBorrarTodo.setText("Eliminar todo");
        btnBorrarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarTodoActionPerformed(evt);
            }
        });
        add(btnBorrarTodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 40, 160, 30));
    }// </editor-fold>//GEN-END:initComponents

    private void cbSeleccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSeleccionActionPerformed
        buscarPor = cbSeleccion.getSelectedItem(); //guardo el nombre que está seleccionado cuando presiono btnAnadir
    }//GEN-LAST:event_cbSeleccionActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        if (buscarPor == "Por parcela") {
            btnBorrarFila.setVisible(false);
            btnBorrarTodo.setVisible(false);
        }
        else{
            btnBorrarFila.setVisible(true);
            btnBorrarTodo.setVisible(true);
        } 
        try {
            llenarTablaBuscar();
        } catch (SQLException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnBorrarFilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarFilaActionPerformed
        eliminarFila();
    }//GEN-LAST:event_btnBorrarFilaActionPerformed

    private void btnBorrarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarTodoActionPerformed
        //borro vehiculos segun el filtro
        if (buscarPor == "Por vehiculo" && cbFiltro.getSelectedItem()== "Todos"){
            try {
                eliminarTodo("DELETE FROM "+nombreTbl+";");
                conexion.updateBd("DELETE FROM alertasVehiculos;");
                conexion.updateBd("DELETE FROM alertas WHERE descripcion = 'Alerta automatica';");
                conexion.updateBd("UPDATE parcelas SET disponibilidad = true;");
            } catch (SQLException ex) {
                Logger.getLogger(PnlGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if (buscarPor == "Por vehiculo" && cbFiltro.getSelectedItem()== "En area"){
            try {
                conexion.updateBd("UPDATE Parcelas SET disponibilidad = true WHERE num_parcela IN(SELECT num_parcela FROM Vehiculos WHERE check_out >= '"+conexion.fechaSQL(fechaHoy)+"');");
                eliminarTodo("DELETE FROM "+nombreTbl+" WHERE check_out >= '"+conexion.fechaSQL(fechaHoy)+"';");
                //TODO; borrar alertavehiculos
                conexion.updateBd("DELETE FROM alertas WHERE descripcion = 'Alerta automatica' and dia_alerta >= '"+conexion.fechaSQL(fechaHoy)+"';");      
            } catch (SQLException ex){ 
                Logger.getLogger(PnlGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if (buscarPor == "Por vehiculo" && cbFiltro.getSelectedItem()== "Antiguos"){  
            try {
                conexion.updateBd("UPDATE Parcelas SET disponibilidad = true WHERE num_parcela IN(SELECT num_parcela FROM Vehiculos WHERE check_out < '"+conexion.fechaSQL(fechaHoy)+"');");
                eliminarTodo("DELETE FROM "+nombreTbl+" WHERE check_out < '"+conexion.fechaSQL(fechaHoy)+"';");
                //TODO; borrar alertavehiculos
                conexion.updateBd("DELETE FROM alertas WHERE descripcion = 'Alerta automatica' and dia_alerta < '"+conexion.fechaSQL(fechaHoy)+"';");    
            } catch (SQLException ex) {
                Logger.getLogger(PnlGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
        //Borro clientes segun el filtro    
        }else if (buscarPor == "Por cliente" && cbFiltro.getSelectedItem()== "Todos"){
             eliminarTodo("DELETE FROM "+nombreTbl+";");
        }else if (buscarPor == "Por cliente" && cbFiltro.getSelectedItem()== "En area"){
            eliminarTodo("DELETE FROM "+nombreTbl+" WHERE check_out >= '"+conexion.fechaSQL(fechaHoy)+"';");
        }else if (buscarPor == "Por cliente" && cbFiltro.getSelectedItem()== "Antiguos"){
            eliminarTodo("DELETE FROM "+nombreTbl+" WHERE check_out < '"+conexion.fechaSQL(fechaHoy)+"';");
        }   
    }//GEN-LAST:event_btnBorrarTodoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBorrarFila;
    private javax.swing.JButton btnBorrarTodo;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JComboBox<String> cbFiltro;
    private javax.swing.JComboBox<String> cbSeleccion;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblResult;
    // End of variables declaration//GEN-END:variables

}


