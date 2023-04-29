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

/*Este panel generará la factura de un vehiculo seleccionado 
añadiendo los servicios que este ha utilizado y calculando los días de estancia*/

public class PnlFactura extends javax.swing.JPanel {
    
    ConexionBBDD conexion = new ConexionBBDD();
    private DefaultTableModel dtm = new DefaultTableModel();
    private int sumaServicios = 0;

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
        btnMas = new javax.swing.JButton();
        JlblContadorServ = new javax.swing.JLabel();
        btnMenos = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        btnAniadir = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        JtfA = new javax.swing.JTextField();
        JtfDe = new javax.swing.JTextField();
        lblTotal = new javax.swing.JLabel();
        tfNumNoches = new javax.swing.JTextField();

        setBackground(new java.awt.Color(0, 153, 204));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        add(JcbMatriculas, new org.netbeans.lib.awtextra.AbsoluteConstraints(426, 74, 175, 36));

        jLabel16.setFont(new java.awt.Font("Rockwell Nova", 0, 22)); // NOI18N
        jLabel16.setText("Generar factura de:");
        add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(403, 26, -1, -1));

        jPanel3.setBackground(new java.awt.Color(0, 153, 204));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JlblNumNoches.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        JlblNumNoches.setForeground(new java.awt.Color(255, 255, 255));
        JlblNumNoches.setText("Nº noches: ");
        jPanel3.add(JlblNumNoches, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        JlblA.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        JlblA.setForeground(new java.awt.Color(255, 255, 255));
        JlblA.setText("A:");
        jPanel3.add(JlblA, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, 39, 29));

        JlblDe.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        JlblDe.setForeground(new java.awt.Color(255, 255, 255));
        JlblDe.setText("De:");
        jPanel3.add(JlblDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 51, 28));

        jLabel20.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
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

        jCBServContratados.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        jPanel3.add(jCBServContratados, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, 237, 30));

        btnMas.setText("+");
        btnMas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMasActionPerformed(evt);
            }
        });
        jPanel3.add(btnMas, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 200, 40, 30));

        JlblContadorServ.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        JlblContadorServ.setForeground(new java.awt.Color(255, 255, 255));
        JlblContadorServ.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JlblContadorServ.setText("1");
        jPanel3.add(JlblContadorServ, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 200, 40, 30));

        btnMenos.setText("-");
        btnMenos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenosActionPerformed(evt);
            }
        });
        jPanel3.add(btnMenos, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, 40, 30));

        jLabel22.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Nº de usos:");
        jPanel3.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 120, -1));

        btnAniadir.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        btnAniadir.setText("Añadir");
        btnAniadir.setBorder(null);
        btnAniadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAniadirActionPerformed(evt);
            }
        });
        jPanel3.add(btnAniadir, new org.netbeans.lib.awtextra.AbsoluteConstraints(369, 200, 110, 30));

        jLabel24.setFont(new java.awt.Font("Rockwell Nova", 0, 20)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Total a pagar:");
        jPanel3.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 460, 140, -1));

        JtfA.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        jPanel3.add(JtfA, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, 150, 30));

        JtfDe.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        jPanel3.add(JtfDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 140, 29));

        lblTotal.setFont(new java.awt.Font("Rockwell Nova", 0, 20)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 450, 80, 40));

        tfNumNoches.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        tfNumNoches.setText("1");
        jPanel3.add(tfNumNoches, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, 60, 30));

        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(259, 131, 540, 569));
    }// </editor-fold>//GEN-END:initComponents

    private void btnAniadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAniadirActionPerformed
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
    }//GEN-LAST:event_btnAniadirActionPerformed

    private void btnMenosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenosActionPerformed
        int contadorServ = Integer.parseInt(JlblContadorServ.getText());
        JlblContadorServ.setText(String.valueOf(--contadorServ));
    }//GEN-LAST:event_btnMenosActionPerformed

    private void btnMasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasActionPerformed
        int contadorServ = Integer.parseInt(JlblContadorServ.getText());
        JlblContadorServ.setText(String.valueOf(++contadorServ));
    }//GEN-LAST:event_btnMasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable JTblServContratados;
    private javax.swing.JComboBox<String> JcbMatriculas;
    private javax.swing.JLabel JlblA;
    private javax.swing.JLabel JlblContadorServ;
    private javax.swing.JLabel JlblDe;
    private javax.swing.JLabel JlblNumNoches;
    private javax.swing.JTextField JtfA;
    private javax.swing.JTextField JtfDe;
    private javax.swing.JButton btnAniadir;
    private javax.swing.JButton btnMas;
    private javax.swing.JButton btnMenos;
    private javax.swing.JComboBox<String> jCBServContratados;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTextField tfNumNoches;
    // End of variables declaration//GEN-END:variables
}
