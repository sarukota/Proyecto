/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package pantallasSwing;

import clasesJava.Alerta;
import clasesJava.AlertaVehiculo;
import clasesJava.Cliente;
import clasesJava.Interfaz;
import clasesJava.ConexionBBDD;
import clasesJava.Vehiculo;
import java.awt.Color;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/* En este panel se pueden registrar los vehiculos y sus ocupantes, que se ingresarán en la BD, se habilita el ingreso de tantos
ocupantes como hayamos marcado al ingresar el vehiculo*/

public class PnlRegistro extends javax.swing.JPanel {
    
    ConexionBBDD conexion = new ConexionBBDD();
    private Color camposNull = new Color(255,0,51);
    Vehiculo vehiculo;
    Alerta alerta;
    int contadorCliente; //Contador para cambiar lbl clientes

    public PnlRegistro() {
        initComponents();
        configInicial();
        contadorCliente = 1;
    }
    
    public boolean comprobacionDatosCliente() {
                
        boolean datosCorrectos = true;
        
        tfDNI.setBackground(Color.white);
        tfNombre.setBackground(Color.white);
        tfApellido1.setBackground(Color.white);
        tfTelefono.setBackground(Color.white);
        
        if (tfDNI.getText().isBlank()) {
            tfDNI.setBackground(Color.red);
            datosCorrectos = false;
        }
        
        if (tfNombre.getText().isBlank()) {
            tfNombre.setBackground(Color.red);
            datosCorrectos = false;
        }
        
        if (tfApellido1.getText().isBlank()) {
            tfApellido1.setBackground(Color.red);
            datosCorrectos = false;
        }
        
        if (tfTelefono.getText().isBlank()) {
            tfTelefono.setBackground(Color.red);
            datosCorrectos = false;
        }
        
        Pattern patternInteger = Pattern.compile("\\d+");
        
        if (!patternInteger.matcher(tfTelefono.getText()).matches()) {
            tfTelefono.setBackground(Color.red);
            datosCorrectos = false;
        }
        
        return datosCorrectos;
    }
    
    public boolean comprobacionDatosVehiculo() {
        
        boolean datosCorrectos = true;
        
        tfMatricula.setBackground(Color.white);
        dcCheckIn.setBackground(Color.white);
        dcCheckOut.setBackground(Color.white);
                
        if (tfMatricula.getText().isBlank()) {
            tfMatricula.setBackground(Color.red);
            datosCorrectos = false;
        }
        
        Date checkin = dcCheckIn.getDate();
        Date checkout = dcCheckOut.getDate();
        
        if (checkin == null) {
            dcCheckIn.setBackground(Color.red);
            datosCorrectos = false;
        }
        
        if (checkout == null) {
            dcCheckOut.setBackground(Color.red);
            datosCorrectos = false;
        }
        
        if (checkin != null && checkout != null) {
            if (checkout.before(checkin)) {
                dcCheckOut.setBackground(Color.red);
                datosCorrectos = false;
            }
        }

        return datosCorrectos;
    }
    
    //se instancia un nuevo vehículo con los datos de los campos
    public Vehiculo crearVehiculo(){
        if (comprobacionDatosVehiculo()) {
            vehiculo = new Vehiculo();
            vehiculo.setMatricula(tfMatricula.getText());
            vehiculo.setMarca(tfMarca.getText());
            vehiculo.setModelo(tfModelo.getText());
            vehiculo.setNumParcela(Integer.parseInt(cbParcela.getSelectedItem().toString()));
            vehiculo.setCheckIn(dcCheckIn.getDate());
            vehiculo.setCheckOut(dcCheckOut.getDate());
            vehiculo.setNumOcupantes(Integer.parseInt(cbOcupantes.getSelectedItem().toString())); //parseo el objeto recogido por el comboBox a int
        }
        else { 
            vehiculo = null;
        }
        return vehiculo;
    }
    
