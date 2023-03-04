/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pantallasSwing;

import clasesJava.Cliente;
import clasesJava.Vehiculo;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author sarac
 */
public class PantallaClientes extends javax.swing.JFrame {
    
    PantallaVehiculos pv = new PantallaVehiculos();
    int contador = 1; //Contador para cambiar label de clientes
    String matricula = pv.matricula;

    public PantallaClientes() {
        initComponents();
        setSize(480,600); //Da el tamaño a la ventana
        setLocationRelativeTo(null);//Centrar el JFrame
        setResizable(false);//No permite redimensionar la ventana
        ImageIcon fondo = new ImageIcon("src/main/java/imagenes/wallpaperVan.png");
        JLabel lblFondo = new JLabel(); //Añade una etiqueta para poner la imagen de fondo
        lblFondo.setSize(480,600);
        lblFondo.setIcon(fondo);
        pnlCliente1.add(lblFondo);
    }
    
    public Cliente crearCliente(){
        
        Cliente cliente = new Cliente();
        
        cliente.setDni(tfDNI.getText());
        cliente.setNacionalidad(tfNacionalidad.getText());
        cliente.setNombre(tfNombre.getText());
        cliente.setApellido1(tfApellido1.getText());
        cliente.setApellido2(tfApellido2.getText());
        cliente.setFechaNac(dcFechaNac.getDate());
        cliente.setTelefono(Integer.parseInt(tfTelefono.getText()));
        cliente.setMail(tfMail.getText());
                
        return cliente;
    }
    
    //Método para convertir una fecha en formato java.util.Date a un String válido para "date" en MySQL
    public String fechaSQL (java.util.Date fechaJava){
        DateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
        String fechaSQL = dateFormat.format(fechaJava);
        return fechaSQL;
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlCliente1 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTitulo.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblTitulo.setText("Inserte los datos de los clientes:");

        lblDNI.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblDNI.setText("DNI/ID:");

        tfDNI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfDNIActionPerformed(evt);
            }
        });

        lblNacionalidad.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblNacionalidad.setText("Nacionalidad:");

        lblNombre.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblNombre.setText("Nombre:");

        lblApellido1.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblApellido1.setText("Apellido 1:");

        lblApellido2.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblApellido2.setText("Apellido 2:");

        lblFechaNac.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblFechaNac.setText("Fecha nac:");

        lblTelefono.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblTelefono.setText("Teléfono:");

        lblMail.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblMail.setText("E-Mail:");

        btnGuardarCliente.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        btnGuardarCliente.setText("Guardar cliente");
        btnGuardarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnGuardarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClienteActionPerformed(evt);
            }
        });

        lblCliente.setFont(new java.awt.Font("Rockwell Nova", 1, 14)); // NOI18N
        lblCliente.setText("Cliente 1:");

        javax.swing.GroupLayout pnlCliente1Layout = new javax.swing.GroupLayout(pnlCliente1);
        pnlCliente1.setLayout(pnlCliente1Layout);
        pnlCliente1Layout.setHorizontalGroup(
            pnlCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCliente1Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(pnlCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCliente1Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(lblTitulo))
                    .addGroup(pnlCliente1Layout.createSequentialGroup()
                        .addGroup(pnlCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCliente1Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(lblCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlCliente1Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(pnlCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblApellido1)
                                    .addComponent(lblNombre)
                                    .addComponent(lblApellido2)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblFechaNac)
                                .addComponent(lblNacionalidad)
                                .addComponent(lblTelefono)
                                .addComponent(lblMail)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dcFechaNac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tfNacionalidad)
                            .addGroup(pnlCliente1Layout.createSequentialGroup()
                                .addComponent(lblDNI)
                                .addGap(18, 18, 18)
                                .addComponent(tfDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tfNombre, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tfApellido1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tfApellido2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tfTelefono)
                            .addComponent(tfMail, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlCliente1Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(btnGuardarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        pnlCliente1Layout.setVerticalGroup(
            pnlCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCliente1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lblTitulo)
                .addGap(34, 34, 34)
                .addGroup(pnlCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDNI)
                    .addComponent(lblCliente)
                    .addComponent(tfDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(pnlCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombre))
                .addGap(18, 18, 18)
                .addGroup(pnlCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfApellido1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblApellido1))
                .addGap(18, 18, 18)
                .addGroup(pnlCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfApellido2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblApellido2))
                .addGap(18, 18, 18)
                .addGroup(pnlCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfNacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNacionalidad))
                .addGap(18, 18, 18)
                .addGroup(pnlCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dcFechaNac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFechaNac))
                .addGap(18, 18, 18)
                .addGroup(pnlCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTelefono)
                    .addComponent(tfTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMail)
                    .addComponent(tfMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(btnGuardarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlCliente1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlCliente1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfDNIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfDNIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfDNIActionPerformed

    private void btnGuardarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClienteActionPerformed
        //Al pulsa boton guardar cliente, se instancia un nuevo cliente con los datos de los campos
        //se añade al set ClientesEnVehiculo y este nuevo set se añade al objeto vehículo
        //o hago una consulta a la BD para obtener nº de ocupantes y con eso relleno variable
        
        int contadorLblClientes = contador+1;
        lblCliente.setText("Cliente "+contadorLblClientes);
        System.out.println("matricula: "+matricula);
        if (contador >= pv.contadorMax){
            pnlCliente1.setVisible(false);
            pv.setVisible(true);
            JOptionPane.showMessageDialog(null, "Todos los clientes del vehículo " +matricula+" han sido guardados" );
            pv.setVisible(true);
        }
        contador ++;

        Cliente cliente1 = crearCliente();
        System.out.println(cliente1.toString());
        Set<Cliente>clientesEnVehiculo = new HashSet<Cliente>();
        clientesEnVehiculo.add(cliente1);
        System.out.println(clientesEnVehiculo.toString());

        //Limpio todos los campos de la interfaz
        tfDNI.setText(null);
        tfNacionalidad.setText(null);
        tfNombre.setText(null);
        tfApellido1.setText(null);
        tfApellido2.setText(null);
        dcFechaNac.setDate(null);
        tfTelefono.setText(null);
        tfMail.setText(null);

    }//GEN-LAST:event_btnGuardarClienteActionPerformed

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
            java.util.logging.Logger.getLogger(PantallaClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaClientes().setVisible(true);
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
    private javax.swing.JPanel pnlCliente1;
    private javax.swing.JTextField tfApellido1;
    private javax.swing.JTextField tfApellido2;
    private javax.swing.JTextField tfDNI;
    private javax.swing.JTextField tfMail;
    private javax.swing.JTextField tfNacionalidad;
    private javax.swing.JTextField tfNombre;
    private javax.swing.JTextField tfTelefono;
    // End of variables declaration//GEN-END:variables
}
