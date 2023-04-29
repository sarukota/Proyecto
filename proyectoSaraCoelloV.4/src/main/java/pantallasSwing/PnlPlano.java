/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package pantallasSwing;

import clasesJava.ConexionBBDD;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/*Este panel nos muestra todas las parcelas de las que dispone el áre y su disponibilidad. También nos permite cargar una
imagen de nuestro equipo que representará el mapa de nuestro área. Fallo a la hora de representar los botones 
cuando hay más de 15 parcelas aprox. Fallo a la hora de cargar la imagen de la base de datos una vez ha sido guardada*/

public class PnlPlano extends javax.swing.JPanel {
    
    ConexionBBDD conexion = new ConexionBBDD(); 
 
    public PnlPlano() {
        initComponents();
        pnlBase.setSize(360,450);
        
        try {
            String rutaMapa = conexion.selectDato("SELECT ruta_mapa FROM area");
            if (rutaMapa != null){
                insertarImagen (lblImagen, rutaMapa);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PnlPlano.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            listaBotones();
        } catch (SQLException ex) {
            Logger.getLogger(PnlPlano.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "No han podido cargarse los datos de las parcelas","Error",0);
        }
    }
    
    //Método para seleccionar la ruta absoluta de un archivo de imagen con un JFileChooser
    public String seleccionarImagen(){
        JFileChooser chooser = new JFileChooser(); //Declaro el FileChooser
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Imagenes JPG, GIF & PNG", "jpg", "gif", "png");
        chooser.setFileFilter(filter); //Pongo un filtro para la extensión de los archivos
        int returnVal = chooser.showOpenDialog(null); //Se abre la ventana de FileChooser
        if(returnVal == JFileChooser.APPROVE_OPTION) { try {
            //Si se presiona el botón aceptar se guarda en BD la ruta del mapa con el formato correcto
            String ruta = chooser.getSelectedFile().getPath();
            String rutaModificada = ruta.replace("\\", "\\\\");
            String direccionArea = conexion.selectDato("SELECT direccion FROM area");
            conexion.updateBd("UPDATE area SET ruta_mapa ='"+rutaModificada+"' WHERE direccion = '"+direccionArea+"';");
            } catch (SQLException ex) {
                Logger.getLogger(PnlPlano.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "Ha ocurrido un error al seleccionar el archivo","Error",0);
            }
        }
        return chooser.getSelectedFile().getPath();
    }
    
    //Metodo para insertar una imagen en un Jlabel
    public void insertarImagen (JLabel labelFondo, String ruta){ 
        ImageIcon imagen = new ImageIcon(ruta); //obtengo una imagen de la ruta específica
        labelFondo.setSize(600, 510);
        ImageIcon imagenEscala = new ImageIcon(imagen.getImage().getScaledInstance(labelFondo.getWidth(), labelFondo.getHeight(), Image.SCALE_DEFAULT)); //para ajustar imagen al tamaño del label
        if (imagenEscala.getImageLoadStatus() == java.awt.MediaTracker.COMPLETE) {
            labelFondo.setText("");
            labelFondo.setIcon(imagenEscala); // Se coloca en el JLabel
        }else{
            JOptionPane.showMessageDialog(this, "Ha ocurrido un error","Error",0);
        }
    } 
    
    /*Método que genera los botones que van a representar las parcelas, se generan tantos botones 
    como parcelas hayamos indicado en la información del Area*/
    public void listaBotones() throws SQLException{ 
        int numParcelas = Integer.parseInt(conexion.selectDato("SELECT num_parcelas FROM area"));
        pnlBotones.setLayout(new GridLayout(0, numParcelas));
        pnlBotones.removeAll();
        for (int i = 0; i < numParcelas; i++) {
            JButton btnParcela = new JButton(String.valueOf(i+1));
            PnlInfoParcelas PIP = new PnlInfoParcelas(i+1);
            btnParcela.setSize(40, 40);
            //se modifica el color de los botones según la disponibilidad de la parcela
            int disponible = Integer.parseInt(conexion.selectDato("SELECT disponibilidad FROM parcelas WHERE num_parcela = "+(i+1)+";")) ;
            if (disponible != 1){
                btnParcela.setBackground(Color.black);
                btnParcela.setForeground(Color.white);
                //Se añade mouse listener sobre el boton para que salte el pnlInfoParcelas al pasar raton por encima
                btnParcela.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        pnlBase.add(PIP,BorderLayout.CENTER);
                        pnlBase.revalidate();
                        pnlBase.repaint();
                    } 
                });
                btnParcela.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        pnlBase.remove(PIP);
                        pnlBase.revalidate();
                        pnlBase.repaint();
                    } 
                });
                }
            else {
                btnParcela.setBackground(Color.white);
                btnParcela.setForeground(Color.black);
            } 
            
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
        pnlBase = new javax.swing.JPanel();

        setBackground(new java.awt.Color(0, 153, 204));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnImportar.setBackground(new java.awt.Color(0, 0, 0));
        btnImportar.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        btnImportar.setForeground(new java.awt.Color(255, 255, 255));
        btnImportar.setText("Importar mapa");
        btnImportar.setBorder(null);
        btnImportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportarActionPerformed(evt);
            }
        });
        add(btnImportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 134, 42));

        lblImagen.setMinimumSize(new java.awt.Dimension(600, 510));
        lblImagen.setPreferredSize(new java.awt.Dimension(600, 510));
        add(lblImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 600, 510));

        pnlBotones.setBackground(new java.awt.Color(0, 153, 204));
        pnlBotones.setAutoscrolls(true);
        pnlBotones.setLayout(new java.awt.GridLayout(1, 0, 5, 0));
        jScrollPane1.setViewportView(pnlBotones);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 750, 60));

        pnlBase.setBackground(new java.awt.Color(0, 153, 204));
        pnlBase.setPreferredSize(new java.awt.Dimension(350, 450));
        add(pnlBase, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 160, 290, 430));
    }// </editor-fold>//GEN-END:initComponents

    private void btnImportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportarActionPerformed
        //Coloca la imagen seleccionada con el metodo seleccionarImagen en una etiqueta
        String pathMapa = seleccionarImagen(); //Guardo la ruta del archivo
        insertarImagen (lblImagen, pathMapa);
        
    }//GEN-LAST:event_btnImportarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImportar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JPanel pnlBase;
    private javax.swing.JPanel pnlBotones;
    // End of variables declaration//GEN-END:variables
}
