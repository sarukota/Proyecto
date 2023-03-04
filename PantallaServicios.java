/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pantallasSwing;

import clasesJava.ConexionBBDD;
import clasesJava.Servicio;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

public class PantallaServicios extends javax.swing.JFrame {

    DefaultTableModel dtmServicios = new DefaultTableModel(); //instancio un modelo de tabla
    ArrayList<Servicio> listaServicios = new ArrayList<>(); //Creo un array para añadir servicios
    Object servicioEscogido; //Creo un objeto para determinar el servicio escogido
    ConexionBBDD conexion = new ConexionBBDD();
    
    public PantallaServicios() { //Configuración inicial de la pantalla
        initComponents();
        setSize(480,600); //Da el tamaño a la ventana
        setLocationRelativeTo(null);//Centrar el JFrame
        setResizable(false);//No permite redimensionar la ventana
        ImageIcon fondo = new ImageIcon("src/main/java/imagenes/wallpaperVan.png"); //Ruta imagen de fondo
        JLabel lblFondo = new JLabel(); //Añade una etiqueta para poner la imagen de fondo
        lblFondo.setSize(480,600);
        lblFondo.setIcon(fondo);
        add(lblFondo);
        setModelo();
        tfOtroServicio.setEnabled(false);
        
    }
    
    //metodo para definir el modelo de la tabla
    private void setModelo(){
        String [] cabecera = {"Servicio","Precio" };
        dtmServicios.setColumnIdentifiers(cabecera);
        tblServicios.setModel(dtmServicios);
    }
    
    //método para rellenar el arrayList de servicios, si escojo otro servicio se rellenará con el campo de debajo
    private void llenarArray(){
        String nombre;
        int precio = Integer.parseInt(tfPrecio.getText());
        if (servicioEscogido == "Otro servicio"){
            nombre = tfOtroServicio.getText();
        }else{
            nombre = cbServicios.getSelectedItem().toString();
        }
        Servicio servicio = new Servicio(nombre,precio);
        listaServicios.add(servicio);
    }
    
    //metodo para mostrar los datos de los servicios en la tabla de la interfaz
    private void llenarTabla(){
        Object [] datos = new Object[dtmServicios.getColumnCount()];
        dtmServicios.setRowCount(0);
        for (Servicio servicio : listaServicios) {
            datos[0] = servicio.getNombre();
            datos[1] = servicio.getPrecio();
            dtmServicios.addRow(datos);
        }
        tblServicios.setModel(dtmServicios);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblRellena = new javax.swing.JLabel();
        lblRellena1 = new javax.swing.JLabel();
        cbServicios = new javax.swing.JComboBox<>();
        tfPrecio = new javax.swing.JTextField();
        lbl€ = new javax.swing.JLabel();
        btnAnadir = new javax.swing.JButton();
        tfOtroServicio = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblServicios = new javax.swing.JTable();
        btnSiguiente = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblRellena.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        lblRellena.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRellena.setText("Marca los servicios con los que cuente tu área y sus precios");

        lblRellena1.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        lblRellena1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRellena1.setText("(si son gratuitos escriba \"0 €\")");

        cbServicios.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        cbServicios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pernocta", "Pasajero extra", "Electricidad", "Llenado/vaciado", "mascota", "Lavadora", "Secadora", "Ficha", "Otro servicio" }));
        cbServicios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbServiciosActionPerformed(evt);
            }
        });

        tfPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPrecioActionPerformed(evt);
            }
        });

        lbl€.setText("€");

        btnAnadir.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        btnAnadir.setText("Añadir servicio");
        btnAnadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnadirActionPerformed(evt);
            }
        });

        tblServicios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblServicios);

        btnSiguiente.setBackground(new java.awt.Color(0, 0, 0));
        btnSiguiente.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        btnSiguiente.setForeground(new java.awt.Color(255, 255, 255));
        btnSiguiente.setText("Siguiente");
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSiguiente)
                .addGap(20, 20, 20))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(lblRellena, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(lblRellena1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(74, 74, 74)
                            .addComponent(cbServicios, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(tfPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(12, 12, 12)
                            .addComponent(lbl€))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(74, 74, 74)
                            .addComponent(tfOtroServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(74, 74, 74)
                            .addComponent(btnAnadir, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(279, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnSiguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(66, 66, 66)
                    .addComponent(lblRellena)
                    .addGap(12, 12, 12)
                    .addComponent(lblRellena1)
                    .addGap(37, 37, 37)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(cbServicios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tfPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(3, 3, 3)
                            .addComponent(lbl€)))
                    .addGap(6, 6, 6)
                    .addComponent(tfOtroServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(12, 12, 12)
                    .addComponent(btnAnadir)
                    .addContainerGap(335, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbServiciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbServiciosActionPerformed
        // TODO add your handling code here:
        servicioEscogido = cbServicios.getSelectedItem(); //guardo el nombre que está seleccionado cuando presiono btnAnadir
        if (servicioEscogido == "Otro servicio"){
            tfOtroServicio.setEnabled(true);
        }else{
            tfOtroServicio.setEnabled(false);
        }
    }//GEN-LAST:event_cbServiciosActionPerformed

    private void tfPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPrecioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfPrecioActionPerformed

    private void btnAnadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnadirActionPerformed
        // TODO add your handling code here:
        llenarArray();
        llenarTabla();
    
    }//GEN-LAST:event_btnAnadirActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        for (Servicio servicio : listaServicios) {
            String insert = "INSERT INTO servicios (nombre,precio) VALUES (\""+servicio.getNombre()+"\","+servicio.getPrecio()+");";
            try {
                conexion.insertInTabla(insert);
            } catch (SQLException ex) {
                Logger.getLogger(PantallaServicios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        PantallaDatosArea pda = new PantallaDatosArea();
        pda.miArea.setServicios(listaServicios);
        listaServicios.toString();
        
        PantallaPrincipal pp = new PantallaPrincipal();
        this.setVisible(false);
        pp.setVisible(true);
    }//GEN-LAST:event_btnSiguienteActionPerformed

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
            java.util.logging.Logger.getLogger(PantallaServicios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaServicios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaServicios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaServicios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaServicios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnadir;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JComboBox<String> cbServicios;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblRellena;
    private javax.swing.JLabel lblRellena1;
    private javax.swing.JLabel lbl€;
    private javax.swing.JTable tblServicios;
    private javax.swing.JTextField tfOtroServicio;
    private javax.swing.JTextField tfPrecio;
    // End of variables declaration//GEN-END:variables
}
