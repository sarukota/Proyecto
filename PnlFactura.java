/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package pantallasSwing;

import clasesJava.ConexionBBDD;
import clasesJava.Interfaz;
import clasesJava.Servicio;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/*Sin terminar aun. Este panel generará la factura de un vehiculo seleccionado 
añadiendo los servicios que este ha utilizado y calculando los días de estancia*/

public class PnlFactura extends javax.swing.JPanel {
    
    ConexionBBDD conexion = new ConexionBBDD();
    ArrayList<Servicio> listaServicios = new ArrayList<>();
    DefaultTableModel dtm = new DefaultTableModel();
    int sumaServicios = 0;

    public PnlFactura() {
        initComponents();
        Calendar calendario = Calendar.getInstance();
        Date fechaHoy = calendario.getTime();
        try {
            String[] datosCBMatriculas;
            datosCBMatriculas = conexion.selectFromTabla("SELECT matricula FROM vehiculos WHERE check_out >= '"+conexion.fechaSQL(fechaHoy)+"';");
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
            conexion.selectFromTabla("SELECT check_in, check_out FROM vehiculos WHERE matricula = '"+JcbMatriculas.getSelectedItem().toString()+"';",fechas);
            String[] datosCBServicios;
            datosCBServicios = conexion.selectFromTabla("SELECT nombre FROM servicios;");
            jCBServContratados.removeAllItems();
            for (int i = 0; i < (datosCBServicios.length); i++) {
                jCBServContratados.addItem(datosCBServicios[i]); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(PnlFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int precioEstancia () throws SQLException{
        int precioNoche = Integer.parseInt(conexion.selectDato("SELECT precio_noche FROM area"));
        int numeroNoches = Integer.parseInt(tfNumNoches.getText());
        int precioEstancia = precioNoche*numeroNoches;
        return precioEstancia;
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
        JtfA = new javax.swing.JTextField();
        JtfDe = new javax.swing.JTextField();
        lblTotal = new javax.swing.JLabel();
        tfNumNoches = new javax.swing.JTextField();

        setBackground(new java.awt.Color(0, 153, 204));

        jLabel16.setFont(new java.awt.Font("Rockwell Nova", 0, 22)); // NOI18N
        jLabel16.setText("Generar factura de:");

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JlblNumNoches.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        JlblNumNoches.setForeground(new java.awt.Color(255, 255, 255));
        JlblNumNoches.setText("Nº noches: ");
        jPanel3.add(JlblNumNoches, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        JlblA.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        JlblA.setForeground(new java.awt.Color(255, 255, 255));
        JlblA.setText("A:");
        jPanel3.add(JlblA, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, 39, 29));

        JlblDe.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        JlblDe.setForeground(new java.awt.Color(255, 255, 255));
        JlblDe.setText("De:");
        jPanel3.add(JlblDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 51, 28));

        jLabel20.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Servicios contratados:");
        jPanel3.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 190, -1));

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

        jPanel3.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 430, 136));

        jPanel3.add(jCBServContratados, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, 237, 30));

        JbtnMas.setText("+");
        JbtnMas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JbtnMasActionPerformed(evt);
            }
        });
        jPanel3.add(JbtnMas, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 200, 40, 30));

        JlblContadorServ.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        JlblContadorServ.setForeground(new java.awt.Color(255, 255, 255));
        JlblContadorServ.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JlblContadorServ.setText("1");
        jPanel3.add(JlblContadorServ, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 200, 30, 20));

        jBtnMenos.setText("-");
        jBtnMenos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnMenosActionPerformed(evt);
            }
        });
        jPanel3.add(jBtnMenos, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 200, 40, 30));

        jLabel22.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Nº de usos:");
        jPanel3.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, 89, -1));

        jBtnAniadir.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        jBtnAniadir.setText("Añadir");
        jBtnAniadir.setBorder(null);
        jBtnAniadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAniadirActionPerformed(evt);
            }
        });
        jPanel3.add(jBtnAniadir, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 200, 99, 30));

        jTextField1.setMaximumSize(new java.awt.Dimension(64, 22));
        jPanel3.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 430, 60, -1));

        jLabel23.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Descuento:");
        jPanel3.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 430, -1, -1));

        jLabel24.setFont(new java.awt.Font("Rockwell Nova", 0, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Total a pagar:");
        jPanel3.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 480, 140, -1));

        jRadioButton1.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        jRadioButton1.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton1.setText("€");
        jPanel3.add(jRadioButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 430, -1, -1));

        jRadioButton2.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        jRadioButton2.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton2.setText("%");
        jPanel3.add(jRadioButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 430, -1, -1));
        jPanel3.add(JtfA, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, 150, 30));
        jPanel3.add(JtfDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 140, 29));

        lblTotal.setFont(new java.awt.Font("Rockwell Nova", 0, 18)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 490, 70, 20));

        tfNumNoches.setText("1");
        jPanel3.add(tfNumNoches, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, 60, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(403, 403, 403)
                        .addComponent(jLabel16))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(428, 428, 428)
                        .addComponent(JcbMatriculas, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(272, 272, 272)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(504, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(JcbMatriculas, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE))
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
        String [] cabecera = {"Servicio","Precio","Nº usos" };
        dtm.setColumnIdentifiers(cabecera);
        Object [] datos = new Object[3];
        int precio = 0;
        try {
            precio = Integer.parseInt(conexion.selectDato("SELECT precio FROM servicios where nombre = '"+jCBServContratados.getSelectedItem().toString()+"';"));    
        } catch (SQLException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
        datos[0] = jCBServContratados.getSelectedItem();
        datos[1] = precio;
        datos[2] = JlblContadorServ.getText();
        dtm.addRow(datos);
        JTblServContratados.setModel(dtm);
        int precioServicios = Integer.parseInt(datos[1].toString())*Integer.parseInt(datos[2].toString());
        sumaServicios = (sumaServicios + precioServicios);
        int total = 0;
        try {
            total = sumaServicios + precioEstancia();
        } catch (SQLException ex) {
            Logger.getLogger(PnlFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
        lblTotal.setText(total+" €");
    }//GEN-LAST:event_jBtnAniadirActionPerformed


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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTextField tfNumNoches;
    // End of variables declaration//GEN-END:variables
}
