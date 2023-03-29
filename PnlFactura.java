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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class PnlFactura extends javax.swing.JPanel {
    
    ConexionBBDD conexion = new ConexionBBDD();

    public PnlFactura() {
        initComponents();
        setSize(1300,700); //Da el tamaño a la ventana
        setLocation(0,0);
        
        Calendar calendario = Calendar.getInstance();
        Date fechaHoy = calendario.getTime();
        try {
            String[] datosCBMatriculas;
            int numRegistros = conexion.contarFilas("SELECT COUNT(*) FROM vehiculos;");
            datosCBMatriculas = conexion.selectFromTabla("select matricula from vehiculos where check_out >= '"+conexion.fechaSQL(fechaHoy)+"';",numRegistros);
            JcbMatriculas.removeAllItems();
            for (int i = 0; i < (datosCBMatriculas.length); i++) {
                JcbMatriculas.addItem(datosCBMatriculas[i]);   
            }
            JcbMatriculas.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0){
                aniadirDatos();
            }
        });
            
 
        } catch (SQLException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void aniadirDatos(){
        try {
            JTextField[]fechas = new JTextField[2];
            fechas[0]=JtfDe;
            fechas[1]=JtfA;
            conexion.selectFromTabla("SELECT check_in, check_out from vehiculos WHERE matricula = '"+JcbMatriculas.getSelectedItem().toString()+"';",fechas);
            String[] datosCBServicios;
            int registrosServicios = conexion.contarFilas("SELECT COUNT(*) FROM servicios;");
            datosCBServicios = conexion.selectFromTabla("select nombre from servicios;",registrosServicios);
            jCBServContratados.removeAllItems();
            for (int i = 0; i < (datosCBServicios.length); i++) {
                jCBServContratados.addItem(datosCBServicios[i]); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(PnlFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JcbMatriculas = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        JlblNumNoches = new javax.swing.JLabel();
        JlblA = new javax.swing.JLabel();
        JlblDe = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        JTblServContratados = new javax.swing.JTable();
        jCBServContratados = new javax.swing.JComboBox<>();
        JbtnMas = new javax.swing.JButton();
        JlblContadorServ = new javax.swing.JLabel();
        jBtnMenos = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jBtnAniadir = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel25 = new javax.swing.JLabel();
        JtfA = new javax.swing.JTextField();
        JtfDe = new javax.swing.JTextField();

        setBackground(new java.awt.Color(0, 153, 204));

        jLabel16.setFont(new java.awt.Font("Rockwell Nova", 0, 18)); // NOI18N
        jLabel16.setText("Generar factura de:");

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JlblNumNoches.setText("Nº noches: ");
        jPanel3.add(JlblNumNoches, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 84, -1, -1));

        JlblA.setText("A:");
        jPanel3.add(JlblA, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, 39, 29));

        JlblDe.setText("De:");
        jPanel3.add(JlblDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 51, 28));

        jLabel20.setText("Servicios contratados:");
        jPanel3.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 128, 136, -1));

        JTblServContratados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Servicio", "Precio", "Nº usos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane9.setViewportView(JTblServContratados);

        jPanel3.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 205, 385, 136));

        jPanel3.add(jCBServContratados, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 117, 237, 30));

        JbtnMas.setText("+");
        JbtnMas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JbtnMasActionPerformed(evt);
            }
        });
        jPanel3.add(JbtnMas, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 160, 40, 30));

        JlblContadorServ.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JlblContadorServ.setText("1");
        jPanel3.add(JlblContadorServ, new org.netbeans.lib.awtextra.AbsoluteConstraints(244, 166, 30, 20));

        jBtnMenos.setText("-");
        jBtnMenos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnMenosActionPerformed(evt);
            }
        });
        jPanel3.add(jBtnMenos, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, 40, 30));

        jLabel22.setText("Nº de usos:");
        jPanel3.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 168, 89, -1));

        jBtnAniadir.setText("Añadir");
        jBtnAniadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAniadirActionPerformed(evt);
            }
        });
        jPanel3.add(jBtnAniadir, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 160, 99, 30));

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel3.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 359, -1, -1));

        jLabel23.setText("Descuento:");
        jPanel3.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 362, -1, -1));

        jLabel24.setText("Total sin IVA:");
        jPanel3.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 399, 126, -1));

        jRadioButton1.setText("€");
        jPanel3.add(jRadioButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(217, 360, -1, -1));

        jRadioButton2.setText("%");
        jPanel3.add(jRadioButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(264, 360, -1, -1));

        jLabel25.setText("Total con IVA:");
        jPanel3.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(275, 399, 126, -1));
        jPanel3.add(JtfA, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, 150, 30));

        JtfDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JtfDeActionPerformed(evt);
            }
        });
        jPanel3.add(JtfDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 140, 29));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(427, 427, 427)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JcbMatriculas, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(282, 282, 282)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(530, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JcbMatriculas, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(99, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void JbtnMasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JbtnMasActionPerformed
        int contadorServ = Integer.parseInt(JlblContadorServ.getText());
        JlblContadorServ.setText(String.valueOf(++contadorServ));
    }//GEN-LAST:event_JbtnMasActionPerformed

    private void jBtnMenosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnMenosActionPerformed
        int contadorServ = Integer.parseInt(JlblContadorServ.getText());
        JlblContadorServ.setText(String.valueOf(--contadorServ));
    }//GEN-LAST:event_jBtnMenosActionPerformed

    private void jBtnAniadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAniadirActionPerformed
        DefaultTableModel dtm = new DefaultTableModel();
        Object [] datos = new Object[3];
        String [] datosServ = new String[2];
        try {
            datosServ = conexion.selectFromTabla("SELECT precio FROM servicios where nombre = '"+jCBServContratados.getSelectedItem().toString()+"';",1);
            System.out.println("se ha hecho el select y obtenido "+datosServ[1]);
        } catch (SQLException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
        datos[0] = jCBServContratados.getSelectedItem();
        datos[1] = datosServ[1];
        datos[2] = JlblContadorServ.getText();
        dtm.addRow(datos);
        JTblServContratados.setModel(dtm);

    }//GEN-LAST:event_jBtnAniadirActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void JtfDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JtfDeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JtfDeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable JTblServContratados;
    private javax.swing.JButton JbtnMas;
    private javax.swing.JComboBox<String> JcbMatriculas;
    private javax.swing.JLabel JlblA;
    private javax.swing.JLabel JlblContadorServ;
    private javax.swing.JLabel JlblDe;
    private javax.swing.JLabel JlblNumNoches;
    private javax.swing.JTextField JtfA;
    private javax.swing.JTextField JtfDe;
    private javax.swing.JButton jBtnAniadir;
    private javax.swing.JButton jBtnMenos;
    private javax.swing.JComboBox<String> jCBServContratados;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
