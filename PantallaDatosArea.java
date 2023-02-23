/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pantallasSwing;

import javax.swing.ImageIcon;

/**
 *
 * @author sarac
 */
public class PantallaDatosArea extends javax.swing.JFrame {

    /**
     * Creates new form PantallaServicios
     */
    public PantallaDatosArea() {
        initComponents();
        setSize(440,540);
        ImageIcon fondo = new ImageIcon("src/main/java/imagenes/wallpaperVan.png");
        lblFondo.setSize(440,540);
        lblFondo.setIcon(fondo);
        setLocationRelativeTo(null);//Centrar el JFrame
        setResizable(false);//No permite redimensionar la ventana
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblRellena = new javax.swing.JLabel();
        btnOmitir = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        lblDireccion = new javax.swing.JLabel();
        lblTelefono = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblPagWeb = new javax.swing.JLabel();
        tfDireccion = new javax.swing.JTextField();
        tfTelefono = new javax.swing.JTextField();
        tfEmail = new javax.swing.JTextField();
        tfPagWeb = new javax.swing.JTextField();
        lblFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblRellena.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblRellena.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRellena.setText("Por favor rellena todos los datos de tu área");
        getContentPane().add(lblRellena, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 440, -1));

        btnOmitir.setBackground(new java.awt.Color(0, 0, 0));
        btnOmitir.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        btnOmitir.setForeground(new java.awt.Color(255, 255, 255));
        btnOmitir.setText("Omitir");
        btnOmitir.setMaximumSize(new java.awt.Dimension(87, 23));
        btnOmitir.setMinimumSize(new java.awt.Dimension(87, 23));
        btnOmitir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOmitirActionPerformed(evt);
            }
        });
        getContentPane().add(btnOmitir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 90, 30));

        btnSiguiente.setBackground(new java.awt.Color(0, 0, 0));
        btnSiguiente.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        btnSiguiente.setForeground(new java.awt.Color(255, 255, 255));
        btnSiguiente.setText("Siguiente");
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });
        getContentPane().add(btnSiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 460, -1, 30));

        lblDireccion.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblDireccion.setText("Dirección:");
        getContentPane().add(lblDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        lblTelefono.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblTelefono.setText("Teléfono:");
        getContentPane().add(lblTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, -1, -1));

        lblEmail.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblEmail.setText("E-mail:");
        getContentPane().add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, 20));

        lblPagWeb.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblPagWeb.setText("Página web:");
        getContentPane().add(lblPagWeb, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, -1, 20));
        getContentPane().add(tfDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 72, 270, 30));
        getContentPane().add(tfTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 112, 270, 30));
        getContentPane().add(tfEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 152, 270, 30));
        getContentPane().add(tfPagWeb, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 192, 270, 30));
        getContentPane().add(lblFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOmitirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOmitirActionPerformed
        // TODO add your handling code here:
        PantallaDatosArea ps = new PantallaDatosArea();
        this.setVisible(false);
        ps.setVisible(true);
    }//GEN-LAST:event_btnOmitirActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(PantallaDatosArea.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaDatosArea.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaDatosArea.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaDatosArea.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaDatosArea().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOmitir;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblPagWeb;
    private javax.swing.JLabel lblRellena;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JTextField tfDireccion;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfPagWeb;
    private javax.swing.JTextField tfTelefono;
    // End of variables declaration//GEN-END:variables
}
