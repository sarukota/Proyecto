/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package pantallasSwing;

import clasesJava.ConexionBBDD;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class PnlInfoParcelas extends javax.swing.JPanel {
    
    ConexionBBDD conexion = new ConexionBBDD();

    public PnlInfoParcelas(int numParcela) {
        initComponents();
        setSize(350,450); //Da el tamaño a la ventana
        setLocation(200,0);
        try {
            datosPanel(numParcela);
        } catch (SQLException ex) {
            Logger.getLogger(PnlInfoParcelas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void datosPanel(int numParcela) throws SQLException{
        //Relleno textFields
        JTextField[] tfVehiculo = {tfMatricula,tfMarca,tfModelo,tfCheckIn,tfCheckOut};
        conexion.selectFromTabla("SELECT matricula, marca, modelo, check_in, check_out FROM vehiculos WHERE num_parcela = '"+numParcela+"';",tfVehiculo);
        //Relleno tabla pasajeros
        String [] cabecera = {"DNI","Nombre","Apellido"};
        DefaultTableModel dtmResult = new DefaultTableModel();
        dtmResult = SetCabeceras(cabecera);
        conexion.selectFromTabla("SELECT dni,nombre,apellido1 FROM clientes WHERE matricula = '"+tfMatricula.getText()+"';",dtmResult);
        
     /*   
        Object[][] consultaClientes = conexion.arrayConsulta("SELECT dni FROM clientes WHERE matricula = '"+tfMatricula.getText()+"';");
        JTextField tf = new JTextField();
        for (int i = 0; i < consultaClientes.length; i++) {
            for (int j = 0; j < consultaClientes[i].length; j++) {
                tf.setText(consultaClientes[i][j].toString());
                pnlPasajeros.add(tf);
                pnlPasajeros.revalidate();
                pnlPasajeros.repaint();
            }
        }*/
        
    }
    
    public DefaultTableModel SetCabeceras (String[] cabecera){
        DefaultTableModel dtmResult = new DefaultTableModel();
        dtmResult.setColumnIdentifiers(cabecera);
        tblPasajeros.setModel(dtmResult);
        dtmResult.setRowCount(0);
        return dtmResult;
    }
    /*
    public void datosPanel2(int numParcela){
        JTextField[] tfVehiculo = {tfMatricula,tfMarca,tfModelo,tfCheckIn,tfCheckOut};
        JTextField[][] tfClientes;
        System.out.println("numero parcela desde panel parcelas = "+numParcela);
        try {
            conexion.selectFromTabla("SELECT matricula, marca, modelo, check_in, check_out FROM vehiculos WHERE num_parcela = '"+numParcela+"';",tfVehiculo);
            String cuentaClientes = Arrays.toString(conexion.selectFromTabla("SELECT COUNT(*) FROM clientes WHERE matricula = '"+tfMatricula.getText()+"';",1));
            char caracter1 = cuentaClientes.charAt(1);
            System.out.println("CuentaClientes : "+Character.getNumericValue(caracter1));
            tfClientes = new JTextField [Character.getNumericValue(caracter1)];
            //ERROR: el select devuelve datos de diferentes filas: solucion modificar select para que me devuelva los datos correctos y asi ahorrar codigo
            Object[][] consultaClientes = conexion.arrayConsulta("SELECT dni FROM clientes WHERE matricula = '"+tfMatricula.getText()+"';");
            //String[][] objAString = new String[consultaClientes.length][consultaClientes[0].length];
            for (int i = 0; i < consultaClientes.length; i++) {
                for (int j = 0; j < consultaClientes[i].length; j++) {
                    tfClientes[i].setText(consultaClientes[i][j].toString());
                }
            }
            for (int i = 0; i < tfClientes.length; i++) {
                pnlPasajeros.add(tfClientes[i]);
            }
            pnlPasajeros.revalidate();
            pnlPasajeros.repaint();

        } catch (SQLException ex) {
            Logger.getLogger(PnlInfoParcelas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }*/

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tfMatricula = new javax.swing.JTextField();
        tfModelo = new javax.swing.JTextField();
        tfMarca = new javax.swing.JTextField();
        tfCheckIn = new javax.swing.JTextField();
        tfCheckOut = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPasajeros = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        tfMatricula.setEditable(false);
        tfMatricula.setBackground(new java.awt.Color(255, 255, 255));
        tfMatricula.setFont(new java.awt.Font("Rockwell Nova", 1, 15)); // NOI18N
        tfMatricula.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfMatricula.setBorder(null);
        tfMatricula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfMatriculaActionPerformed(evt);
            }
        });

        tfModelo.setEditable(false);
        tfModelo.setBackground(new java.awt.Color(255, 255, 255));
        tfModelo.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        tfModelo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfModelo.setBorder(null);

        tfMarca.setEditable(false);
        tfMarca.setBackground(new java.awt.Color(255, 255, 255));
        tfMarca.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        tfMarca.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfMarca.setBorder(null);

        tfCheckIn.setEditable(false);
        tfCheckIn.setBackground(new java.awt.Color(255, 255, 255));
        tfCheckIn.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        tfCheckIn.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfCheckIn.setBorder(null);

        tfCheckOut.setEditable(false);
        tfCheckOut.setBackground(new java.awt.Color(255, 255, 255));
        tfCheckOut.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        tfCheckOut.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfCheckOut.setBorder(null);
        tfCheckOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfCheckOutActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Rockwell Nova", 1, 14)); // NOI18N
        jLabel1.setText("Pasajeros");

        tblPasajeros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblPasajeros);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(tfMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfCheckIn)
                                    .addComponent(tfMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfModelo)
                                    .addComponent(tfCheckOut, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(23, 23, 23))))
            .addGroup(layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(tfMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfCheckIn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfCheckOut, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tfMatriculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfMatriculaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfMatriculaActionPerformed

    private void tfCheckOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfCheckOutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfCheckOutActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPasajeros;
    private javax.swing.JTextField tfCheckIn;
    private javax.swing.JTextField tfCheckOut;
    private javax.swing.JTextField tfMarca;
    private javax.swing.JTextField tfMatricula;
    private javax.swing.JTextField tfModelo;
    // End of variables declaration//GEN-END:variables
}
