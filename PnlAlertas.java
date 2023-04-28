/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package pantallasSwing;

import clasesJava.Alerta;
import clasesJava.ConexionBBDD;
import java.awt.Color;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*En este panel se pueden añadir nuevas alertas para que el programa las muestre en los días indicados. 
Las alertas que figuran como alertas automáticas en las tablas se generan al ingresar un nuevo vehiculo*/

public class PnlAlertas extends javax.swing.JPanel {
    
    ConexionBBDD conexion = new ConexionBBDD();
    Calendar calendario = Calendar.getInstance();
    DefaultTableModel dtmHoy;
    DefaultTableModel dtmManiana;
    DefaultTableModel dtmSiguientes;
    
    public PnlAlertas() {
        initComponents(); 
        configInicial();    
    }
    
    private void configInicial(){
        //Declaración de variables
        dtmHoy = new DefaultTableModel();
        dtmManiana = new DefaultTableModel();
        dtmSiguientes = new DefaultTableModel();
        String [] cabecera = {"Titulo","Descripción"};
        String [] cabecera2 = {"Titulo","Descripción","Día"};
        Date fechaHoy = calendario.getTime();
        Date fechaManiana = new Date(fechaHoy.getTime() + TimeUnit.DAYS.toMillis(1));
        String fechaHoySQL = conexion.fechaSQL(fechaHoy);
        String fechaManianaSQL = conexion.fechaSQL(fechaManiana);
        
        //Configuración tablas alertas
        pnlNuevaAlerta.setVisible(false);
        tblHoy.setDefaultEditor(Object.class, null);
        tblManiana.setDefaultEditor(Object.class, null);
        tblSiguientes.setDefaultEditor(Object.class, null);
        tblHoy.setRowHeight(20);
        tblManiana.setRowHeight(20);
        tblSiguientes.setRowHeight(20);
        tblHoy.setForeground(Color.red);
        dtmHoy.setColumnIdentifiers(cabecera);
        tblHoy.setModel(dtmHoy);
        dtmHoy.setRowCount(0);
        dtmManiana.setColumnIdentifiers(cabecera);    
        tblManiana.setModel(dtmManiana);
        dtmManiana.setRowCount(0);
        dtmSiguientes.setColumnIdentifiers(cabecera2);
        tblSiguientes.setModel(dtmSiguientes);
        dtmSiguientes.setRowCount(0);
        //Conexión con BD, muestra datos BD en Jtable
        try {
            conexion.selectFromTabla("SELECT titulo, descripcion FROM alertas WHERE dia_alerta LIKE '"+fechaHoySQL+"';", dtmHoy);
            conexion.selectFromTabla("SELECT titulo, descripcion FROM alertas WHERE dia_alerta LIKE '"+fechaManianaSQL+"';", dtmManiana);
            conexion.selectFromTabla("SELECT titulo, descripcion, dia_alerta FROM alertas WHERE dia_alerta > '"+fechaManianaSQL+"';", dtmSiguientes);
        } catch (SQLException ex) {
            Logger.getLogger(PnlAlertas.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "No se han podido recuperar los datos de las alertas.");
        }
    }
    
     public boolean comprobacionDatosAlerta() {
                
        boolean datosCorrectos = true;
        
        tfTituloAlerta.setBackground(Color.white);
        dcDiaAlerta.setBackground(Color.white);

        if (tfTituloAlerta.getText().isBlank()) {
            tfTituloAlerta.setBackground(Color.red);
            datosCorrectos = false;
        }
        
        Date diaAlerta = dcDiaAlerta.getDate();
        
        if (diaAlerta == null) {
            dcDiaAlerta.setBackground(Color.red);
            datosCorrectos = false;
        }else if (diaAlerta.before(calendario.getTime())){
            dcDiaAlerta.setBackground(Color.red);
            datosCorrectos = false;
        }

        return datosCorrectos;
    }
    
    //Método que genera una nueva alerta con los campos que rellena el usuario
    public Alerta nuevaAlerta(){
        Alerta alerta;
        if (comprobacionDatosAlerta()){
            alerta = new Alerta(tfTituloAlerta.getText(),taDescripcionAlerta.getText(),dcDiaAlerta.getDate());
        }else{
            alerta = null;
        }
        return alerta;
    }
    
    public void eliminarAlerta(JTable tabla, DefaultTableModel dtm){
        int filaSeleccionada = tabla.getSelectedRow();
            String id = tabla.getValueAt(filaSeleccionada, 0).toString();
            Object[] opciones = { "ACEPTAR", "CANCELAR" };
            int opcion = JOptionPane.showOptionDialog(null, "Se eliminarán los datos", "Confirmación",JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opciones, opciones[0]);
            if (opcion == 0){ //ACEPTAR
                String sentenciaSQL = "DELETE FROM alertas WHERE "+dtm.getColumnName(0)+ "=?;";
                    try {  
                        conexion.updateBd(sentenciaSQL,id);
                    } catch (SQLException ex) {
                        Logger.getLogger(PnlAlertas.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(this, "No se ha podido eliminar la alerta.");
                    }
            }
    }
    
    public void eliminarFila(){
        if (tblHoy.getSelectedRow() < 0 & tblManiana.getSelectedRow() < 0 & tblSiguientes.getSelectedRow() < 0){
            JOptionPane.showMessageDialog(this,"Selecciona una alerta");
        }
        else{
            if (tblHoy.getSelectedRow() < 0);
            else eliminarAlerta(tblHoy, dtmHoy);
            if (tblManiana.getSelectedRow() < 0);
            else eliminarAlerta(tblManiana, dtmManiana);
            if (tblSiguientes.getSelectedRow() < 0);
            else eliminarAlerta(tblSiguientes, dtmSiguientes);
        }
        
              
    } 

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblHoy = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblManiana = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblSiguientes = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        btnNuevaAlerta = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        pnlNuevaAlerta = new javax.swing.JPanel();
        tfTituloAlerta = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        taDescripcionAlerta = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        dcDiaAlerta = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(0, 153, 204));

