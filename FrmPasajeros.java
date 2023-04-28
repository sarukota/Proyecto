/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pantallasSwing;

import clasesJava.Cliente;
import clasesJava.ConexionBBDD;
import java.awt.Color;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;


public class FrmPasajeros extends javax.swing.JFrame {
    
    String matricula;
    ConexionBBDD conexion = new ConexionBBDD();

    public FrmPasajeros() {
        initComponents();
    }
    
    public FrmPasajeros(String id) {
        initComponents();
        this.matricula = id;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlClientes = new javax.swing.JPanel();
        lblDNI = new javax.swing.JLabel();
        tfDNI = new javax.swing.JTextField();
        lblNacionalidad = new javax.swing.JLabel();
        tfNacionalidad = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        tfNombre = new javax.swing.JTextField();
        lblApellido1 = new javax.swing.JLabel();
        tfApellido1 = new javax.swing.JTextField();
        tfApellido2 = new javax.swing.JTextField();
        lblApellido2 = new javax.swing.JLabel();
        lblFechaNac = new javax.swing.JLabel();
        dcFechaNac = new com.toedter.calendar.JDateChooser();
        lblTelefono = new javax.swing.JLabel();
        tfTelefono = new javax.swing.JTextField();
        lblMail = new javax.swing.JLabel();
        tfMail = new javax.swing.JTextField();
        btnGuardarCliente = new javax.swing.JButton();
        lblCliente = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        pnlClientes.setBackground(new java.awt.Color(0, 153, 204));
        pnlClientes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDNI.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblDNI.setText("DNI/ID:");
        pnlClientes.add(lblDNI, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, -1, -1));

