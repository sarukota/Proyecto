/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package clasesJava;

import java.awt.Dimension;
import java.awt.Image;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
public class Interfaz extends javax.swing.JFrame {
    
    Area miArea;
    Vehiculo vehiculo;
    Cliente cliente;
    Parcela parcela;
    Alerta alerta;
    Servicio servicio;
    Set <Cliente> clientes = new HashSet<>();
    DefaultTableModel dtmResult = new DefaultTableModel(); //instancio un modelo de tabla
    ArrayList<Servicio> listaDatos = new ArrayList<>(); //Creo un array para añadir servicios
    Object buscarPor; //Creo un objeto para determinar la busqueda
    
    
    int contador = 2; //Contador para cambiar label de clientes

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
    
    //se instancia una nueva area con los datos de los campos
    public Area crearArea(){
        Area area = new Area();
        area.setDireccion(tfDireccion.getText());
        area.setTelefono((Integer.parseInt(tfTelefonoArea.getText())));
        area.setMail(tfEmailArea.getText());
        area.setWeb(tfPagWeb.getText());
        area.setNumParcelas((Integer.parseInt(tfNParcelas.getText())));
        area.setPrecioNoche((Integer.parseInt(tfprecioNoche.getText())));
        return area;
    }
    
    //se instancia un nuevo vehículo con los datos de los campos
    public Vehiculo crearVehiculo(){
        vehiculo = new Vehiculo();
        vehiculo.setMatricula(tfMatricula.getText());
        vehiculo.setMarca(tfMarca.getText());
        vehiculo.setModelo(tfModelo.getText());
        vehiculo.setCheckIn(dcCheckIn.getDate());
        vehiculo.setCheckOut(dcCheckOut.getDate());
        vehiculo.setNumOcupantes (Integer.parseInt(cbOcupantes.getSelectedItem().toString())); //parseo el objeto recogido por el comboBox a int
        return vehiculo;
    }
    
    //se instancia una nueva parcela con los datos de los campos
    public Parcela crearParcela(){
        parcela = new Parcela();
        parcela.setNumParcela(Integer.parseInt(cbParcela.getSelectedItem().toString()));
        parcela.setDisponible(false);
        return parcela;
    }
    