    //Método para crear un nuevo cliente con los datos del cuestionario
    public Cliente crearCliente(){
        
        Cliente cliente = new Cliente();
        
        if (comprobacionDatosCliente()) {  // Si los datos son correctos
            cliente.setDni(tfDNI.getText());
            cliente.setNacionalidad(tfNacionalidad.getText());
            cliente.setNombre(tfNombre.getText());
            cliente.setApellido1(tfApellido1.getText());
            cliente.setApellido2(tfApellido2.getText());
            cliente.setFechaNac(dcFechaNac.getDate());
            cliente.setTelefono(Integer.parseInt(tfTelefono.getText()));
            cliente.setMail(tfMail.getText());
            cliente.setMatriculaAuto(tfMatricula.getText());
        }
        else {
            cliente = null;
        }
        return cliente; 
    }
    
    //Método para cambiar del frame Clientes al de vehiculos cuando ya se hayan introducido todos
    public void cambiarPnl(){
            configInicial();
            tfMatricula.setEnabled(true);
            tfMarca.setEnabled(true);
            tfModelo.setEnabled(true);
            dcCheckIn.setEnabled(true);
            dcCheckOut.setEnabled(true);
            cbOcupantes.setEnabled(true);
            cbParcela.setEnabled(true);

            //Limpio todos los campos de la interfaz
            tfMatricula.setText("");
            tfMarca.setText("");
            tfModelo.setText("");
            dcCheckIn.setDate(null);
            dcCheckOut.setDate(null);
            cbOcupantes.setSelectedIndex(0);
            cbParcela.setSelectedIndex(0);          
             
    }
      
    private void configInicial(){
        pnlClientes.setVisible(false);
        tfMatricula.setBackground(Color.white);
        try {
            String[] datosComboBox;
            datosComboBox = conexion.selectFromTabla("SELECT num_parcela FROM parcelas WHERE disponibilidad = true;");
            cbParcela.removeAllItems();
            for (int i = 0; i < (datosComboBox.length); i++) {
            cbParcela.addItem(datosComboBox[i]); 
        }
        } catch (SQLException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        pnlVehiculos = new javax.swing.JPanel();
        lblMatricula = new javax.swing.JLabel();
        lblMarca = new javax.swing.JLabel();
        tfMatricula = new javax.swing.JTextField();
        tfMarca = new javax.swing.JTextField();
        lblModelo = new javax.swing.JLabel();
        tfModelo = new javax.swing.JTextField();
        lblCheckIn = new javax.swing.JLabel();
        lblCheckOut = new javax.swing.JLabel();
        dcCheckIn = new com.toedter.calendar.JDateChooser();
        dcCheckOut = new com.toedter.calendar.JDateChooser();
        cbOcupantes = new javax.swing.JComboBox<>();
        lblOcupantes = new javax.swing.JLabel();
        lblParcela = new javax.swing.JLabel();
        cbParcela = new javax.swing.JComboBox<>();
        btnGuardarVehiculo = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlClientes.setBackground(new java.awt.Color(0, 153, 204));
        pnlClientes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDNI.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblDNI.setText("DNI/ID:");
        pnlClientes.add(lblDNI, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, -1, -1));
        pnlClientes.add(tfDNI, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 90, 140, 30));