        jPanel2.setBackground(new java.awt.Color(0, 153, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Rockwell Nova", 1, 12)); // NOI18N
        jLabel9.setText("Hoy:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 43, 51, -1));

        jLabel12.setFont(new java.awt.Font("Rockwell Nova", 1, 12)); // NOI18N
        jLabel12.setText("Próximas:");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 470, 86, -1));

        tblHoy.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane8.setViewportView(tblHoy);

        jPanel2.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 72, 477, 180));

        jLabel13.setFont(new java.awt.Font("Rockwell Nova", 1, 12)); // NOI18N
        jLabel13.setText("Mañana:");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, 77, -1));

        tblManiana.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane7.setViewportView(tblManiana);

        jPanel2.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, 477, 157));

        tblSiguientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane6.setViewportView(tblSiguientes);

        jPanel2.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 500, 477, 157));

        jLabel8.setText(" ");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 690, -1, -1));

        jScrollPane4.setViewportView(jPanel2);

        btnNuevaAlerta.setBackground(new java.awt.Color(0, 0, 0));
        btnNuevaAlerta.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        btnNuevaAlerta.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevaAlerta.setText("Nueva alerta");
        btnNuevaAlerta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevaAlerta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaAlertaActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Rockwell Nova", 1, 14)); // NOI18N
        jLabel14.setText("ALERTAS");

        pnlNuevaAlerta.setBackground(new java.awt.Color(0, 153, 204));
        pnlNuevaAlerta.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        pnlNuevaAlerta.add(tfTituloAlerta, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 41, 250, -1));

        taDescripcionAlerta.setColumns(20);
        taDescripcionAlerta.setRows(5);
        jScrollPane5.setViewportView(taDescripcionAlerta);

        pnlNuevaAlerta.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 250, 144));

        jLabel7.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        jLabel7.setText("Título:");
        pnlNuevaAlerta.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 12, -1, -1));

        jLabel10.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        jLabel10.setText("Día aviso:");
        pnlNuevaAlerta.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 75, -1, -1));

        jLabel11.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        jLabel11.setText("Descripción:");
        pnlNuevaAlerta.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, -1, -1));

        btnGuardar.setBackground(new java.awt.Color(0, 0, 0));
        btnGuardar.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        pnlNuevaAlerta.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 400, 96, 30));
        pnlNuevaAlerta.add(dcDiaAlerta, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 240, -1));

        jButton1.setBackground(new java.awt.Color(255, 0, 51));
        jButton1.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Eliminar alerta");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(625, 625, 625)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlNuevaAlerta, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(btnNuevaAlerta, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(34, 34, 34)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel14)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNuevaAlerta, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addComponent(pnlNuevaAlerta, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        Alerta alerta = nuevaAlerta();
        
        if(alerta != null) { // Si los datos son correctos
        
        try {
                conexion.updateBd(alerta.toSQL());
                JOptionPane.showMessageDialog(this,"Se ha añadido la nueva alerta");
                pnlNuevaAlerta.setVisible(false);
                tfTituloAlerta.setText(null);
                dcDiaAlerta.setDate(null);
                taDescripcionAlerta.setText(null);
                configInicial();
            } catch (SQLException ex) {
                if (ex.getMessage().toLowerCase().contains("duplicate")) { // Si el mensaje es entrada duplicada, es porque se repite la matrícula
                    JOptionPane.showMessageDialog(this, "Ya existe una alerta con ese título");
                }
                else {
                    JOptionPane.showMessageDialog(this, "No se ha podido añadir la alerta");
                }
            }
        } else{
            JOptionPane.showMessageDialog(this, "Compruebe los datos.");
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnNuevaAlertaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaAlertaActionPerformed
        if (pnlNuevaAlerta.isVisible()) pnlNuevaAlerta.setVisible(false);
        else pnlNuevaAlerta.setVisible(true);  
    }//GEN-LAST:event_btnNuevaAlertaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        eliminarFila();
        configInicial();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevaAlerta;
    private com.toedter.calendar.JDateChooser dcDiaAlerta;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JPanel pnlNuevaAlerta;
    private javax.swing.JTextArea taDescripcionAlerta;
    private javax.swing.JTable tblHoy;
    private javax.swing.JTable tblManiana;
    private javax.swing.JTable tblSiguientes;
    private javax.swing.JTextField tfTituloAlerta;
    // End of variables declaration//GEN-END:variables
}