    //Método para crear un nuevo cliente con los datos del cuestionario
    public Cliente crearCliente(){
        cliente = new Cliente();
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
    
    //Método para cambiar del frame Clientes al de vehiculos cuando ya se hayan introducido todos
    public void cambiarAVehiculos(){
        if ((contador-1) >= vehiculo.getNumOcupantes()){
            pnlClientes.setVisible(false);
            JOptionPane.showMessageDialog(null, "Todos los clientes del vehículo " +vehiculo.getMatricula()+" han sido guardados" );
            
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
    
    //Método para establecer los datos del area de la BD en el pnlDatosArea
    
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
    
    //Método que añade los botones de las parcelas en la pantalla del mapa
    public void aniadirBotones(){
        
        for (int i = 0; i <= miArea.getNumParcelas(); i++) {
            List<JButton>botones = new ArrayList<>();
            JButton btn = new JButton(String.valueOf(i));
            btn.setPreferredSize(new Dimension(40,40));
            botones.add(btn);
            pnlBotones.add(btn);
        }
        pnlBotones.updateUI();
        
    }
   
    //metodo para definir el modelo y mostrar los datos de la BD en la tabla del panel de Gestion
    private void llenarTablaBuscar() throws SQLException{
        buscarPor = cbSeleccion.getSelectedItem(); //guardo el tipo de busqueda que está seleccionada 
        ConexionBBDD connect = new ConexionBBDD();
        if (buscarPor == "Por vehiculo"){
            String [] cabecera = {"Matricula","Marca","Modelo","NºOcupantes","Check in","Check out"};
            dtmResult.setColumnIdentifiers(cabecera);
            tblResult.setModel(dtmResult);
            dtmResult.setRowCount(0);
            try {
                connect.selectFromTabla("SELECT* FROM vehiculos;",dtmResult);
            } catch (SQLException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }else if (buscarPor == "Por cliente"){
            String [] cabecera = {"DNI","Nombre","Apellido 1","Apellido 2","F.nacimiento","Nacionalidad","Telefono","Mail"};
            dtmResult.setColumnIdentifiers(cabecera);
            dtmResult.setRowCount(0);
            try {
                connect.selectFromTabla("SELECT* FROM clientes;",dtmResult);
            } catch (SQLException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            String [] cabecera = {"Nº parcela","Disponible"};
            dtmResult.setColumnIdentifiers(cabecera);
            dtmResult.setRowCount(0);
            try {
                connect.selectFromTabla("SELECT* FROM parcelas;",dtmResult);
            } catch (SQLException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        jScrollPane2 = new javax.swing.JScrollPane();
        tblResult = new javax.swing.JTable();
        cbSeleccion = new javax.swing.JComboBox<>();
        btnBuscar = new javax.swing.JButton();
        pnlMapa = new javax.swing.JPanel();
        btnImportar = new javax.swing.JButton();
        lblImagen = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlBotones = new javax.swing.JPanel();
        pnlAlertas = new javax.swing.JPanel();
        pnlDatos = new javax.swing.JPanel();
        pnlDatosArea = new javax.swing.JPanel();
        tfEmailArea = new javax.swing.JTextField();
        tfNParcelas = new javax.swing.JTextField();
        tfPagWeb = new javax.swing.JTextField();
        lblPrecioNoche = new javax.swing.JLabel();
        tfprecioNoche = new javax.swing.JTextField();
        lblRellena = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        lblTelefono1 = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblNPArcelas = new javax.swing.JLabel();
        lblPagWeb = new javax.swing.JLabel();
        tfDireccion = new javax.swing.JTextField();
        tfTelefonoArea = new javax.swing.JTextField();
        btnSiguiente = new javax.swing.JButton();

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
            .addGap(0, 700, Short.MAX_VALUE)
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

        pnlGestion.setBackground(new java.awt.Color(0, 153, 204));
        pnlGestion.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tblResult);

        pnlGestion.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 890, 480));

        cbSeleccion.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        cbSeleccion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Por vehiculo", "Por cliente", "Por parcela" }));
        cbSeleccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSeleccionActionPerformed(evt);
            }
        });
        pnlGestion.add(cbSeleccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 120, 30));

        btnBuscar.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        pnlGestion.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, -1, 30));

        tpnlPantallas.addTab("ges", pnlGestion);

        pnlMapa.setBackground(new java.awt.Color(0, 153, 204));
        pnlMapa.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnImportar.setText("Importar mapa");
        btnImportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportarActionPerformed(evt);
            }
        });
        pnlMapa.add(btnImportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 23, 134, 42));
        pnlMapa.add(lblImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 83, 710, 400));

        pnlBotones.setLayout(new java.awt.GridLayout());
        jScrollPane1.setViewportView(pnlBotones);

        pnlMapa.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 490, 850, 120));

        tpnlPantallas.addTab("mapa", pnlMapa);

        javax.swing.GroupLayout pnlAlertasLayout = new javax.swing.GroupLayout(pnlAlertas);
        pnlAlertas.setLayout(pnlAlertasLayout);
        pnlAlertasLayout.setHorizontalGroup(
            pnlAlertasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1110, Short.MAX_VALUE)
        );
        pnlAlertasLayout.setVerticalGroup(
            pnlAlertasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );

        tpnlPantallas.addTab("aler", pnlAlertas);

        pnlDatos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlDatosArea.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        pnlDatosArea.add(tfEmailArea, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 180, 230, -1));
        pnlDatosArea.add(tfNParcelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 260, 230, -1));
        pnlDatosArea.add(tfPagWeb, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 220, 230, -1));

        lblPrecioNoche.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblPrecioNoche.setText("€/noche:");
        pnlDatosArea.add(lblPrecioNoche, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, -1, -1));
        pnlDatosArea.add(tfprecioNoche, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 300, 230, -1));

        lblRellena.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblRellena.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRellena.setText("Por favor rellena todos los datos de tu área");
        pnlDatosArea.add(lblRellena, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, -1, -1));

        lblDireccion.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblDireccion.setText("Dirección:");
        pnlDatosArea.add(lblDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        lblTelefono1.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblTelefono1.setText("Teléfono:");
        pnlDatosArea.add(lblTelefono1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, -1, -1));

        lblEmail.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblEmail.setText("E-mail:");
        pnlDatosArea.add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, -1, -1));

        lblNPArcelas.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblNPArcelas.setText("Nº parcelas:");
        pnlDatosArea.add(lblNPArcelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, -1, -1));

        lblPagWeb.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        lblPagWeb.setText("Página web:");
        pnlDatosArea.add(lblPagWeb, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, -1, -1));
        pnlDatosArea.add(tfDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, 230, -1));
        pnlDatosArea.add(tfTelefonoArea, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, 230, -1));

        btnSiguiente.setBackground(new java.awt.Color(0, 0, 0));
        btnSiguiente.setFont(new java.awt.Font("Rockwell Nova", 0, 12)); // NOI18N
        btnSiguiente.setForeground(new java.awt.Color(255, 255, 255));
        btnSiguiente.setText("Siguiente");
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });
        pnlDatosArea.add(btnSiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 410, -1, 30));

        pnlDatos.add(pnlDatosArea, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 440, 490));

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
        parcela = crearParcela();
        vehiculo = crearVehiculo();
        vehiculo.setParcela(parcela);
        
        ConexionBBDD conexion = new ConexionBBDD();
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
        clientes.add(cliente);
        lblCliente.setText("Cliente "+contador);
        System.out.println("matricula: "+vehiculo.getMatricula());
        cambiarAVehiculos();
        vehiculo.setClientes(clientes);
        contador ++;
        
        ConexionBBDD conexion = new ConexionBBDD();
        try {
            conexion.insertInTabla(cliente.toSQL());  
        } catch (SQLException ex) {
            Logger.getLogger(InterfazOld.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("fallo al insertar el vehiculo en la BD");
        }

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
        aniadirBotones();
    }//GEN-LAST:event_pnlBtnMapaMouseClicked

    private void pnlBtnAlertasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnAlertasMouseClicked
        tpnlPantallas.setSelectedIndex(4);
    }//GEN-LAST:event_pnlBtnAlertasMouseClicked

    private void pnlBtnInfoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBtnInfoMouseClicked
        tpnlPantallas.setSelectedIndex(5);
    }//GEN-LAST:event_pnlBtnInfoMouseClicked

    private void btnImportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportarActionPerformed
        //Coloca la imagen seleccionada con el metodo seleccionarImagen en una etiqueta
        String pathMapa = seleccionarImagen(); //Guardo la ruta del archivo
        insertarImagen (lblImagen, pathMapa);

    }//GEN-LAST:event_btnImportarActionPerformed

    private void cbSeleccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSeleccionActionPerformed
        // TODO add your handling code here:
        buscarPor = cbSeleccion.getSelectedItem(); //guardo el nombre que está seleccionado cuando presiono btnAnadir
        
    }//GEN-LAST:event_cbSeleccionActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
            llenarTablaBuscar();
        } catch (SQLException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        /* Al pulsar boton se establece la conexión con la BD y se añaden a la tabla Area los datos del cuestionario
        Ademas se pasa a la siguiente pantalla*/
        ConexionBBDD connect = new ConexionBBDD();
        miArea = crearArea();
        /*String insert = "INSERT INTO area (direccion,telefono,mail,web,num_parcelas,precio_noche) VALUES (\""+tfDireccion.getText()+"\","+Integer.valueOf(tfTelefono.getText())+",\""
        + tfEmail.getText()+"\",\""+tfPagWeb.getText()+"\","+Integer.valueOf(tfNParcelas.getText())+","+Integer.valueOf(tfprecioNoche.getText())+");";*/
        try {
            connect.insertInTabla(miArea.toSQL());
        } catch (SQLException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      
    }//GEN-LAST:event_btnSiguienteActionPerformed

    
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
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnGuardarCliente;
    private javax.swing.JButton btnGuardarVehiculo;
    private javax.swing.JButton btnImportar;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JComboBox<String> cbOcupantes;
    private javax.swing.JComboBox<String> cbParcela;
    private javax.swing.JComboBox<String> cbSeleccion;
    private com.toedter.calendar.JDateChooser dcCheckIn;
    private com.toedter.calendar.JDateChooser dcCheckOut;
    private com.toedter.calendar.JDateChooser dcFechaNac;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblApellido1;
    private javax.swing.JLabel lblApellido2;
    private javax.swing.JLabel lblCheckIn;
    private javax.swing.JLabel lblCheckOut;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblDNI;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFechaNac;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel lblMail;
    private javax.swing.JLabel lblMarca;
    private javax.swing.JLabel lblMatricula;
    private javax.swing.JLabel lblMenu;
    private javax.swing.JLabel lblModelo;
    private javax.swing.JLabel lblNPArcelas;
    private javax.swing.JLabel lblNacionalidad;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblOcupantes;
    private javax.swing.JLabel lblPagWeb;
    private javax.swing.JLabel lblParcela;
    private javax.swing.JLabel lblPrecioNoche;
    private javax.swing.JLabel lblRellena;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTelefono1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlAlertas;
    private javax.swing.JPanel pnlBienvenida;
    private javax.swing.JPanel pnlBotones;
    private javax.swing.JPanel pnlBtnAlertas;
    private javax.swing.JPanel pnlBtnGestion;
    private javax.swing.JPanel pnlBtnInfo;
    private javax.swing.JPanel pnlBtnMapa;
    private javax.swing.JPanel pnlBtnRegistro;
    private javax.swing.JPanel pnlClientes;
    private javax.swing.JPanel pnlDatos;
    private javax.swing.JPanel pnlDatosArea;
    private javax.swing.JPanel pnlGestion;
    private javax.swing.JPanel pnlMapa;
    private javax.swing.JPanel pnlRegistro;
    private javax.swing.JPanel pnlVehiculos;
    private javax.swing.JTable tblResult;
    private javax.swing.JTextField tfApellido1;
    private javax.swing.JTextField tfApellido2;
    private javax.swing.JTextField tfDNI;
    private javax.swing.JTextField tfDireccion;
    private javax.swing.JTextField tfEmailArea;
    private javax.swing.JTextField tfMail;
    private javax.swing.JTextField tfMarca;
    private javax.swing.JTextField tfMatricula;
    private javax.swing.JTextField tfModelo;
    private javax.swing.JTextField tfNParcelas;
    private javax.swing.JTextField tfNacionalidad;
    private javax.swing.JTextField tfNombre;
    private javax.swing.JTextField tfPagWeb;
    private javax.swing.JTextField tfTelefono;
    private javax.swing.JTextField tfTelefonoArea;
    private javax.swing.JTextField tfprecioNoche;
    private javax.swing.JTabbedPane tpnlPantallas;
    // End of variables declaration//GEN-END:variables
}