        tfDNI.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        tfDNI.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pnlClientes.add(tfDNI, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 90, 140, 30));

        lblNacionalidad.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblNacionalidad.setText("Nacionalidad:");
        pnlClientes.add(lblNacionalidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, -1, -1));

        tfNacionalidad.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        tfNacionalidad.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pnlClientes.add(tfNacionalidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 290, 230, 30));

        lblNombre.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblNombre.setText("Nombre:");
        pnlClientes.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, -1, -1));

        tfNombre.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        tfNombre.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pnlClientes.add(tfNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 140, 230, 30));

        lblApellido1.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblApellido1.setText("Apellido 1:");
        pnlClientes.add(lblApellido1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, -1, -1));

        tfApellido1.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        tfApellido1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pnlClientes.add(tfApellido1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 190, 230, 30));

        tfApellido2.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        tfApellido2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pnlClientes.add(tfApellido2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 240, 230, 30));

        lblApellido2.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblApellido2.setText("Apellido 2:");
        pnlClientes.add(lblApellido2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, -1, -1));

        lblFechaNac.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblFechaNac.setText("Fecha nac:");
        pnlClientes.add(lblFechaNac, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 350, -1, -1));

        dcFechaNac.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pnlClientes.add(dcFechaNac, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 340, 230, 30));

        lblTelefono.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblTelefono.setText("Teléfono:");
        pnlClientes.add(lblTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 400, -1, -1));

        tfTelefono.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        tfTelefono.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pnlClientes.add(tfTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 390, 230, 30));

        lblMail.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblMail.setText("E-Mail:");
        pnlClientes.add(lblMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 450, -1, -1));

        tfMail.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        tfMail.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pnlClientes.add(tfMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 440, 230, 30));

        btnGuardarCliente.setBackground(new java.awt.Color(0, 0, 0));
        btnGuardarCliente.setFont(new java.awt.Font("Rockwell Nova", 1, 16)); // NOI18N
        btnGuardarCliente.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarCliente.setText("Guardar cliente");
        btnGuardarCliente.setBorder(null);
        btnGuardarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClienteActionPerformed(evt);
            }
        });
        pnlClientes.add(btnGuardarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 550, 184, 40));

        lblCliente.setFont(new java.awt.Font("Rockwell Nova", 1, 16)); // NOI18N
        lblCliente.setText("Cliente:");
        pnlClientes.add(lblCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, 100, -1));

        lblTitulo.setFont(new java.awt.Font("Rockwell Nova", 0, 18)); // NOI18N
        lblTitulo.setText("Inserta los datos de los clientes:");
        pnlClientes.add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    //Metodo para comprobar los campos
     public boolean comprobacionDatosCliente() {
                
        boolean datosCorrectos = true;
        
        tfDNI.setBackground(Color.white);
        tfNombre.setBackground(Color.white);
        tfApellido1.setBackground(Color.white);
        tfTelefono.setBackground(Color.white);
        
        //Se comprueba si campo DNI esta vacio
        if (tfDNI.getText().isBlank()) {
            tfDNI.setBackground(Color.red);
            datosCorrectos = false;
        }
        //Se comprueba si campo nombre esta vacio
        if (tfNombre.getText().isBlank()) {
            tfNombre.setBackground(Color.red);
            datosCorrectos = false;
        }
        //Se comprueba si campo Apellido1 esta vacio
        if (tfApellido1.getText().isBlank()) {
            tfApellido1.setBackground(Color.red);
            datosCorrectos = false;
        }
        //Se comprueba si campo telefono esta vacio
        if (tfTelefono.getText().isBlank()) {
            tfTelefono.setBackground(Color.red);
            datosCorrectos = false;
        }
        //Se comprueba que en campo telefono solo se puedan introducir números
        Pattern patternInteger = Pattern.compile("\\d+");
        
        if (!patternInteger.matcher(tfTelefono.getText()).matches()) {
            tfTelefono.setBackground(Color.red);
            datosCorrectos = false;
        }
        
        return datosCorrectos;
    }
    
    public Cliente crearCliente(){

        Cliente cliente = new Cliente();
        
        if (comprobacionDatosCliente()) {
            cliente.setDni(tfDNI.getText());
            cliente.setNacionalidad(tfNacionalidad.getText());
            cliente.setNombre(tfNombre.getText());
            cliente.setApellido1(tfApellido1.getText());
            cliente.setApellido2(tfApellido2.getText());
            cliente.setFechaNac(dcFechaNac.getDate());
            cliente.setTelefono(Integer.parseInt(tfTelefono.getText()));
            cliente.setMail(tfMail.getText());
            cliente.setMatriculaAuto(matricula);
        }
        else {
            cliente = null;
        }

        return cliente;
    }
    
    private void btnGuardarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClienteActionPerformed

        Cliente cliente = crearCliente();

        if (cliente != null) { // Si los datos son válidos
            try {
                conexion.updateBd(cliente.toSQL());
                JOptionPane.showMessageDialog(this, "Cliente añadido.");

                String num_ocupantes = conexion.selectDato("SELECT num_ocupantes FROM vehiculos WHERE matricula = '" + matricula + "';");
                int num_ocupantes_nuevo = Integer.parseInt(num_ocupantes)  + 1;
                conexion.updateBd("UPDATE vehiculos SET num_ocupantes=" + num_ocupantes_nuevo + " WHERE matricula='" + matricula + "';");

                this.dispose(); // Cerrar ventana
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "El cliente no ha podido añadirse.");
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "Compruebe los campos.");
        }
                
        
    }//GEN-LAST:event_btnGuardarClienteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmPasajeros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPasajeros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPasajeros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPasajeros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPasajeros().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardarCliente;
    private com.toedter.calendar.JDateChooser dcFechaNac;
    private javax.swing.JLabel lblApellido1;
    private javax.swing.JLabel lblApellido2;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblDNI;
    private javax.swing.JLabel lblFechaNac;
    private javax.swing.JLabel lblMail;
    private javax.swing.JLabel lblNacionalidad;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlClientes;
    private javax.swing.JTextField tfApellido1;
    private javax.swing.JTextField tfApellido2;
    private javax.swing.JTextField tfDNI;
    private javax.swing.JTextField tfMail;
    private javax.swing.JTextField tfNacionalidad;
    private javax.swing.JTextField tfNombre;
    private javax.swing.JTextField tfTelefono;
    // End of variables declaration//GEN-END:variables
}