        lblNacionalidad.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblNacionalidad.setText("Nacionalidad:");
        pnlClientes.add(lblNacionalidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, -1, -1));
        pnlClientes.add(tfNacionalidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 290, 230, 30));

        lblNombre.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblNombre.setText("Nombre:");
        pnlClientes.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, -1, -1));
        pnlClientes.add(tfNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 140, 230, 30));

        lblApellido1.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblApellido1.setText("Apellido 1:");
        pnlClientes.add(lblApellido1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, -1, -1));
        pnlClientes.add(tfApellido1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 190, 230, 30));
        pnlClientes.add(tfApellido2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 240, 230, 30));

        lblApellido2.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblApellido2.setText("Apellido 2:");
        pnlClientes.add(lblApellido2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, -1, -1));

        lblFechaNac.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblFechaNac.setText("Fecha nac:");
        pnlClientes.add(lblFechaNac, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 350, -1, -1));
        pnlClientes.add(dcFechaNac, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 340, 230, 30));

        lblTelefono.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblTelefono.setText("Teléfono:");
        pnlClientes.add(lblTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 400, -1, -1));
        pnlClientes.add(tfTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 390, 230, 30));

        lblMail.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblMail.setText("E-Mail:");
        pnlClientes.add(lblMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 450, -1, -1));
        pnlClientes.add(tfMail, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 440, 230, 30));

        btnGuardarCliente.setBackground(new java.awt.Color(0, 0, 0));
        btnGuardarCliente.setFont(new java.awt.Font("Rockwell Nova", 1, 16)); // NOI18N
        btnGuardarCliente.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarCliente.setText("Guardar cliente");
        btnGuardarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClienteActionPerformed(evt);
            }
        });
        pnlClientes.add(btnGuardarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 550, 184, 40));

        lblCliente.setFont(new java.awt.Font("Rockwell Nova", 1, 16)); // NOI18N
        lblCliente.setText("Cliente 1:");
        lblCliente.setToolTipText("");
        pnlClientes.add(lblCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, 100, -1));

        lblTitulo.setFont(new java.awt.Font("Rockwell Nova", 0, 18)); // NOI18N
        lblTitulo.setText("Inserta los datos de los clientes:");
        pnlClientes.add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, -1, -1));

        add(pnlClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 0, 630, 670));

        pnlVehiculos.setBackground(new java.awt.Color(0, 153, 204));
        pnlVehiculos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblMatricula.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblMatricula.setText("Matrícula:");
        pnlVehiculos.add(lblMatricula, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, -1, -1));

        lblMarca.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblMarca.setText("Marca:");
        pnlVehiculos.add(lblMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, -1, -1));
        pnlVehiculos.add(tfMatricula, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 110, 210, 30));
        pnlVehiculos.add(tfMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 160, 210, 30));

        lblModelo.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblModelo.setText("Modelo:");
        pnlVehiculos.add(lblModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 220, -1, -1));
        pnlVehiculos.add(tfModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 210, 210, 30));

        lblCheckIn.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblCheckIn.setText("Check-in:");
        pnlVehiculos.add(lblCheckIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 310, -1, -1));

        lblCheckOut.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblCheckOut.setText("Check-out:");
        pnlVehiculos.add(lblCheckOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 310, -1, -1));
        pnlVehiculos.add(dcCheckIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 350, 130, 30));
        pnlVehiculos.add(dcCheckOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(285, 350, 130, 30));

        cbOcupantes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6" }));
        cbOcupantes.setMaximumSize(new java.awt.Dimension(70, 20));
        cbOcupantes.setMinimumSize(new java.awt.Dimension(70, 20));
        cbOcupantes.setPreferredSize(new java.awt.Dimension(70, 20));
        pnlVehiculos.add(cbOcupantes, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 460, 90, 30));

        lblOcupantes.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblOcupantes.setText("Ocupantes:");
        pnlVehiculos.add(lblOcupantes, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 420, -1, -1));

        lblParcela.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblParcela.setText("Parcela:");
        pnlVehiculos.add(lblParcela, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 420, -1, -1));

        cbParcela.setMaximumSize(new java.awt.Dimension(70, 20));
        cbParcela.setMinimumSize(new java.awt.Dimension(70, 20));
        cbParcela.setPreferredSize(new java.awt.Dimension(70, 20));
        pnlVehiculos.add(cbParcela, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 460, 80, 30));

        btnGuardarVehiculo.setBackground(new java.awt.Color(0, 0, 0));
        btnGuardarVehiculo.setFont(new java.awt.Font("Rockwell Nova", 1, 16)); // NOI18N
        btnGuardarVehiculo.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarVehiculo.setText("Guardar vehiculo");
        btnGuardarVehiculo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarVehiculoActionPerformed(evt);
            }
        });
        pnlVehiculos.add(btnGuardarVehiculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 550, -1, 40));

        jLabel6.setFont(new java.awt.Font("Rockwell Nova", 0, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Introduce los datos del vehículo:");
        pnlVehiculos.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, -1, -1));

        add(pnlVehiculos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, 670));
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClienteActionPerformed
        //Comprobación de campos vacíos. 
        
            Cliente cliente = crearCliente();
            
            if (cliente != null) {
                try {
                    conexion.updateBd(cliente.toSQL());
                    
                    lblCliente.setText("Cliente "+(contadorCliente+1)+":");
                    
                    tfDNI.setText("");
                    tfNombre.setText("");
                    tfApellido1.setText("");
                    tfApellido2.setText("");
                    tfNacionalidad.setText("");
                    dcFechaNac.setDate(null);
                    tfTelefono.setText("");
                    tfMail.setText("");

                    if ((contadorCliente) >= vehiculo.getNumOcupantes()){
                        cambiarPnl();
                        JOptionPane.showMessageDialog(null,"El vehiculo "+vehiculo.getMatricula()+" y todos sus ocupantes han sido guardados correctamente");
                    }
                    contadorCliente++;
                    
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "El cliente no ha podido añadirse");
                }
                
            }
            else {
                JOptionPane.showMessageDialog(this, "Compruebe los datos.");
            }
        
    }//GEN-LAST:event_btnGuardarClienteActionPerformed

    private void btnGuardarVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarVehiculoActionPerformed
        // TODO add your handling code here:
        vehiculo = crearVehiculo();
        
        if (vehiculo != null) { // Si los datos son correctos

            alerta = new Alerta("Salida del vehiculo "+vehiculo.getMatricula(),"Alerta automatica",vehiculo.getCheckOut());
            
            try {
                
                // Guardo el vehículo
                conexion.updateBd(vehiculo.toSQL());
                
                // Si el vehículo se guarda bien, bloqueo campos de vehículo y activo los del cliente                
                pnlClientes.setVisible(true);
                tfMatricula.setEnabled(false);
                tfMarca.setEnabled(false);
                tfModelo.setEnabled(false);
                dcCheckIn.setEnabled(false);
                dcCheckOut.setEnabled(false);
                cbOcupantes.setEnabled(false);
                cbParcela.setEnabled(false);
                
                try {
                    // Guardo la alerta
                    conexion.updateBd(alerta.toSQL());//Cambiar orden si cambio FK, poner esta linea despues de update vehiculo
                    AlertaVehiculo alertaVehiculo = new AlertaVehiculo(vehiculo.getMatricula(), alerta.getTitulo());
                    conexion.updateBd(alertaVehiculo.toSQL());
                    // Actualizo disponibilidad de la parcela
                    conexion.updateBd("UPDATE parcelas SET disponibilidad = false WHERE num_parcela ="+vehiculo.getNumParcela()+";");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "No se ha podido añadir la alerta del vehículo");
                }
                
            } catch (SQLException ex) {
                if (ex.getMessage().toLowerCase().contains("duplicate")) { // Si el mensaje es entrada duplicada, es porque se repite la matrícula
                    JOptionPane.showMessageDialog(this, "La matrícula ya está en el sistema.");
                }
                else {
                    JOptionPane.showMessageDialog(this, "No se ha podido añadir el vehículo");
                }
            }
            
        }
        else{ 
            JOptionPane.showMessageDialog(this, "Compruebe los datos.");
        }
    }//GEN-LAST:event_btnGuardarVehiculoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardarCliente;
    private javax.swing.JButton btnGuardarVehiculo;
    private javax.swing.JComboBox<String> cbOcupantes;
    private javax.swing.JComboBox<String> cbParcela;
    private com.toedter.calendar.JDateChooser dcCheckIn;
    private com.toedter.calendar.JDateChooser dcCheckOut;
    private com.toedter.calendar.JDateChooser dcFechaNac;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lblApellido1;
    private javax.swing.JLabel lblApellido2;
    private javax.swing.JLabel lblCheckIn;
    private javax.swing.JLabel lblCheckOut;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblDNI;
    private javax.swing.JLabel lblFechaNac;
    private javax.swing.JLabel lblMail;
    private javax.swing.JLabel lblMarca;
    private javax.swing.JLabel lblMatricula;
    private javax.swing.JLabel lblModelo;
    private javax.swing.JLabel lblNacionalidad;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblOcupantes;
    private javax.swing.JLabel lblParcela;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlClientes;
    private javax.swing.JPanel pnlVehiculos;
    private javax.swing.JTextField tfApellido1;
    private javax.swing.JTextField tfApellido2;
    private javax.swing.JTextField tfDNI;
    private javax.swing.JTextField tfMail;
    private javax.swing.JTextField tfMarca;
    private javax.swing.JTextField tfMatricula;
    private javax.swing.JTextField tfModelo;
    private javax.swing.JTextField tfNacionalidad;
    private javax.swing.JTextField tfNombre;
    private javax.swing.JTextField tfTelefono;
    // End of variables declaration//GEN-END:variables
}
