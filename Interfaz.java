/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package clasesJava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Interfaz extends javax.swing.JFrame {
    
    int contador = 2; //Contador para cambiar label de clientes
    int contadorMax;
    String matricula;

    public Interfaz() {
        initComponents();
        setSize(1300,700); //Da el tamaño a la ventana
        setLocationRelativeTo(null);//Centrar el JFrame
        setResizable(false);
        lblMenu.setSize(300,700);
        tpnlPantallas.setSize(1000,700);
        ImageIcon fondo = new ImageIcon("src/main/java/imagenes/wallpaperVan2.jpg");
        lblMenu.setIcon(fondo);
    }
    
    //se instancia un nuevo vehículo con los datos de los campos
    public Vehiculo crearVehiculo(){
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMatricula(tfMatricula.getText());
        matricula = vehiculo.getMatricula();
        vehiculo.setMarca(tfMarca.getText());
        vehiculo.setModelo(tfModelo.getText());
        vehiculo.setCheckIn(dcCheckIn.getDate());
        vehiculo.setCheckOut(dcCheckOut.getDate());
        vehiculo.setNumOcupantes (Integer.parseInt(cbOcupantes.getSelectedItem().toString())); //parseo el objeto recogido por el comboBox a int
        contadorMax = vehiculo.getNumOcupantes();
        return vehiculo;
    }
    
    //se instancia un nuevo vehículo con los datos de los campos
    public Parcela crearParcela(){
        Parcela parcela = new Parcela();
        parcela.setNumParcela(Integer.parseInt(cbParcela.getSelectedItem().toString()));
        parcela.setDisponible(false);
        return parcela;
    }
    
    //Método para crear un nuevo cliente con los datos del cuestionario
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
    
    //método para introducir los datos de un vehículo en la BD
    public void guardarVehiculoBD(Vehiculo vehiculo) throws SQLException{
        ConexionBBDD conexion = new ConexionBBDD();
        Connection connect = DriverManager.getConnection(conexion.getBBDD(),conexion.getUSER(),conexion.getPASSWORD());
	Statement sentencia = connect.createStatement();
	sentencia.executeUpdate("USE furgoGestion;");
        System.out.println("usando furgoGestion");
        String insert = "INSERT INTO vehiculos (matricula, marca, modelo, num_ocupantes, check_in, check_out)"
                + "VALUES ('"+vehiculo.getMatricula()+"','"+vehiculo.getMarca()+"','"+vehiculo.getModelo()+"',"+vehiculo.getNumOcupantes()+",'"
                + conexion.fechaSQL(vehiculo.getCheckIn())+"','"+conexion.fechaSQL(vehiculo.getCheckOut())+"');";
        sentencia.executeUpdate(insert);
        System.out.println("los datos se han insertado correctamente");
        sentencia.close();
        connect.close();
    }
    
    public void cambiarAVehiculos(){
        if ((contador-1) >= contadorMax){
            pnlClientes.setVisible(false);
            JOptionPane.showMessageDialog(null, "Todos los clientes del vehículo " +matricula+" han sido guardados" );
            
            tfMatricula.setEnabled(true);
            tfMarca.setEnabled(true);
            tfModelo.setEnabled(true);
            dcCheckIn.setEnabled(true);
            dcCheckOut.setEnabled(true);
            cbOcupantes.setEnabled(true);
            cbParcela.setEnabled(true);

            //Limpio todos los campos de la interfaz
            tfMatricula.setText(null);
            tfMarca.setText(null);
            tfModelo.setText(null);
            dcCheckIn.setDate(null);
            dcCheckOut.setDate(null);
            cbOcupantes.setSelectedIndex(0);
            cbParcela.setSelectedIndex(0);
            
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlBtnRegistro = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        pnlBtnGestion = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        pnlBtnMapa = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        pnlBtnAlertas = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        pnlBtnInfo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblMenu = new javax.swing.JLabel();
        tpnlPantallas = new javax.swing.JTabbedPane();
        pnlBienvenida = new javax.swing.JPanel();
        pnlRegistro = new javax.swing.JPanel();
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
        pnlGestion = new javax.swing.JPanel();
        pnlMapa = new javax.swing.JPanel();
        pnlAlertas = new javax.swing.JPanel();
        pnlDatos = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlBtnRegistro.setBackground(new java.awt.Color(0, 153, 204));
        pnlBtnRegistro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBtnRegistroMouseClicked(evt);
            }
        });
        pnlBtnRegistro.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Rockwell Nova", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("REGISTRO");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnlBtnRegistro.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 0, 90, 50));

        getContentPane().add(pnlBtnRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 340, 50));

        pnlBtnGestion.setBackground(new java.awt.Color(0, 153, 204));
        pnlBtnGestion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnlBtnGestion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBtnGestionMouseClicked(evt);
            }
        });
        pnlBtnGestion.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Rockwell Nova", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("GESTIÓN");
        pnlBtnGestion.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 0, 90, 50));

        getContentPane().add(pnlBtnGestion, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 340, 50));

        pnlBtnMapa.setBackground(new java.awt.Color(0, 153, 204));
        pnlBtnMapa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnlBtnMapa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBtnMapaMouseClicked(evt);
            }
        });
        pnlBtnMapa.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Rockwell Nova", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("MAPA");
        pnlBtnMapa.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, 90, 50));

        getContentPane().add(pnlBtnMapa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 340, 50));

        pnlBtnAlertas.setBackground(new java.awt.Color(0, 153, 204));
        pnlBtnAlertas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnlBtnAlertas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBtnAlertasMouseClicked(evt);
            }
        });
        pnlBtnAlertas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Rockwell Nova", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("ALERTAS");
        pnlBtnAlertas.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 0, 90, 50));

        getContentPane().add(pnlBtnAlertas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 340, 50));

        pnlBtnInfo.setBackground(new java.awt.Color(0, 153, 204));
        pnlBtnInfo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnlBtnInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBtnInfoMouseClicked(evt);
            }
        });
        pnlBtnInfo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Rockwell Nova", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("INFORMACIÓN");
        pnlBtnInfo.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 0, 140, 50));

        getContentPane().add(pnlBtnInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 340, 50));
        getContentPane().add(lblMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 340, 700));

        javax.swing.GroupLayout pnlBienvenidaLayout = new javax.swing.GroupLayout(pnlBienvenida);
        pnlBienvenida.setLayout(pnlBienvenidaLayout);
        pnlBienvenidaLayout.setHorizontalGroup(
            pnlBienvenidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        pnlBienvenidaLayout.setVerticalGroup(
            pnlBienvenidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 670, Short.MAX_VALUE)
        );

        tpnlPantallas.addTab("bienv", pnlBienvenida);

        pnlRegistro.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlClientes.setBackground(new java.awt.Color(0, 153, 204));
        pnlClientes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDNI.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblDNI.setText("DNI/ID:");
        pnlClientes.add(lblDNI, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, -1, -1));

        tfDNI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfDNIActionPerformed(evt);
            }
        });
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

        btnGuardarCliente.setFont(new java.awt.Font("Rockwell Nova", 1, 16)); // NOI18N
        btnGuardarCliente.setText("Guardar cliente");
        btnGuardarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnGuardarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClienteActionPerformed(evt);
            }
        });
        pnlClientes.add(btnGuardarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 550, 184, 40));

        lblCliente.setFont(new java.awt.Font("Rockwell Nova", 1, 16)); // NOI18N
        lblCliente.setText("Cliente 1:");
        pnlClientes.add(lblCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, 100, -1));

        lblTitulo.setFont(new java.awt.Font("Rockwell Nova", 0, 18)); // NOI18N
        lblTitulo.setText("Inserta los datos de los clientes:");
        pnlClientes.add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, -1, -1));

        pnlRegistro.add(pnlClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 0, 630, 670));

        pnlVehiculos.setBackground(new java.awt.Color(0, 153, 204));
        pnlVehiculos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblMatricula.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblMatricula.setText("Matrícula:");
        pnlVehiculos.add(lblMatricula, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, -1, -1));

        lblMarca.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblMarca.setText("Marca:");
        pnlVehiculos.add(lblMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, -1, -1));

        tfMatricula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfMatriculaActionPerformed(evt);
            }
        });
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
        cbOcupantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbOcupantesActionPerformed(evt);
            }
        });
        pnlVehiculos.add(cbOcupantes, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 460, 90, 30));

        lblOcupantes.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblOcupantes.setText("Ocupantes:");
        pnlVehiculos.add(lblOcupantes, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 420, -1, -1));

        lblParcela.setFont(new java.awt.Font("Rockwell Nova", 0, 16)); // NOI18N
        lblParcela.setText("Parcela:");
        pnlVehiculos.add(lblParcela, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 420, -1, -1));

        cbParcela.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        cbParcela.setMaximumSize(new java.awt.Dimension(70, 20));
        cbParcela.setMinimumSize(new java.awt.Dimension(70, 20));
        cbParcela.setPreferredSize(new java.awt.Dimension(70, 20));
        /*if (cbLuz.isSelected()){
            cbParcela.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6" }));
        }else{
            cbParcela.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        }*/

        cbParcela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbParcelaActionPerformed(evt);
            }
        });
        pnlVehiculos.add(cbParcela, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 460, 80, 30));

        btnGuardarVehiculo.setFont(new java.awt.Font("Rockwell Nova", 1, 16)); // NOI18N
        btnGuardarVehiculo.setText("Guardar vehiculo");
        btnGuardarVehiculo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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

        pnlRegistro.add(pnlVehiculos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 470, 670));

        tpnlPantallas.addTab("reg", pnlRegistro);

        javax.swing.GroupLayout pnlGestionLayout = new javax.swing.GroupLayout(pnlGestion);
        pnlGestion.setLayout(pnlGestionLayout);
        pnlGestionLayout.setHorizontalGroup(
            pnlGestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        pnlGestionLayout.setVerticalGroup(
            pnlGestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 670, Short.MAX_VALUE)
        );

        tpnlPantallas.addTab("ges", pnlGestion);

        javax.swing.GroupLayout pnlMapaLayout = new javax.swing.GroupLayout(pnlMapa);
        pnlMapa.setLayout(pnlMapaLayout);
        pnlMapaLayout.setHorizontalGroup(
            pnlMapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        pnlMapaLayout.setVerticalGroup(
            pnlMapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 670, Short.MAX_VALUE)
        );

        tpnlPantallas.addTab("mapa", pnlMapa);

        javax.swing.GroupLayout pnlAlertasLayout = new javax.swing.GroupLayout(pnlAlertas);
        pnlAlertas.setLayout(pnlAlertasLayout);
        pnlAlertasLayout.setHorizontalGroup(
            pnlAlertasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        pnlAlertasLayout.setVerticalGroup(
            pnlAlertasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 670, Short.MAX_VALUE)
        );

        tpnlPantallas.addTab("aler", pnlAlertas);

        pnlDatos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        tpnlPantallas.addTab("dato", pnlDatos);

        getContentPane().add(tpnlPantallas, new org.netbeans.lib.awtextra.AbsoluteConstraints(337, 0, 1110, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfMatriculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfMatriculaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfMatriculaActionPerformed

    private void cbOcupantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbOcupantesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbOcupantesActionPerformed

    private void cbParcelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbParcelaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbParcelaActionPerformed

    private void btnGuardarVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarVehiculoActionPerformed
        // TODO add your handling code here:
        Parcela parcela = crearParcela();
        Vehiculo vehiculo = crearVehiculo();
        vehiculo.setParcela(parcela);
        
        ConexionBBDD conexion = new ConexionBBDD();
        /*String insertVehiculo = "INSERT INTO vehiculos (matricula, marca, modelo, num_ocupantes, check_in, check_out)"
                + "VALUES ('"+vehiculo.getMatricula()+"','"+vehiculo.getMarca()+"','"+vehiculo.getModelo()+"',"+vehiculo.getNumOcupantes()+",'"
                + conexion.fechaSQL(vehiculo.getCheckIn())+"','"+conexion.fechaSQL(vehiculo.getCheckOut())+"');";
        String insertParcela = "INSERT INTO parcelas (num_parcela, disponibilidad)"
                + "VALUES ("+parcela.getNumParcela()+","+parcela.isDisponible()+");";*/
        try {
            conexion.insertInTabla(vehiculo.toSQL());
            conexion.insertInTabla(parcela.toSQL());
        } catch (SQLException ex) {
            Logger.getLogger(InterfazOld.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("fallo al insertar el vehiculo en la BD");
        }
       
        System.out.println(vehiculo.toString());
        System.out.println("numero ocupantes" + vehiculo.getNumOcupantes());
        
        //Desabilita los componentes al introducir el vehiculo y habilita el panel Clientes
        pnlClientes.setVisible(true);
        tfMatricula.setEnabled(false);
        tfMarca.setEnabled(false);
        tfModelo.setEnabled(false);
        dcCheckIn.setEnabled(false);
        dcCheckOut.setEnabled(false);
        cbOcupantes.setEnabled(false);
        cbParcela.setEnabled(false);

    }//GEN-LAST:event_btnGuardarVehiculoActionPerformed

    private void tfDNIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfDNIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfDNIActionPerformed

    private void btnGuardarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClienteActionPerformed
        // TODO add your handling code here:
        //Al pulsa boton guardar en cliente, se instancia un nuevo cliente con los datos de los campos
        //se añade al set ClientesEnVehiculo y este nuevo set se añade al objeto vehículo

        //o hago una consulta a la BD para obtener nº de ocupantes y con eso relleno variable
        Cliente cliente = crearCliente();
        lblCliente.setText("Cliente "+contador);
        System.out.println("matricula: "+matricula);
        cambiarAVehiculos();
        /*if ((contador-1) >= contadorMax){
            pnlClientes.setVisible(false);
            JOptionPane.showMessageDialog(null, "Todos los clientes del vehículo " +matricula+" han sido guardados" );
            
            tfMatricula.setEnabled(true);
            tfMarca.setEnabled(true);
            tfModelo.setEnabled(true);
            dcCheckIn.setEnabled(true);
            dcCheckOut.setEnabled(true);
            cbOcupantes.setEnabled(true);
            cbParcela.setEnabled(true);

            //Limpio todos los campos de la interfaz
            tfMatricula.setText(null);
            tfMarca.setText(null);
            tfModelo.setText(null);
            dcCheckIn.setDate(null);
            dcCheckOut.setDate(null);
            cbOcupantes.setSelectedIndex(0);
            cbParcela.setSelectedIndex(0);
            
        }*/
        contador ++;
        
        ConexionBBDD conexion = new ConexionBBDD();
        String insertCliente = "INSERT INTO clientes (dni, nombre, apellido1, apellido2, fecha_nac, nacionalidad, telefono, mail)"
                + "VALUES ('"+cliente.getDni()+"','"+cliente.getNombre()+"','"+cliente.getApellido1()+"','"+cliente.getApellido2()+"','"
                + conexion.fechaSQL(cliente.getFechaNac())+"','"+cliente.getNacionalidad()+"',"+cliente.getTelefono()+",'"+cliente.getMail()+"');";
        try {
            conexion.insertInTabla(insertCliente);  
        } catch (SQLException ex) {
            Logger.getLogger(InterfazOld.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("fallo al insertar el vehiculo en la BD");
        }
        /*Cliente cliente1 = crearCliente();
        System.out.println(cliente1.toString());
        Set<Cliente>clientesEnVehiculo = new HashSet<Cliente>();
        clientesEnVehiculo.add(cliente1);
        System.out.println(clientesEnVehiculo.toString());*/

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

    private void pnlBtnRegistroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnRegistroMouseClicked
        tpnlPantallas.setSelectedIndex(1);
        pnlClientes.setVisible(false);
    }//GEN-LAST:event_pnlBtnRegistroMouseClicked

    private void pnlBtnGestionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnGestionMouseClicked
        tpnlPantallas.setSelectedIndex(2);
    }//GEN-LAST:event_pnlBtnGestionMouseClicked

    private void pnlBtnMapaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnMapaMouseClicked
        tpnlPantallas.setSelectedIndex(3);
    }//GEN-LAST:event_pnlBtnMapaMouseClicked

    private void pnlBtnAlertasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnAlertasMouseClicked
        tpnlPantallas.setSelectedIndex(4);
    }//GEN-LAST:event_pnlBtnAlertasMouseClicked

    private void pnlBtnInfoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnInfoMouseClicked
        tpnlPantallas.setSelectedIndex(5);
    }//GEN-LAST:event_pnlBtnInfoMouseClicked

    
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
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardarCliente;
    private javax.swing.JButton btnGuardarVehiculo;
    private javax.swing.JComboBox<String> cbOcupantes;
    private javax.swing.JComboBox<String> cbParcela;
    private com.toedter.calendar.JDateChooser dcCheckIn;
    private com.toedter.calendar.JDateChooser dcCheckOut;
    private com.toedter.calendar.JDateChooser dcFechaNac;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
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
    private javax.swing.JLabel lblMenu;
    private javax.swing.JLabel lblModelo;
    private javax.swing.JLabel lblNacionalidad;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblOcupantes;
    private javax.swing.JLabel lblParcela;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlAlertas;
    private javax.swing.JPanel pnlBienvenida;
    private javax.swing.JPanel pnlBtnAlertas;
    private javax.swing.JPanel pnlBtnGestion;
    private javax.swing.JPanel pnlBtnInfo;
    private javax.swing.JPanel pnlBtnMapa;
    private javax.swing.JPanel pnlBtnRegistro;
    private javax.swing.JPanel pnlClientes;
    private javax.swing.JPanel pnlDatos;
    private javax.swing.JPanel pnlGestion;
    private javax.swing.JPanel pnlMapa;
    private javax.swing.JPanel pnlRegistro;
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
    private javax.swing.JTabbedPane tpnlPantallas;
    // End of variables declaration//GEN-END:variables
}
