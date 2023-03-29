/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package pantallasSwing;

import clasesJava.ConexionBBDD;
import clasesJava.Servicio;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PnlPlano extends javax.swing.JPanel {
    
    ConexionBBDD conexion = new ConexionBBDD(); 
 
    public PnlPlano() {
        initComponents();
        setSize(1300,700); //Da el tamaño a la ventana
        setLocation(0,0);
        try {
            listaBotones();
        } catch (SQLException ex) {
            Logger.getLogger(PnlPlano.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Método para seleccionar la ruta absoluta de un archivo de imagen con un JFileChooser
    public String seleccionarImagen(){
        JFileChooser chooser = new JFileChooser(); //Declaro el FileChooser
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, GIF & PNG Images", "jpg", "gif", "png");
        chooser.setFileFilter(filter); //Pongo un filtro para la extensión de los archivos
        int returnVal = chooser.showOpenDialog(null); //Se abre la ventana de FileChooser
        if(returnVal == JFileChooser.APPROVE_OPTION) { //Si se presiona el botón aceptar
           System.out.println("You chose to open this file: " + chooser.getSelectedFile().getPath());
        }
        return chooser.getSelectedFile().getPath();
    }
    
    //Metodo para insertar una imagen en un Jlabel
    public void insertarImagen (JLabel labelFondo, String ruta){ 
        ImageIcon imagen = new ImageIcon(ruta); //obtengo una imagen de la ruta específica
        ImageIcon imagenEscala = new ImageIcon(imagen.getImage().getScaledInstance(labelFondo.getWidth(), labelFondo.getHeight(), Image.SCALE_DEFAULT)); //para ajustar imagen al tamaño del label
        if (imagenEscala.getImageLoadStatus() == java.awt.MediaTracker.COMPLETE) {
            labelFondo.setText("");
            labelFondo.setIcon(imagenEscala); // Se coloca en el JLabel
        }else{
            System.err.println("Ha ocurrido un error");
        }
    }
    
    public void listaBotones() throws SQLException{
        System.out.println("entro en lista botones");
        String parcelasArea = Arrays.toString(conexion.selectFromTabla("SELECT num_parcelas FROM area",1));
        char caracter1 = parcelasArea.charAt(1);
        char caracter2 = parcelasArea.charAt(2);
        System.out.println("caracteres sumados: "+Character.getNumericValue(caracter1+caracter2));
        
        for (int i = 0; i < (Character.getNumericValue(caracter1+caracter2)); i++) {
            JButton btnParcela = new JButton(String.valueOf(i+1));
            PnlInfoParcelas PIP = new PnlInfoParcelas(i+1);
            btnParcela.setSize(40, 40);

            //se modifica el color de los botones según la disponibilidad de la parcela
            String disponible = Arrays.toString(conexion.selectFromTabla("SELECT disponibilidad FROM parcelas WHERE num_parcela = "+(i+1)+";",1));
            System.out.println("disponibilidad: "+disponible);
            char disponibleChar = disponible.charAt(1);
            if (Character.getNumericValue(disponibleChar)!= 1){
                btnParcela.setBackground(Color.black);
                btnParcela.setForeground(Color.white);
            }
            else {
                btnParcela.setBackground(Color.white);
                btnParcela.setForeground(Color.black);
            } 
            //Se añade mouse listener sobre el boton para que salte el panel de datos al pasar raton por encima
            btnParcela.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    lblImagen.add(PIP,BorderLayout.CENTER);
                    lblImagen.revalidate();
                    lblImagen.repaint();
                } 
            });
            btnParcela.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    lblImagen.remove(PIP);
                    lblImagen.revalidate();
                    lblImagen.repaint();
                } 
            });
            pnlBotones.add(btnParcela);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnImportar = new javax.swing.JButton();
        lblImagen = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlBotones = new javax.swing.JPanel();

        setBackground(new java.awt.Color(0, 153, 204));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnImportar.setBackground(new java.awt.Color(0, 0, 0));
        btnImportar.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        btnImportar.setForeground(new java.awt.Color(255, 255, 255));
        btnImportar.setText("Importar mapa");
        btnImportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportarActionPerformed(evt);
            }
        });
        add(btnImportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 23, 134, 42));
        add(lblImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 83, 710, 400));

        pnlBotones.setBackground(new java.awt.Color(0, 153, 204));
        pnlBotones.setLayout(new java.awt.GridLayout(1, 0, 5, 0));
        jScrollPane1.setViewportView(pnlBotones);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 490, 850, 120));
    }// </editor-fold>//GEN-END:initComponents

    private void btnImportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportarActionPerformed
        //Coloca la imagen seleccionada con el metodo seleccionarImagen en una etiqueta
        //Añadir funcionalidad: si en la BD pathMapa tiene datos, meter esos datos en el label
        String pathMapa = seleccionarImagen(); //Guardo la ruta del archivo
        insertarImagen (lblImagen, pathMapa);
        //miArea.setPathMapa(pathMapa);
    }//GEN-LAST:event_btnImportarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImportar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JPanel pnlBotones;
    // End of variables declaration//GEN-END:variables
}
