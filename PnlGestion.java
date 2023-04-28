/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package pantallasSwing;

import clasesJava.ConexionBBDD;
import clasesJava.Interfaz;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
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
    String id_tabla;

    public PnlGestion() {
        initComponents();
        conexion = new ConexionBBDD();
        configInicial();
    }
    
    public void llenarFiltroInicio() {
        
        cbFiltro.removeAllItems();
        cbFiltro.addItem("Todos");
        cbFiltro.addItem("Antiguos");
        cbFiltro.addItem("En area");
        cbFiltro.addItem("Por matricula");
        
    }
    
    //Da un valor inicial al comboBox filtro y crea un ActionListener para el comboBox Seleccion
    public void configInicial(){
        
        llenarFiltroInicio();
        
        buscarPor = "Por vehiculo";
        
        jtMatricula.setVisible(false);
        
        cbSeleccion.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0){
                llenarCbFiltro();
            }
        });
    }
    
    //metodo que rellena el ComboBox Filtro según los valores del ComboBox Seleccion
    public void llenarCbFiltro(){
         cbFiltro.removeAllItems();
        if (cbSeleccion.getSelectedItem() == "Por vehiculo" || cbSeleccion.getSelectedItem() == "Por cliente" ) {
            cbFiltro.addItem("Todos");
            cbFiltro.addItem("Antiguos");
            cbFiltro.addItem("En area");
            cbFiltro.addItem("Por matricula");
        }
        else if (cbSeleccion.getSelectedItem() == "Por parcela"){
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
                else if (cbFiltro.getSelectedItem() == "Por matricula") conexion.selectFromTabla("SELECT matricula, marca, modelo, num_ocupantes, num_parcela, check_in, check_out FROM vehiculos WHERE matricula = '" + jtMatricula.getText().trim() + "';",dtmResult);
            
            } catch (SQLException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "No se han podido recuperar los datos de los vehículos.");
            }    
        }else if (buscarPor == "Por cliente"){
            
            nombreTbl= "clientes";
            String [] cabecera = {"DNI","Nombre","Apellido1","Apellido2","Fecha_nac","Nacionalidad","Telefono","Mail","Matricula",};
            dtmResult = SetCabeceras(cabecera);
            String consultaJoin = "SELECT clientes.dni, clientes.nombre, clientes.apellido1, clientes.apellido2,clientes.fecha_nac,clientes.nacionalidad,clientes.telefono,clientes.mail,clientes.matricula FROM clientes INNER JOIN vehiculos ON clientes.matricula = vehiculos.matricula WHERE vehiculos.check_out";
            try {
                if(cbFiltro.getSelectedItem() == "Todos")conexion.selectFromTabla("SELECT dni, nombre, apellido1, apellido2, fecha_nac, nacionalidad, telefono, mail, matricula FROM clientes;",dtmResult);
                else if (cbFiltro.getSelectedItem() == "En area")conexion.selectFromTabla(consultaJoin+" >= '"+conexion.fechaSQL(fechaHoy)+"';",dtmResult);
                else if (cbFiltro.getSelectedItem() == "Antiguos")conexion.selectFromTabla(consultaJoin+" < '"+conexion.fechaSQL(fechaHoy)+"';",dtmResult);
                else if (cbFiltro.getSelectedItem() == "Por matricula")conexion.selectFromTabla("SELECT dni, nombre, apellido1, apellido2, fecha_nac, nacionalidad, telefono, mail, matricula FROM clientes WHERE matricula='" + jtMatricula.getText().trim() + "';",dtmResult);
            } catch (SQLException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "No se han podido recuperar los datos de los clientes.");
            }
        }else{
            String [] cabecera = {"Nº parcela","Disponible",};
            dtmResult = SetCabeceras(cabecera);
            
            nombreTbl= "parcelas";
            try {
                if (cbFiltro.getSelectedItem() == "Todas") conexion.selectFromTabla("SELECT num_parcela, CASE WHEN disponibilidad = 0 THEN 'NO' ELSE 'SI' END as disponibilidad FROM parcelas",dtmResult);//devuelve todas las parcelas
                else if (cbFiltro.getSelectedItem() == "Ocupadas") conexion.selectFromTabla("SELECT num_parcela, CASE WHEN disponibilidad = 0 THEN 'NO' ELSE 'SI' END as disponibilidad FROM parcelas where disponibilidad = false;",dtmResult);
                else conexion.selectFromTabla("SELECT num_parcela, CASE WHEN disponibilidad = 0 THEN 'NO' ELSE 'SI' END as disponibilidad FROM parcelas where disponibilidad = true;",dtmResult);
            } catch (SQLException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "No se han podido recuperar los datos de las parcelas.");
            }
        }
        editarDatos();
    }
    
    //Método que añade un escuchador a la tabla para modificar los datos de la BD según los datos cambiados en la Jtable
    public void editarDatos(){
        tblResult.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tblResult.getSelectedRow();
                id_tabla = tblResult.getValueAt(fila, 0).toString();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
            
        });

        if (nombreTbl.toLowerCase() != "parcelas") {
            tblResult.getModel().addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    int fila = e.getFirstRow();
                    int columna = e.getColumn();
                    if (e.getType() == TableModelEvent.UPDATE) {
                        
                        String valor = dtmResult.getValueAt(fila, columna).toString();
                        String nombreColumna = dtmResult.getColumnName(columna);//No es necesario cambiar nombre de cabeceras??
                        
                        String nombreColumnalw = nombreColumna.toLowerCase();
                        
                        // Editar la disponibilidad de una parcela si se cambia en la tabla
                        if ("vehiculos".equals(nombreTbl.toLowerCase()) && "num_parcela".equals(nombreColumna.toLowerCase())) 
                            {
                            try {
                                String numParcelasAntiguo = conexion.selectDato("SELECT num_parcela from vehiculos where matricula='" + id_tabla + "';");
                                String sentenciaAntiguaParcela = "UPDATE parcelas SET disponibilidad = true WHERE num_parcela = " + numParcelasAntiguo;
                                String sentenciaNuevaParcela = "UPDATE parcelas SET disponibilidad = false WHERE num_parcela = " + valor;

                                conexion.updateBd(sentenciaAntiguaParcela);
                                conexion.updateBd(sentenciaNuevaParcela);
                            } catch (SQLException ex) {
                                Logger.getLogger(PnlGestion.class.getName()).log(Level.SEVERE, null, ex);
                                JOptionPane.showMessageDialog(null, "No se han podido actualizar los datos.");
                            } 
                        }
                        
                        if ("vehiculos".equals(nombreTbl.toLowerCase()) && "matricula".equals(nombreColumna.toLowerCase())) 
                            {
                            try {
                                
                                String sentenciaCambioAlerta = "UPDATE alertas SET titulo = 'Salida del vehiculo " + valor + "' WHERE titulo = 'Salida del vehiculo "+id_tabla+"'";
                                conexion.updateBd(sentenciaCambioAlerta);
                                
                            } catch (SQLException ex) {
                                Logger.getLogger(PnlGestion.class.getName()).log(Level.SEVERE, null, ex);
                                JOptionPane.showMessageDialog(null, "No se han podido actualizar los datos.");
                            } 
                        }
                        

                        String sentenciaSQL = "UPDATE "+nombreTbl+" SET "+ nombreColumna + "=? WHERE "+dtmResult.getColumnName(0)+ "=?;";
                        try {
                            conexion.updateBd(sentenciaSQL,valor,id_tabla);
                        } catch (SQLException ex) {
                            Logger.getLogger(PnlGestion.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, "No se han podido actualizar los datos.");
                        }
                    }    
                }
            });
        }
        else {
            tblResult.setDefaultEditor(Object.class, null); // No se puede editar la tabla parcela
        }
        
    }

    //Método que elimina una fila tanto de la Jtable como de la BD
    public void eliminarFila(){
        if (tblResult.getSelectedRow() < 0|| tblResult.getSelectedRowCount() > 1)
            JOptionPane.showMessageDialog(this,"Selecciona una fila"); //Si no hay fila seleccionada
        else{
            int filaSeleccionada = tblResult.getSelectedRow();
            String id = tblResult.getValueAt(filaSeleccionada, 0).toString();
            Object[] opciones = { "ACEPTAR", "CANCELAR" };
            int opcion = JOptionPane.showOptionDialog(this, "Se eliminarán los datos", "Confirmación",JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opciones, opciones[0]);
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
                        if (buscarPor == "Por cliente"){
                            String matricula = tblResult.getValueAt(filaSeleccionada, 8).toString();
                            String num_ocupantes = conexion.selectDato("SELECT num_ocupantes FROM vehiculos WHERE matricula = '" + matricula + "';");
                            int num_ocupantes_nuevo = Integer.parseInt(num_ocupantes) - 1;
                            conexion.updateBd("UPDATE vehiculos SET num_ocupantes =" + num_ocupantes_nuevo + " WHERE matricula ='" + matricula + "';");
                        }
                        llenarTablaBuscar();
                    } catch (SQLException ex) {
                        Logger.getLogger(PnlGestion.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "No se ha podido eliminar la fila");
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
                    JOptionPane.showMessageDialog(null, "No se han podido eliminar los datos.");
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
        jtMatricula = new javax.swing.JTextField();
        btnAniadirPasajero = new javax.swing.JButton();

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
        add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 40, -1, 30));

        cbFiltro.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        cbFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFiltroActionPerformed(evt);
            }
        });
        add(cbFiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 40, 140, 30));

        btnBorrarFila.setBackground(new java.awt.Color(255, 0, 51));
        btnBorrarFila.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        btnBorrarFila.setForeground(new java.awt.Color(255, 255, 255));
        btnBorrarFila.setText("Eliminar fila");
        btnBorrarFila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarFilaActionPerformed(evt);
            }
        });
        add(btnBorrarFila, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 20, 110, 30));

        btnBorrarTodo.setBackground(new java.awt.Color(255, 0, 51));
        btnBorrarTodo.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        btnBorrarTodo.setForeground(new java.awt.Color(255, 255, 255));
        btnBorrarTodo.setText("Eliminar todo");
        btnBorrarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarTodoActionPerformed(evt);
            }
        });
        add(btnBorrarTodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 60, 110, 30));

        jtMatricula.setToolTipText("");
        jtMatricula.setMaximumSize(new java.awt.Dimension(120, 30));
        jtMatricula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtMatriculaActionPerformed(evt);
            }
        });
        add(jtMatricula, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, 120, 30));

        btnAniadirPasajero.setBackground(new java.awt.Color(0, 0, 0));
        btnAniadirPasajero.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        btnAniadirPasajero.setForeground(new java.awt.Color(255, 255, 255));
        btnAniadirPasajero.setText("Añadir pasajero");
        btnAniadirPasajero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAniadirPasajeroActionPerformed(evt);
            }
        });
        add(btnAniadirPasajero, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 40, -1, 30));
    }// </editor-fold>//GEN-END:initComponents

    private void cbSeleccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSeleccionActionPerformed
        buscarPor = cbSeleccion.getSelectedItem(); //guardo el nombre que está seleccionado cuando presiono btnAnadir
    }//GEN-LAST:event_cbSeleccionActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        if (buscarPor == "Por parcela") {
            btnBorrarFila.setVisible(false);
            btnBorrarTodo.setVisible(false);
            this.repaint(); // Para que se repinte el panel y se dejen de mostrar los botones
        }
        else{
            btnBorrarFila.setVisible(true);
            btnBorrarTodo.setVisible(true);
        } 
        
        if (buscarPor == "Por vehiculo") {
            btnAniadirPasajero.setVisible(true);
        }
        else {
            btnAniadirPasajero.setVisible(false);
            this.repaint();
        }
        
        try {
            llenarTablaBuscar();
        } catch (SQLException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

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
            try {
                eliminarTodo("DELETE FROM "+nombreTbl+";");
                conexion.updateBd("UPDATE vehiculos SET num_ocupantes = 0;");
            } catch (SQLException ex) {
                Logger.getLogger(PnlGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if (buscarPor == "Por cliente" && cbFiltro.getSelectedItem()== "En area"){
            try {
                eliminarTodo("DELETE FROM "+nombreTbl+" WHERE matricula IN (SELECT matricula FROM vehiculos WHERE check_out >= '"+conexion.fechaSQL(fechaHoy)+"');");
                conexion.updateBd("UPDATE vehiculos SET num_ocupantes = 0 WHERE check_out >= '"+conexion.fechaSQL(fechaHoy)+"';");
            } catch (SQLException ex) {
                Logger.getLogger(PnlGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if (buscarPor == "Por cliente" && cbFiltro.getSelectedItem()== "Antiguos"){
            try {
                eliminarTodo("DELETE FROM "+nombreTbl+" WHERE matricula IN (SELECT matricula FROM vehiculos WHERE check_out < '"+conexion.fechaSQL(fechaHoy)+"');");
                conexion.updateBd("UPDATE vehiculos SET num_ocupantes = 0 WHERE check_out < '"+conexion.fechaSQL(fechaHoy)+"';");
            } catch (SQLException ex) {
                Logger.getLogger(PnlGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }   
        else if (buscarPor == "Por cliente" && cbFiltro.getSelectedItem()== "Por matricula"){
            try {
                String matricula = jtMatricula.getText();
                eliminarTodo("DELETE FROM "+nombreTbl+" WHERE matricula = '" + matricula + "';");
                conexion.updateBd("UPDATE vehiculos SET num_ocupantes = 0 WHERE matricula = '" + matricula + "';");
            } catch (SQLException ex) {
                Logger.getLogger(PnlGestion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }   
    }//GEN-LAST:event_btnBorrarTodoActionPerformed

    private void jtMatriculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtMatriculaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtMatriculaActionPerformed

    private void cbFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFiltroActionPerformed
        // TODO add your handling code here:
        if ((buscarPor == "Por cliente" || buscarPor == "Por vehiculo") && cbFiltro.getSelectedItem()== "Por matricula") {
            jtMatricula.setVisible(true);
        }
        else {
            jtMatricula.setVisible(false);
            jtMatricula.setText("");
        }
    }//GEN-LAST:event_cbFiltroActionPerformed

    private void btnBorrarFilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarFilaActionPerformed
        eliminarFila();
    }//GEN-LAST:event_btnBorrarFilaActionPerformed

    private void btnAniadirPasajeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAniadirPasajeroActionPerformed
        
        if (id_tabla == null || id_tabla.isBlank()) { // No se ha seleccionado ningún vehículo
            JOptionPane.showMessageDialog(this, "No ha seleccionado ningún vehículo");
        }
        else { 
            FrmPasajeros ventanaPasajeros = new FrmPasajeros(id_tabla); // Se abre la ventana para añadir un pasajero
            ventanaPasajeros.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            ventanaPasajeros.setVisible(true);
        }
    }//GEN-LAST:event_btnAniadirPasajeroActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAniadirPasajero;
    private javax.swing.JButton btnBorrarFila;
    private javax.swing.JButton btnBorrarTodo;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JComboBox<String> cbFiltro;
    private javax.swing.JComboBox<String> cbSeleccion;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jtMatricula;
    private javax.swing.JTable tblResult;
    // End of variables declaration//GEN-END:variables

}


